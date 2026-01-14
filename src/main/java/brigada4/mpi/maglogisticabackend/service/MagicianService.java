package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.MagicApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.UserDTO;
import brigada4.mpi.maglogisticabackend.mapper.MagicApplicationMapper;
import brigada4.mpi.maglogisticabackend.models.*;
import brigada4.mpi.maglogisticabackend.repositories.*;
import com.itextpdf.io.font.PdfEncodings;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @Autowired
    public MagicianService(MagicianRepository magicianRepository, MagicApplicationMapper magicApplicationMapper, MagicApplicationRepository magicApplicationRepository, MagicAppPatternRepository magicAppPatternRepository, StorekeeperRepository storekeeperRepository, HunterRepository hunterRepository, ExtractorRepository extractorRepository, UserRepository userRepository) {
        this.magicianRepository = magicianRepository;
        this.magicApplicationMapper = magicApplicationMapper;
        this.magicApplicationRepository = magicApplicationRepository;
        this.magicAppPatternRepository = magicAppPatternRepository;
        this.storekeeperRepository = storekeeperRepository;
        this.hunterRepository = hunterRepository;
        this.extractorRepository = extractorRepository;
        this.userRepository = userRepository;
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
