package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicResponseDTO;
import brigada4.mpi.maglogisticabackend.mapper.ExtractionApplicationMapper;
import brigada4.mpi.maglogisticabackend.models.*;
import brigada4.mpi.maglogisticabackend.repositories.*;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
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
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class StorekeeperService {

    private final MagicRepository magicRepository;
    private final ExtractorRepository extractorRepository;
    private final HunterRepository hunterRepository;
    private final StorekeeperRepository storekeeperRepository;
    private final MagicApplicationRepository magicApplicationRepository;
    private final MagicResponseRepository magicResponseRepository;
    private final ExtractionApplicationMapper extractionApplicationMapper;
    private final ExtractionApplicationRepository extractionApplicationRepository;
    private final MagicStorageRepository magicStorageRepository;
    private final NotificationService notificationService;
    private final MagicianRepository magicianRepository;

    public StorekeeperService(MagicRepository magicRepository, ExtractorRepository extractorRepository, HunterRepository hunterRepository, StorekeeperRepository storekeeperRepository, MagicApplicationRepository magicApplicationRepository, MagicResponseRepository magicResponseRepository, ExtractionApplicationMapper extractionApplicationMapper, ExtractionApplicationRepository extractionApplicationRepository, MagicStorageRepository magicStorageRepository, NotificationService notificationService, MagicianRepository magicianRepository) {
        this.magicRepository = magicRepository;
        this.extractorRepository = extractorRepository;
        this.hunterRepository = hunterRepository;
        this.storekeeperRepository = storekeeperRepository;
        this.magicApplicationRepository = magicApplicationRepository;
        this.magicResponseRepository = magicResponseRepository;
        this.extractionApplicationMapper = extractionApplicationMapper;
        this.extractionApplicationRepository = extractionApplicationRepository;
        this.magicStorageRepository = magicStorageRepository;
        this.notificationService = notificationService;
        this.magicianRepository = magicianRepository;
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
        if (magicApplication.getMagician().getEmail() != null) {
            notificationService.sendMailAboutWorkingApplication(magicApplication.getMagician(), storekeeper);
        }
        return magicApplicationRepository.save(magicApplication);
    }

    public ByteArrayInputStream generateReportOne(String userId, String applicationId) {
        MagicApplication magicApplication = magicApplicationRepository.findById(applicationId).orElse(null);
        if (magicApplication == null) {
            return null;
        }
        Magician magician = magicianRepository.findById(magicApplication.getMagician().getId()).orElse(null);
        if (magician == null) {
            return null;
        }
        Magic magic = magicRepository.findById(magicApplication.getMagic().getId()).orElse(null);
        if (magic == null) {
            return null;
        }
        Storekeeper storekeeper = storekeeperRepository.findById(userId).orElse(null);
        if (storekeeper == null) {
            return null;
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
            document.add(new Paragraph("Applicant's full name : " + storekeeper.getSurname() + " " + storekeeper.getName() + " " + storekeeper.getPatronymic()).setFontSize(14));
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
            if (storekeeper != null && storekeeper.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Magician")));
                table.addCell(new Cell().add(new Paragraph(magician.getSurname() + " " + magician.getName() + " " + magician.getPatronymic())));
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
        MagicApplication magicApplication = magicApplicationRepository.findById(applicationId).orElse(null);
        if (magicApplication == null) {
            return null;
        }
        Magician magician = magicianRepository.findById(magicApplication.getMagician().getId()).orElse(null);
        if (magician == null) {
            return null;
        }
        Magic magic = magicRepository.findById(magicApplication.getMagic().getId()).orElse(null);
        if (magic == null) {
            return null;
        }
        Storekeeper storekeeper = storekeeperRepository.findById(userId).orElse(null);
        if (storekeeper == null) {
            return null;
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
                MagicianService.BackgroundEventHandler handler = new MagicianService.BackgroundEventHandler(imageData);
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
            document.add(new Paragraph("Applicant's full name : " + storekeeper.getSurname() + " " + storekeeper.getName() + " " + storekeeper.getPatronymic()).setFontSize(14));
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
            if (storekeeper != null && storekeeper.getId() != null) {
                table.addCell(new Cell().add(new Paragraph("Magician")));
                table.addCell(new Cell().add(new Paragraph(magician.getSurname() + " " + magician.getName() + " " + magician.getPatronymic())));
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
}
