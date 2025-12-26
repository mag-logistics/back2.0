package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.*;
import brigada4.mpi.maglogisticabackend.mapper.EmployeesMapper;
import brigada4.mpi.maglogisticabackend.mapper.MagicApplicationMapper;
import brigada4.mpi.maglogisticabackend.models.*;
import brigada4.mpi.maglogisticabackend.service.MagicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/magician")
public class MagicianController {

    private final MagicianService magicianService;
    private final MagicApplicationMapper magicApplicationMapper;
    private final EmployeesMapper employeesMapper;

    @Autowired
    public MagicianController(MagicianService magicianService, MagicApplicationMapper magicApplicationMapper1, EmployeesMapper employeesMapper) {
        this.magicianService = magicianService;
        this.magicApplicationMapper = magicApplicationMapper1;
        this.employeesMapper = employeesMapper;
    }

    /**
     * 1. Подать заявку на получение магии (обработка данных, которые получаем с формы создания заявки на магию
     * В форме указывается (magicApplicationDTO):
     * - volume (объем магии)
     * - deadline (срок)
     * - magic (магия (из выпадающего списка))
     */
    @PostMapping("/createApp")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> createMagicApplication(@RequestParam String magicianId, @RequestBody MagicApplicationDTO magicApplicationDTO) {
        if (magicianId == null || magicApplicationDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        MagicApplicationDTO response = magicApplicationMapper.fromMagicApplication(magicianService.createMagicApplication(magicianId, magicApplicationDTO));
        return ResponseEntity.ok(response);
    }

    /**
     * 2. Создать шаблон для оформления заявки (отрабатывает, когда маг нажимает на кнопку на странице создания заявки)
     */
    @PostMapping("/createAppPattern")
    //    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> createMagicAppPattern(@RequestParam String magicianId, @RequestBody MagicApplicationDTO magicApplicationDTO) {
        if (magicianId == null || magicApplicationDTO == null) {
            return ResponseEntity.badRequest().body("Magician ID cannot be empty");
        }
        MagicApplicationDTO response = magicApplicationMapper.DTOFromPattern(magicianService.createAppPattern(magicianId, magicApplicationDTO));
        return ResponseEntity.ok(response);
    }

    /**
     * 3. Получить все шаблоны заявок для определенного мага.
     * (Данная функция должна вызываться на главной странице у мага, чтобы вывести ему все его шаблоны заявок)
     */
    @GetMapping("/getAllAppPatterns")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getAllMagicAppPatterns(@RequestParam String magicianId) {
        if (magicianId == null || magicianId.isEmpty()) {
            return ResponseEntity.badRequest().body("Magician ID cannot be empty");
        }
        List<MagicAppPattern> patterns = magicianService.getAllMagicAppPatterns(magicianId);
        List<MagicAppPatternDTO> response = magicApplicationMapper.listPatterDTOFromPatterns(patterns);
        return ResponseEntity.ok(response);
    }

    /**
     * 4.0 Получить всех сотрудников
     */
    @GetMapping("/getAllEmployees")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getAllEmployees() {
        List<UserDTO> employees = employeesMapper.fromUsers(magicianService.findAllEmployees());
        return ResponseEntity.ok(employees);
    }


    /**
     * 4.1 Получить всех кладовщиков
     */
    @GetMapping("/getAllStorekeepers")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getAllStorekeepers() {
        List<StorekeeperDTO> storekeepers = employeesMapper.toStorekeeperDTOList(magicianService.findAllStorekeepers());
        return ResponseEntity.ok(storekeepers);
    }

    /**
     * 4.2 Получить всех охотников
     */
    @GetMapping("/getAllHunters")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getAllHunters() {
        List<HunterDTO> hunters = employeesMapper.toHunterDTOList(magicianService.findAllHunters());
        return ResponseEntity.ok(hunters);
    }

    /**
     * 4.3 Получить всех высасывателей
     */
    @GetMapping("/getAllExtractors")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getAllExtractors() {
        List<ExtractorDTO> extractors = employeesMapper.toExtractorDTOList(magicianService.findAllExtractors());
        return ResponseEntity.ok(extractors);
    }

    /**
     * 5. Получить определенного сотрудника
     */
    @GetMapping("/getEmployee")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getEmployeeById(@RequestParam String employeeId) {
        if (employeeId == null || employeeId.isEmpty()) {
            return ResponseEntity.badRequest().body("Employee ID cannot be empty");
        }
        UserDTO userDTO = employeesMapper.fromUser(magicianService.findEmployeeById(employeeId));
        return ResponseEntity.ok(userDTO);
    }

    /**
     * 6. Назначить магическое поощрение
     */
    @PostMapping("/assignMagicalReward")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> assignMagicalReward(@RequestParam String employeeId, @RequestParam int rewardCount) {
        if (employeeId == null || employeeId.isEmpty()) {
            return ResponseEntity.badRequest().body("Employee ID cannot be empty");
        }
        UserDTO userDTO = employeesMapper.fromUser(magicianService.assignMagicalReward(employeeId, rewardCount));
        if (userDTO == null) {
            return ResponseEntity.badRequest().body("We can't find such employee");
        }
        return ResponseEntity.ok(userDTO);
    }

    /**
     * 7. Оформить поджопник
     */
    @PostMapping("/assignMagicalPenalty")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> assignMagicalPenalty(@RequestParam String employeeId, @RequestParam int penaltyCount) {
        if (employeeId == null || employeeId.isEmpty()) {
            return ResponseEntity.badRequest().body("Employee ID cannot be empty");
        }
        UserDTO userDTO = employeesMapper.fromUser(magicianService.assignMagicalPenalty(employeeId, penaltyCount));
        if (userDTO == null) {
            return ResponseEntity.badRequest().body("We can't find such employee");
        }
        return ResponseEntity.ok(userDTO);
    }

    /**
     * 8. Получение всех заявок на магию, которые связаны с конкретным магом по его id.
     */
    @GetMapping("/getAllMagicApp")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getAllMagicApp(@RequestParam String magicianId) {
        if (magicianId == null || magicianId.isEmpty()) {
            return ResponseEntity.badRequest().body("Magician ID cannot be empty");
        }
        List<MagicApplication> magicAppDTO = magicianService.getAllMagicApp(magicianId);
        List<MagicApplicationDTO> response = magicApplicationMapper.fromMagicApplications(magicAppDTO);
        return ResponseEntity.ok(response);
    }
}
