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
import brigada4.mpi.maglogisticabackend.service.MagicianService;
import brigada4.mpi.maglogisticabackend.service.NotificationService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private MagicStorageRepository magicStorageRepository;

    @Autowired
    private AnimalStorageRepository animalStorageRepository;

    @Autowired
    private NotificationService  notificationService;

    @Autowired
    private HunterRepository hunterRepository;


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
                .orElseThrow(() -> new NotFoundException("Пользователь " + email +" не найден"));

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
        hunterApplication.setAnimalCount(request.quantity());
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

        if (app.getStorekeeper().getEmail() != null) {
            notificationService.sendMailAboutWorkingApplication(app.getStorekeeper(), extractor);
        }

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
    public ExtractionResponseDTO completeApplication(String name, String applicationId) {

        ExtractionApplication app = extractionApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Заявка " + applicationId + " не найдена"));

        Magic magic = app.getMagic();
        int requiredVolume = app.getVolume();

        MagicStorage magicStorage = magicStorageRepository.findByMagicId(magic.getId())
                .orElseThrow(() -> new NotFoundException(
                        "Хранилище для магии " + magic.getId() + " не найдено"));

        List<Animal> animals = animalRepository.findByMagicId(magic.getId());
        List<AnimalStorage> animalStorages = animalStorageRepository.findAll();

        Map<String, AnimalStorage> storageByAnimalId = animalStorages.stream()
                .collect(Collectors.toMap(
                        s -> s.getAnimal().getId(),
                        Function.identity()
                ));

        List<Animal> animalsInStorage = animals.stream()
                .filter(a -> storageByAnimalId.containsKey(a.getId()))
                .toList();

        int collectedVolume = 0;

        for (Animal animal : animalsInStorage) {
            if (collectedVolume >= requiredVolume) break;

            AnimalStorage storage = storageByAnimalId.get(animal.getId());
            int availableCount = storage.getQuantity();
            int volumePerAnimal = animal.getMagicVolume();

            while (availableCount > 0 && collectedVolume < requiredVolume) {
                collectedVolume += volumePerAnimal;
                availableCount--;
            }

            storage.setQuantity(availableCount);
            animalStorageRepository.save(storage);
        }

        magicStorage.setVolume(magicStorage.getVolume() + requiredVolume);
        magicStorageRepository.save(magicStorage);

        ExtractionResponse extractionResponse = new ExtractionResponse(
                app.getId(),
                app.getExtractor(),
                new Date()
        );

        extractionResponseRepository.save(extractionResponse);

        app.setExtractionResponse(extractionResponse);
        app.setStatus(ApplicationStatus.FINISHED);

        extractionApplicationRepository.save(app);

        notificationService.sendMailAboutFinishingApplication(app.getStorekeeper(), app.getExtractor());

        return extractionResponseMapper.toDTO(extractionResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean canCollectMagic(String applicationId) {

        ExtractionApplication app = extractionApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException(
                        "Заявка " + applicationId + " не найдена"));

        Magic magic = app.getMagic();
        int requiredVolume = app.getVolume();

        List<Animal> animals = animalRepository.findByMagicId(magic.getId());
        List<AnimalStorage> animalStorages = animalStorageRepository.findAll();

        Map<String, AnimalStorage> storageByAnimalId = animalStorages.stream()
                .collect(Collectors.toMap(
                        s -> s.getAnimal().getId(),
                        Function.identity()
                ));

        int collectedVolume = 0;

        for (Animal animal : animals) {
            if (collectedVolume >= requiredVolume) {
                return true;
            }

            AnimalStorage storage = storageByAnimalId.get(animal.getId());
            if (storage == null || storage.getQuantity() == 0) {
                continue;
            }

            int availableCount = storage.getQuantity();
            int volumePerAnimal = animal.getMagicVolume();

            collectedVolume += availableCount * volumePerAnimal;
        }

        return collectedVolume >= requiredVolume;
    }

    @Override
    @Transactional
    public ByteArrayInputStream generateReportOne(String userId, String applicationId) {
        Extractor extractor = extractorRepository.findById(userId).orElse(null);
        ExtractionApplication extractionApplication = extractionApplicationRepository.findById(applicationId).orElse(null);
        if (extractor == null || extractionApplication == null) {
            return null;
        }
        Magic magic = magicRepository.findById(extractionApplication.getMagic().getId()).orElse(null);
        if (magic == null) {
            return null;
        }
        Storekeeper storekeeper = extractionApplication.getStorekeeper();
        HunterApplication hunterApplication = new HunterApplication();
        Hunter hunter = new Hunter();
        if (extractionApplication != null && extractionApplication.getHunterApp() != null) {
            hunterApplication = hunterApplicationRepository.findById(extractionApplication.getHunterApp().getId()).orElse(null);
        }
        if (hunterApplication != null && hunterApplication.getHunter() != null) {
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
            Paragraph title = new Paragraph("Report by extraction application with id:\n" + applicationId)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n")); // пустая строка

            // 4. Добавляем информацию
            document.add(new Paragraph("Applicant's full name : " + extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic()).setFontSize(14));
            document.add(new Paragraph("Application type: Extraction application").setFontSize(14));
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
        Extractor extractor = extractorRepository.findById(userId).orElse(null);
        ExtractionApplication extractionApplication = extractionApplicationRepository.findById(applicationId).orElse(null);
        if (extractor == null || extractionApplication == null) {
            return null;
        }
        Magic magic = magicRepository.findById(extractionApplication.getMagic().getId()).orElse(null);
        if (magic == null) {
            return null;
        }
        Storekeeper storekeeper = extractionApplication.getStorekeeper();
        HunterApplication hunterApplication = new HunterApplication();
        Hunter hunter = new Hunter();
        if (extractionApplication != null && extractionApplication.getHunterApp() != null) {
            hunterApplication = hunterApplicationRepository.findById(extractionApplication.getHunterApp().getId()).orElse(null);
        }
        if (hunterApplication != null && hunterApplication.getHunter() != null) {
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
                MagicianService.BackgroundEventHandler handler = new MagicianService.BackgroundEventHandler(imageData);
                pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, handler);
            } else {
                System.err.println("Фоновое изображение не найдено. Будет создан отчет без фона.");
            }

            // 3. Добавляем заголовок
            Paragraph title = new Paragraph("Report by extraction application with id:\n" + applicationId)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n")); // пустая строка

            // 4. Добавляем информацию
            document.add(new Paragraph("Applicant's full name : " + extractor.getSurname() + " " + extractor.getName() + " " + extractor.getPatronymic()).setFontSize(14));
            document.add(new Paragraph("Application type: Extraction application").setFontSize(14));
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
