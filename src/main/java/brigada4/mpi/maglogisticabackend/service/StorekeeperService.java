package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicResponseDTO;
import brigada4.mpi.maglogisticabackend.mapper.ExtractionApplicationMapper;
import brigada4.mpi.maglogisticabackend.mapper.MagicMapper;
import brigada4.mpi.maglogisticabackend.models.*;
import brigada4.mpi.maglogisticabackend.repositories.*;
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
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StorekeeperService {

    private final StorekeeperRepository storekeeperRepository;
    private final MagicApplicationRepository magicApplicationRepository;
    private final MagicResponseRepository magicResponseRepository;
    private final ExtractionApplicationMapper extractionApplicationMapper;
    private final ExtractionApplicationRepository extractionApplicationRepository;
    private final MagicStorageRepository magicStorageRepository;
    private final NotificationService notificationService;

    public StorekeeperService(StorekeeperRepository storekeeperRepository, MagicApplicationRepository magicApplicationRepository, MagicResponseRepository magicResponseRepository, ExtractionApplicationMapper extractionApplicationMapper, ExtractionApplicationRepository extractionApplicationRepository, MagicStorageRepository magicStorageRepository, NotificationService notificationService) {
        this.storekeeperRepository = storekeeperRepository;
        this.magicApplicationRepository = magicApplicationRepository;
        this.magicResponseRepository = magicResponseRepository;
        this.extractionApplicationMapper = extractionApplicationMapper;
        this.extractionApplicationRepository = extractionApplicationRepository;
        this.magicStorageRepository = magicStorageRepository;
        this.notificationService = notificationService;
    }

    public List<MagicApplication> getAllMagicApplication() {
        return magicApplicationRepository.findAllByStatus(ApplicationStatus.CREATED);
    }


    public List<MagicApplication> getMyMagicApplication(String storekeeperId) {
        return magicApplicationRepository.findAllByStorekeeperId(storekeeperId);
    }

    public List<MagicResponse> getMyMagicResponses(String storekeeperId) {
        return magicResponseRepository.findAllByStorekeeperId(storekeeperId);
    }

    public MagicApplication getMagicApplication(String magicAppId) {
        return magicApplicationRepository.findById(magicAppId).orElse(null);
    }

    @Transactional
    public MagicResponse processMagicApplication(String storekeeperId, String magicApplicationId, MagicResponseDTO magicResponseDTO) {
        MagicResponse magicResponse = new MagicResponse();
        Storekeeper storekeeper = storekeeperRepository.findById(storekeeperId).orElse(null);
        MagicApplication magicApplication = magicApplicationRepository.findById(magicApplicationId).orElse(null);
        if (storekeeper == null || magicApplication == null) {
            return null;
        }
        magicResponse.setStorekeeper(storekeeper);
        magicResponse.setMagicApplicationId(magicApplication.getId());

        magicResponse.setDate(magicResponseDTO.getDate());

        if (magicApplication.getMagician().getEmail() != null) {
            notificationService.sendMailAboutFinishingApplication(magicApplication.getMagician(), storekeeper);
        }

        return magicResponseRepository.save(magicResponse);
    }

    @Transactional
    public ExtractionApplication createExtractionApp(String storekeeperId, ExtractionApplicationDTO extractionApplicationDTO) {
        Storekeeper storekeeper = storekeeperRepository.findById(storekeeperId).orElse(null);
        if (storekeeper == null) {
            return null;
        }

        ExtractionApplication extractionApplication = extractionApplicationMapper.toEntity(extractionApplicationDTO);
        MagicApplication magicApp = magicApplicationRepository.findById(extractionApplicationDTO.magicApp().getId()).orElse(null);
        if (magicApp == null) {
            return null;
        }
        extractionApplication.setMagicAppId(magicApp.getId());
        Magic magic = magicApp.getMagic();
        extractionApplication.setMagic(magic);
        extractionApplication.setStorekeeper(storekeeper);
        Date date = new Date();
        extractionApplication.setInitDate(date);
        extractionApplication.setStatus(ApplicationStatus.CREATED);
        return extractionApplicationRepository.save(extractionApplication);
    }

    public boolean checkMagicAvailability(String magicApplicationId) {
        MagicApplication magicApplication = magicApplicationRepository.findById(magicApplicationId).orElse(null);
        if (magicApplication == null) {
            return false;
        }
        MagicStorage magicStorage = magicStorageRepository.findByMagicId(magicApplication.getMagic().getId()).orElse(null);
        if (magicStorage == null) {
            return false;
        }
        return magicApplication.getVolume() <= magicStorage.getVolume();
    }

    @Transactional
    public void saveResponseInMagicApplication(String magicApplicationId, MagicResponse magicResponse) {
        MagicApplication magicApplication = magicApplicationRepository.findById(magicApplicationId).orElse(null);
        if (magicApplication == null) {
            return;
        }
        magicApplication.setMagicResponse(magicResponse);
        magicApplication.setStatus(ApplicationStatus.FINISHED);
        magicApplicationRepository.save(magicApplication);
        MagicStorage magicStorage = magicStorageRepository.findByMagicId(magicApplication.getMagic().getId()).orElse(null);
        if (magicStorage != null) {
            magicStorage.setVolume(magicStorage.getVolume() - magicApplication.getVolume());
        }
        magicApplicationRepository.save(magicApplication);
    }

    @Transactional
    public void saveExtrAppInMagicApp(ExtractionApplicationDTO extractionApplicationDTO, ExtractionApplication extractionApplication) {
        MagicApplication magicApplication = magicApplicationRepository.findById(extractionApplicationDTO.magicApp().getId()).orElse(null);
        if (magicApplication == null) {
            return;
        }
        magicApplication.setExtractionApp(extractionApplication);
        magicApplicationRepository.save(magicApplication);
    }

    @Transactional
    public MagicApplication takeMagicApplication(String storekeeperId, String magicApplicationId) {
        Storekeeper storekeeper = storekeeperRepository.findById(storekeeperId).orElse(null);
        MagicApplication magicApplication = magicApplicationRepository.findById(magicApplicationId).orElse(null);
        if (magicApplication == null || storekeeper == null) {
            return null;
        }
        magicApplication.setStorekeeper(storekeeper);
        magicApplication.setStatus(ApplicationStatus.WORKED);
        notificationService.sendMailAboutWorkingApplication(magicApplication.getMagician(), storekeeper);
        return magicApplicationRepository.save(magicApplication);
    }

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
