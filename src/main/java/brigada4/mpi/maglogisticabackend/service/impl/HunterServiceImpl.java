package brigada4.mpi.maglogisticabackend.service.impl;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterResponseDTO;
import brigada4.mpi.maglogisticabackend.dto.StatusDTO;
import brigada4.mpi.maglogisticabackend.exception.ConflictException;
import brigada4.mpi.maglogisticabackend.exception.NotFoundException;
import brigada4.mpi.maglogisticabackend.mapper.HunterApplicationMapper;
import brigada4.mpi.maglogisticabackend.mapper.HunterResponseMapper;
import brigada4.mpi.maglogisticabackend.models.*;
import brigada4.mpi.maglogisticabackend.repositories.*;
import brigada4.mpi.maglogisticabackend.service.HunterService;
import brigada4.mpi.maglogisticabackend.service.MagicianService;
import brigada4.mpi.maglogisticabackend.service.NotificationService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class HunterServiceImpl implements HunterService {

    @Autowired
    private HunterApplicationRepository hunterApplicationRepository;

    @Autowired
    private HunterApplicationMapper hunterApplicationMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HunterRepository hunterRepository;

    @Autowired
    private HunterResponseRepository hunterResponseRepository;

    @Autowired
    private HunterResponseMapper hunterResponseMapper;

    @Autowired
    private AnimalStorageRepository animalStorageRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MagicRepository magicRepository;
    @Autowired
    private ExtractorRepository extractorRepository;

    @Override
    public List<HunterApplicationDTO> getAllApplications() {
        return hunterApplicationRepository.findAllByStatus(ApplicationStatus.CREATED)
                .stream()
                .map(hunterApplicationMapper::toDTO)
                .toList();
    }

    @Override
    public HunterApplicationDTO getApplicationById(String id) {
        return hunterApplicationRepository.findById(id)
                .map(hunterApplicationMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Заявка " + id + " не найдена"));
    }

    @Override
    public List<HunterApplicationDTO> getMyApplications(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь " + email + " не найден"));

        return hunterApplicationRepository.findByHunterId(user.getId())
                .stream()
                .map(hunterApplicationMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public HunterApplicationDTO takeApplication(String email, String application_id) {
        HunterApplication app = hunterApplicationRepository.findById(application_id)
                .orElseThrow(() -> new NotFoundException("Заявка " + application_id + " не найдена"));

        if (app.getHunter() != null) {
            throw new ConflictException("Заявка уже взята");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь " + email + " не найден"));

        Hunter hunter = hunterRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Охотник " + user.getId() + " не найден"));

        app.setHunter(hunter);
        app.setStatus(ApplicationStatus.WORKED);

        hunterApplicationRepository.save(app);

        return hunterApplicationMapper.toDTO(app);
    }

    @Override
    public List<HunterResponseDTO> getMyResponses(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь " + email + " не найден"));

        Hunter hunter = hunterRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Охотник " + user.getId() + " не найден"));

        return hunterResponseRepository.findAllByHunterId(hunter.getId())
                .stream()
                .map(hunterResponseMapper::toDTO)
                .toList();
    }

    @Override
    public HunterResponseDTO completeApplication(String email, String applicationId) {

        HunterApplication app = hunterApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Заявка " + applicationId + " не найдена"));

        Animal animal = app.getAnimal();
        int requiredQuantity = app.getAnimalCount();

        AnimalStorage animalStorage = animalStorageRepository.findByAnimalId(animal.getId());
        animalStorage.setQuantity(animalStorage.getQuantity() + requiredQuantity);

        animalStorageRepository.save(animalStorage);

        HunterResponse hunterResponse = new HunterResponse(
                app.getId(),
                app.getHunter(),
                new Date(),
                requiredQuantity
        );

        hunterResponseRepository.save(hunterResponse);

        app.setHunterResponse(hunterResponse);
        app.setStatus(ApplicationStatus.FINISHED);
        hunterApplicationRepository.save(app);

        notificationService.sendMailAboutFinishingApplication(app.getExtractor(), app.getHunter());

        return hunterResponseMapper.toDTO(hunterResponse);
    }

    @Override
    public ByteArrayInputStream generateReportOne(String userId, String applicationId) {
        Hunter hunter = hunterRepository.findById(userId).orElse(null);
        HunterApplication hunterApplication = hunterApplicationRepository.findById(applicationId).orElse(null);
        if (hunter == null || hunterApplication == null) {
            return null;
        }
        Magic magic = magicRepository.findById(hunterApplication.getMagic().getId()).orElse(null);
        if (magic == null) {
            return null;
        }
        Extractor extractor = new Extractor();
        extractor = extractorRepository.findById(hunterApplication.getExtractor().getId()).orElse(null);

        // Создаем поток для записи PDF в память
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // 1. Инициализация PDF документа
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // 2. Загружаем шрифт с поддержкой кириллицы
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

            document.setFont(font);

            // 3. Добавляем заголовок
            Paragraph title = new Paragraph("Report by hunter application with id:\n" + applicationId)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n")); // пустая строка

            // 4. Добавляем информацию
            document.add(new Paragraph("Applicant's full name : " + extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic()).setFontSize(14));
            document.add(new Paragraph("Application type: Hunter application").setFontSize(14));
            document.add(new Paragraph("Magic:" + magic.getMagicColour().getName() + ", " + magic.getMagicPower().getName() + ", " + magic.getMagicType().getName() + ", " + magic.getMagicState().getName()).setFontSize(14));
            document.add(new Paragraph("Application status: " + hunterApplication.getStatus()).setFontSize(14));

            document.add(new Paragraph("\n")); // пустая строка
            Paragraph subTitle1 = new Paragraph("Application lifecycle\n")
                    .setFontSize(14)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(subTitle1);

            // 5. Добавляем таблицу с данными
            Table table = new Table(UnitValue.createPercentArray(new float[]{30, 70}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Заголовки таблицы
            table.addHeaderCell(new Cell().add(new Paragraph("Employee").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Employee's full name").setBold()));

            // Данные таблицы
            if (hunter != null && hunter.getId() != null && extractor != null && extractor.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Extractor")));
                table.addCell(new Cell().add(new Paragraph(extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic())));

                table.addCell(new Cell().add(new Paragraph("Hunter")));
                table.addCell(new Cell().add(new Paragraph(hunter.getSurname() + " " + hunter.getName() + " " + hunter.getPatronymic())));
            } else if (extractor != null && extractor.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Extractor")));
                table.addCell(new Cell().add(new Paragraph(extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic())));
            } else {
                return null;
            }

            document.add(table);

            document.add(new Paragraph("\n")); // пустая строка

            // 6. Добавляем подпись
            document.add(new Paragraph("Date of report generation: " + java.time.LocalDate.now())
                    .setFontSize(10)
                    .setItalic()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT));
            Paragraph signature = new Paragraph("Report generator by MagLogistica\nReport system")
                    .setFontSize(10)
                    .setItalic()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT);
            document.add(signature);

            document.add(new Paragraph("\n"));

            Paragraph signaturePerson = new Paragraph("___________________")
                    .setFontSize(10)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT);
            document.add(signaturePerson);

            // 7. Закрываем документ
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при генерации PDF: " + e.getMessage(), e);
        }

        // 8. Возвращаем InputStream
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream generateReportTwo(String userId, String applicationId) {
        Hunter hunter = hunterRepository.findById(userId).orElse(null);
        HunterApplication hunterApplication = hunterApplicationRepository.findById(applicationId).orElse(null);
        if (hunter == null || hunterApplication == null) {
            return null;
        }
        Magic magic = magicRepository.findById(hunterApplication.getMagic().getId()).orElse(null);
        if (magic == null) {
            return null;
        }
        Extractor extractor = new Extractor();
        extractor = extractorRepository.findById(hunterApplication.getExtractor().getId()).orElse(null);

        // Создаем поток для записи PDF в память
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // 1. Инициализация PDF документа
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.setMargins(110, 70, 0, 70);
            // 2. Загружаем шрифт с поддержкой кириллицы
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

            document.setFont(font);

            InputStream imageStream = getClass().getResourceAsStream("fon_for_report.png");
            if (imageStream == null) {
                // Если файл не найден в ресурсах, попробуем другой путь
                imageStream = getClass().getClassLoader().getResourceAsStream("fon_for_report.png");
            }

            if (imageStream != null) {
                byte[] imageBytes = imageStream.readAllBytes();
                ImageData imageData = ImageDataFactory.create(imageBytes);

                // 4. Создаем обработчик событий для добавления фона на каждую страницу
                MagicianService.BackgroundEventHandler handler = new MagicianService.BackgroundEventHandler(imageData);
                pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, handler);
            } else {
                System.err.println("Фоновое изображение не найдено. Будет создан отчет без фона.");
            }

            // 3. Добавляем заголовок
            Paragraph title = new Paragraph("Report by hunter application with id:\n" + applicationId)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n")); // пустая строка

            // 4. Добавляем информацию
            document.add(new Paragraph("Applicant's full name : " + extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic()).setFontSize(14));
            document.add(new Paragraph("Application type: Hunter application").setFontSize(14));
            document.add(new Paragraph("Magic:" + magic.getMagicColour().getName() + ", " + magic.getMagicPower().getName() + ", " + magic.getMagicType().getName() + ", " + magic.getMagicState().getName()).setFontSize(14));
            document.add(new Paragraph("Application status: " + hunterApplication.getStatus()).setFontSize(14));

            document.add(new Paragraph("\n")); // пустая строка
            Paragraph subTitle1 = new Paragraph("Application lifecycle\n")
                    .setFontSize(14)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(subTitle1);

            // 5. Добавляем таблицу с данными
            Table table = new Table(UnitValue.createPercentArray(new float[]{30, 70}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Заголовки таблицы
            table.addHeaderCell(new Cell().add(new Paragraph("Employee").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Employee's full name").setBold()));

            // Данные таблицы
            if (hunter != null && hunter.getId() != null && extractor != null && extractor.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Extractor")));
                table.addCell(new Cell().add(new Paragraph(extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic())));

                table.addCell(new Cell().add(new Paragraph("Hunter")));
                table.addCell(new Cell().add(new Paragraph(hunter.getSurname() + " " + hunter.getName() + " " + hunter.getPatronymic())));
            } else if (extractor != null && extractor.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Extractor")));
                table.addCell(new Cell().add(new Paragraph(extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic())));
            } else {
                return null;
            }

            document.add(table);

            document.add(new Paragraph("\n")); // пустая строка

            // 6. Добавляем подпись
            document.add(new Paragraph("Date of report generation: " + java.time.LocalDate.now())
                    .setFontSize(10)
                    .setItalic()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT));
            Paragraph signature = new Paragraph("Report generator by MagLogistica\nReport system")
                    .setFontSize(10)
                    .setItalic()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT);
            document.add(signature);

            document.add(new Paragraph("\n"));

            Paragraph signaturePerson = new Paragraph("___________________")
                    .setFontSize(10)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT);
            document.add(signaturePerson);

            // 7. Закрываем документ
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при генерации PDF: " + e.getMessage(), e);
        }

        // 8. Возвращаем InputStream
        return new ByteArrayInputStream(out.toByteArray());
    }

}
