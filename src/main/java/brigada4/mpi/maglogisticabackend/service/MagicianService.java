package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.MagicApplicationDTO;
import brigada4.mpi.maglogisticabackend.mapper.MagicApplicationMapper;
import brigada4.mpi.maglogisticabackend.models.*;
import brigada4.mpi.maglogisticabackend.repositories.*;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.kernel.events.Event;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MagicianService {

    private final MagicianRepository magicianRepository;
    private final MagicApplicationMapper magicApplicationMapper;
    private final MagicApplicationRepository magicApplicationRepository;
    private final MagicAppPatternRepository magicAppPatternRepository;
    private final StorekeeperRepository storekeeperRepository;
    private final HunterRepository hunterRepository;
    private final ExtractorRepository extractorRepository;
    private final UserRepository userRepository;
    private final MagicRepository magicRepository;

    @Autowired
    public MagicianService(MagicianRepository magicianRepository, MagicApplicationMapper magicApplicationMapper, MagicApplicationRepository magicApplicationRepository, MagicAppPatternRepository magicAppPatternRepository, StorekeeperRepository storekeeperRepository, HunterRepository hunterRepository, ExtractorRepository extractorRepository, UserRepository userRepository, MagicRepository magicRepository) {
        this.magicianRepository = magicianRepository;
        this.magicApplicationMapper = magicApplicationMapper;
        this.magicApplicationRepository = magicApplicationRepository;
        this.magicAppPatternRepository = magicAppPatternRepository;
        this.storekeeperRepository = storekeeperRepository;
        this.hunterRepository = hunterRepository;
        this.extractorRepository = extractorRepository;
        this.userRepository = userRepository;
        this.magicRepository = magicRepository;
    }

    @Transactional
    public MagicApplication createMagicApplication(String magicianId, MagicApplicationDTO magicApplicationDTO) {
        Magician magician = magicianRepository.findById(magicianId).orElse(null);
        if (magician == null) {
            return null;
        }
        MagicApplication magicApplication = magicApplicationMapper.fromMagicApplicationDTO(magicApplicationDTO);
        magicApplication.setMagician(magician);
        Date date = new Date();
        magicApplication.setInitDate(date);
        magicApplication.setStatus(ApplicationStatus.CREATED);
        return magicApplicationRepository.save(magicApplication);
    }

    @Transactional
    public MagicAppPattern createAppPattern(String magicianId, MagicApplicationDTO magicApplicationDTO) {
        Magician magician = magicianRepository.findById(magicianId).orElse(null);
        if (magician == null) {
            return null;
        }
        MagicAppPattern pattern = magicApplicationMapper.fromMagicAppDTO(magicApplicationDTO);
        pattern.setMagician(magician);
        return  magicAppPatternRepository.save(pattern);
    }

    public List<MagicApplication> getAllMagicApp(String magicianId) {
        return magicApplicationRepository.findAllByMagicianId(magicianId);
    }


    public List<MagicAppPattern> getAllMagicAppPatterns(String magicianId) {
        return magicAppPatternRepository.findAllByMagicianId(magicianId);
    }

    public List<Storekeeper> findAllStorekeepers() {
        return storekeeperRepository.findAll();
    }

    public List<Hunter> findAllHunters() {
        return hunterRepository.findAll();
    }

    public List<Extractor> findAllExtractors() {
        return extractorRepository.findAll();
    }

    public User findEmployeeById(String employeeId) {
        User user = userRepository.findById(employeeId).orElse(null);
        return user;
    }

    public User assignMagicalReward(String employeeId, int rewardCount) {
        User user = userRepository.findById(employeeId).orElse(null);
        if (user == null) {
            return null;
        }
        int oldRewardPoints = user.getRewardPoints();
        user.setRewardPoints(oldRewardPoints + rewardCount);
        return userRepository.save(user);
    }

    public User assignMagicalPenalty(String employeeId, int penaltyCount) {
        User user = userRepository.findById(employeeId).orElse(null);
        if (user == null) {
            return null;
        }
        int oldPenaltyPoints = user.getPenaltyPoints();
        user.setPenaltyPoints(oldPenaltyPoints + penaltyCount);
        return userRepository.save(user);
    }

    public List<User> findAllEmployees() {
        List<Storekeeper> storekeepers = storekeeperRepository.findAll();
        List<Hunter> hunters = hunterRepository.findAll();
        List<Extractor> extractors = extractorRepository.findAll();
        List<User> users = new ArrayList<>();
        users.addAll(storekeepers);
        users.addAll(hunters);
        users.addAll(extractors);
        return users;
    }

    public ByteArrayInputStream generateReportOne(String userId, String applicationId) {
        Magician magician = magicianRepository.findById(userId).orElse(null);
        MagicApplication magicApplication = magicApplicationRepository.findById(applicationId).orElse(null);
        if (magician == null || magicApplication == null) {
            return null;
        }
        Magic magic = magicRepository.findById(magicApplication.getMagic().getId()).orElse(null);
        if (magic == null) {
            return null;
        }
        Storekeeper storekeeper = storekeeperRepository.findById(magicApplication.getStorekeeper().getId()).orElse(null);
        if (storekeeper == null) {
            return null;
        }
        ExtractionApplication extractionApplication = new ExtractionApplication();
        Hunter hunter = new Hunter();
        Extractor extractor = new Extractor();
        if (magicApplication.getExtractionApp() != null) {
            extractionApplication = magicApplication.getExtractionApp();
            extractor = extractorRepository.findById(extractionApplication.getExtractor().getId()).orElse(null);
        }
        HunterApplication hunterApplication = new HunterApplication();
        if (extractionApplication.getHunterApp() != null) {
            hunterApplication = extractionApplication.getHunterApp();
            hunter = hunterRepository.findById(hunterApplication.getHunter().getId()).orElse(null);
        }


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
            Paragraph title = new Paragraph("Report by magic application with id:\n" + applicationId)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n")); // пустая строка

            // 4. Добавляем информацию
            document.add(new Paragraph("Applicant's full name : " + magician.getSurname() + " " + magician.getName() + " " + magician.getPatronymic()).setFontSize(14));
            document.add(new Paragraph("Application type: Magic application").setFontSize(14));
            document.add(new Paragraph("Magic:" + magic.getMagicColour().getName() + ", " + magic.getMagicPower().getName() + ", " + magic.getMagicType().getName() + ", " + magic.getMagicState().getName()).setFontSize(14));
            document.add(new Paragraph("Application status: " + magicApplication.getStatus()).setFontSize(14));

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
                table.addCell(new Cell().add(new Paragraph("Storekeeper")));
                table.addCell(new Cell().add(new Paragraph(storekeeper.getSurname() + " " + storekeeper.getName() + " " + storekeeper.getPatronymic())));

                table.addCell(new Cell().add(new Paragraph("Extractor")));
                table.addCell(new Cell().add(new Paragraph(extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic())));

                table.addCell(new Cell().add(new Paragraph("Hunter")));
                table.addCell(new Cell().add(new Paragraph(hunter.getSurname() + " " + hunter.getName() + " " + hunter.getPatronymic())));
            } else if (extractor != null && extractor.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Storekeeper")));
                table.addCell(new Cell().add(new Paragraph(storekeeper.getSurname() + " " + storekeeper.getName() + " " + storekeeper.getPatronymic())));

                table.addCell(new Cell().add(new Paragraph("Extractor")));
                table.addCell(new Cell().add(new Paragraph(extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic())));
            } else if (storekeeper != null && storekeeper.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Storekeeper")));
                table.addCell(new Cell().add(new Paragraph(storekeeper.getSurname() + " " + storekeeper.getName() + " " + storekeeper.getPatronymic())));
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

    public ByteArrayInputStream generateReportTwo(String userId, String applicationId) {
        Magician magician = magicianRepository.findById(userId).orElse(null);
        MagicApplication magicApplication = magicApplicationRepository.findById(applicationId).orElse(null);
        if (magician == null || magicApplication == null) {
            return null;
        }
        Magic magic = magicRepository.findById(magicApplication.getMagic().getId()).orElse(null);
        if (magic == null) {
            return null;
        }
        Storekeeper storekeeper = storekeeperRepository.findById(magicApplication.getStorekeeper().getId()).orElse(null);
        if (storekeeper == null) {
            return null;
        }
        ExtractionApplication extractionApplication = new ExtractionApplication();
        Hunter hunter = new Hunter();
        Extractor extractor = new Extractor();
        if (magicApplication.getExtractionApp() != null) {
            extractionApplication = magicApplication.getExtractionApp();
            extractor = extractorRepository.findById(extractionApplication.getExtractor().getId()).orElse(null);
        }
        HunterApplication hunterApplication = new HunterApplication();
        if (extractionApplication.getHunterApp() != null) {
            hunterApplication = extractionApplication.getHunterApp();
            hunter = hunterRepository.findById(hunterApplication.getHunter().getId()).orElse(null);
        }


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
                BackgroundEventHandler handler = new BackgroundEventHandler(imageData);
                pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, handler);
            } else {
                System.err.println("Фоновое изображение не найдено. Будет создан отчет без фона.");
            }

            // 3. Добавляем заголовок
            Paragraph title = new Paragraph("Report by magic application with id:\n" + applicationId)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n")); // пустая строка

            // 4. Добавляем информацию
            document.add(new Paragraph("Applicant's full name : " + magician.getSurname() + " " + magician.getName() + " " + magician.getPatronymic()).setFontSize(14));
            document.add(new Paragraph("Application type: Magic application").setFontSize(14));
            document.add(new Paragraph("Magic:" + magic.getMagicColour().getName() + ", " + magic.getMagicPower().getName() + ", " + magic.getMagicType().getName() + ", " + magic.getMagicState().getName()).setFontSize(14));
            document.add(new Paragraph("Application status: " + magicApplication.getStatus()).setFontSize(14));

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
                table.addCell(new Cell().add(new Paragraph("Storekeeper")));
                table.addCell(new Cell().add(new Paragraph(storekeeper.getSurname() + " " + storekeeper.getName() + " " + storekeeper.getPatronymic())));

                table.addCell(new Cell().add(new Paragraph("Extractor")));
                table.addCell(new Cell().add(new Paragraph(extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic())));

                table.addCell(new Cell().add(new Paragraph("Hunter")));
                table.addCell(new Cell().add(new Paragraph(hunter.getSurname() + " " + hunter.getName() + " " + hunter.getPatronymic())));
            } else if (extractor != null && extractor.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Storekeeper")));
                table.addCell(new Cell().add(new Paragraph(storekeeper.getSurname() + " " + storekeeper.getName() + " " + storekeeper.getPatronymic())));

                table.addCell(new Cell().add(new Paragraph("Extractor")));
                table.addCell(new Cell().add(new Paragraph(extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic())));
            } else if (storekeeper != null && storekeeper.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Storekeeper")));
                table.addCell(new Cell().add(new Paragraph(storekeeper.getSurname() + " " + storekeeper.getName() + " " + storekeeper.getPatronymic())));
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

    public static class BackgroundEventHandler implements IEventHandler {
        private final ImageData backgroundImage;

        public BackgroundEventHandler(ImageData backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();

            // Получаем размеры страницы
            Rectangle pageSize = page.getPageSize();

            // Создаем PdfCanvas для рисования на странице (до основного контента)
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.newContentStreamBefore(),
                    page.getResources(),
                    pdfDoc
            );

            // Сохраняем графическое состояние
            pdfCanvas.saveState();

            // Создаем изображение
            Image image = new Image(backgroundImage);

            // Растягиваем изображение на всю страницу
            image.scaleToFit(pageSize.getWidth(), pageSize.getHeight());
            image.setFixedPosition(0, 0);

            // Устанавливаем прозрачность (по желанию, чтобы текст был лучше виден)
            PdfExtGState gState = new PdfExtGState();
            gState.setFillOpacity(0.3f); // 30% непрозрачности (можно регулировать)
            pdfCanvas.setExtGState(gState);

            // Добавляем изображение на холст
            new Canvas(pdfCanvas, pageSize).add(image);

            // Восстанавливаем графическое состояние
            pdfCanvas.restoreState();
        }
    }
}
