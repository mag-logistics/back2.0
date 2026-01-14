package brigada4.mpi.maglogisticabackend.service.impl;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.ExtractionResponseDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.exception.ConflictException;
import brigada4.mpi.maglogisticabackend.exception.NotFoundException;
import brigada4.mpi.maglogisticabackend.mapper.ExtractionApplicationMapper;
import brigada4.mpi.maglogisticabackend.mapper.ExtractionResponseMapper;
import brigada4.mpi.maglogisticabackend.mapper.HunterApplicationMapper;
import brigada4.mpi.maglogisticabackend.models.*;
import brigada4.mpi.maglogisticabackend.payload.request.CreateHunterApplicationRequest;
import brigada4.mpi.maglogisticabackend.repositories.*;
import brigada4.mpi.maglogisticabackend.service.ExtractorService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExtractorServiceImpl implements ExtractorService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExtractionApplicationRepository extractionApplicationRepository;

    @Autowired
    private ExtractionApplicationMapper extractionApplicationMapper;

    @Autowired
    private HunterApplicationRepository hunterApplicationRepository;

    @Autowired
    private HunterApplicationMapper hunterApplicationMapper;

    @Autowired
    private ExtractionResponseMapper extractionResponseMapper;

    @Autowired
    private MagicRepository magicRepository;

    @Autowired
    private ExtractorRepository extractorRepository;

    @Autowired
    private ExtractionResponseRepository extractionResponseRepository;
    @Autowired
    private AnimalRepository animalRepository;


    @Override
    public List<ExtractionApplicationDTO> getAllApplications() {
        List<ExtractionApplication> list = extractionApplicationRepository.findAllByStatus(ApplicationStatus.CREATED);

        return list
                .stream()
                .map(extractionApplicationMapper::toDTO)
                .toList();
    }

    @Override
    public List<ExtractionApplicationDTO> getMyApplications(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Кладовщик " + email +" не найден"));

        return extractionApplicationRepository.findAllByExtractorId(user.getId())
                .stream()
                .map(extractionApplicationMapper::toDTO)
                .toList();
    }

    @Override
    public ExtractionApplicationDTO getApplicationById(String id) {
        return extractionApplicationRepository.findById(id)
                .map(extractionApplicationMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Заявка " + id + " не найдена"));
    }

    @Override
    @Transactional
    public HunterApplicationDTO createHunterApplication(String email, String extrAppId, CreateHunterApplicationRequest request) {

        ExtractionApplication extractionApplication = extractionApplicationRepository.findById(extrAppId)
                .orElseThrow(() -> new NotFoundException("ExtractionApp " + extrAppId + " не найдена"));

        Magic magic = magicRepository.findById(request.magicId())
                .orElseThrow(() -> new NotFoundException("Магия " + request.magicId() + " не найдена"));

        Animal animal = animalRepository.findById(request.animalId())
                .orElseThrow(() -> new NotFoundException("Животное " + request.animalId() + " не найдено"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь " + email + " не найден"));

        Extractor extractor = extractorRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Высасыватель " + user.getId() + " не найден"));

        HunterApplication hunterApplication = hunterApplicationMapper.toEntity(request);
        hunterApplication.setExtractionAppId(extrAppId);
        hunterApplication.setExtractor(extractor);
        hunterApplication.setMagic(magic);
        hunterApplication.setAnimal(animal);
        Date date = new Date();
        hunterApplication.setInitDate(date);
        hunterApplication.setStatus(ApplicationStatus.CREATED);

        hunterApplicationRepository.save(hunterApplication);

        return hunterApplicationMapper.toDTO(hunterApplication);
    }

    @Override
    @Transactional
    public ExtractionApplicationDTO takeApplication(String email, String application_id) {
        ExtractionApplication app = extractionApplicationRepository.findById(application_id)
                .orElseThrow(() -> new NotFoundException("Заявка " + application_id + "не найдена "));

        if (app.getExtractor() != null) {
            throw new ConflictException("Заявка уже взята");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь " + email + " не найден"));

        Extractor extractor = extractorRepository.findById(user.getId())
                        .orElseThrow(() -> new NotFoundException("Высасыватель " + user.getId() + " не найден"));


        app.setExtractor(extractor);
        app.setStatus(ApplicationStatus.WORKED);

        extractionApplicationRepository.save(app);

        return extractionApplicationMapper.toDTO(app);
    }

    @Override
    public List<ExtractionResponseDTO> getMyResponses(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь " + email + " не найден"));

        Extractor extractor = extractorRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Высасыватель " + user.getId() + " не найден"));

        return extractionResponseRepository.findAllByExtractorId(extractor.getId())
                .stream()
                .map(extractionResponseMapper::toDTO)
                .toList();
    }


    @Override
    @Transactional
    public ByteArrayInputStream generateReportOne(String userId, String applicationId) {
        // Создаем поток для записи PDF в память
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // 1. Инициализация PDF документа
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // 2. Загружаем шрифт с поддержкой кириллицы
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            document.setFont(font);

            // 3. Добавляем заголовок
            Paragraph title = new Paragraph("ОТЧЕТ ПО ЗАЯВКЕ")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n")); // пустая строка

            // 4. Добавляем информацию
            document.add(new Paragraph("ID пользователя: " + userId));
            document.add(new Paragraph("ID заявки: " + applicationId));
            document.add(new Paragraph("Дата генерации: " + java.time.LocalDate.now()));

            document.add(new Paragraph("\n")); // пустая строка

            // 5. Добавляем таблицу с данными
            Table table = new Table(UnitValue.createPercentArray(new float[]{30, 70}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Заголовки таблицы
            table.addHeaderCell(new Cell().add(new Paragraph("Поле").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Значение").setBold()));

            // Данные таблицы (пример)
            table.addCell(new Cell().add(new Paragraph("Статус")));
            table.addCell(new Cell().add(new Paragraph("Обработана")));

            table.addCell(new Cell().add(new Paragraph("Тип")));
            table.addCell(new Cell().add(new Paragraph("Магическая")));

            table.addCell(new Cell().add(new Paragraph("Сумма")));
            table.addCell(new Cell().add(new Paragraph("15 000 руб.")));

            document.add(table);

            document.add(new Paragraph("\n")); // пустая строка

            // 6. Добавляем подпись
            Paragraph signature = new Paragraph("Генератор отчетов\nСистема управления заявками")
                    .setFontSize(10)
                    .setItalic()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT);
            document.add(signature);

            // 7. Закрываем документ
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при генерации PDF: " + e.getMessage(), e);
        }

        // 8. Возвращаем InputStream
        return new ByteArrayInputStream(out.toByteArray());
    }


}
