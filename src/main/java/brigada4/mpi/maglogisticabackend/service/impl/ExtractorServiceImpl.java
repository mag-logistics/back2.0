package brigada4.mpi.maglogisticabackend.service.impl;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.exception.NotFoundException;
import brigada4.mpi.maglogisticabackend.mapper.ExtractionApplicationMapper;
import brigada4.mpi.maglogisticabackend.mapper.HunterApplicationMapper;
import brigada4.mpi.maglogisticabackend.models.ApplicationStatus;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import brigada4.mpi.maglogisticabackend.payload.CreateHunterApplicationRequest;
import brigada4.mpi.maglogisticabackend.repositories.ExtractionApplicationRepository;
import brigada4.mpi.maglogisticabackend.repositories.ExtractorRepository;
import brigada4.mpi.maglogisticabackend.repositories.HunterApplicationRepository;
import brigada4.mpi.maglogisticabackend.repositories.MagicRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExtractorServiceImpl implements ExtractorService {

//    private final ExtractorRepository extractorRepository;
    private final ExtractionApplicationRepository extractionApplicationRepository;
    private final ExtractionApplicationMapper extractionApplicationMapper;

    private final HunterApplicationRepository hunterApplicationRepository;
    private final HunterApplicationMapper hunterApplicationMapper;

    private final MagicRepository magicRepository;


    public ExtractorServiceImpl(/*ExtractorRepository extractorRepository,*/ ExtractionApplicationRepository extractionApplicationRepository, ExtractionApplicationMapper extractionApplicationMapper, HunterApplicationMapper hunterApplicationMapper, HunterApplicationRepository hunterApplicationRepository, MagicRepository magicRepository) {
//        this.extractorRepository = extractorRepository;
        this.magicRepository = magicRepository;
        this.extractionApplicationRepository = extractionApplicationRepository;
        this.extractionApplicationMapper = extractionApplicationMapper;
        this.hunterApplicationMapper = hunterApplicationMapper;
        this.hunterApplicationRepository = hunterApplicationRepository;
    }

    @Override
    public List<ExtractionApplicationDTO> getAllApplications() {
        return extractionApplicationRepository.findAll()
                .stream()
                .map(extractionApplicationMapper::toDTO)
                .toList();
    }

    @Override
    public ExtractionApplicationDTO getApplicationById(String id) {
        return extractionApplicationRepository.findById(id)
                .map(extractionApplicationMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Application with id " + id + " not found"));
    }

    @Override
    @Transactional
    public HunterApplicationDTO createHunterApplication(CreateHunterApplicationRequest request) {
//        extractorRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Extractor with id " + id + " not found"));

        magicRepository.findById(request.magicId())
                .orElseThrow(() -> new NotFoundException("Magic with id " + request.magicId() + " not found"));

        HunterApplication hunterApplication = hunterApplicationMapper.toEntity(request);

        Date date = new Date();
        hunterApplication.setInitDate(new Date());
        hunterApplication.setStatus(ApplicationStatus.CREATED);
        date.setTime(date.getTime() + 24 * 60 * 60 * 1000);

        hunterApplicationRepository.save(hunterApplication);

        return hunterApplicationMapper.toDTO(hunterApplication);
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
