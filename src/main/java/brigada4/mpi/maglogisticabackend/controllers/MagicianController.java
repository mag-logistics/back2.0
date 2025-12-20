package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.MagicApplicationDTO;
import brigada4.mpi.maglogisticabackend.mapper.MagicApplicationMapper;
import brigada4.mpi.maglogisticabackend.service.MagicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/magician")
public class MagicianController {

    private final MagicianService magicianService;
    private final MagicApplicationMapper magicApplicationMapper;

    @Autowired
    public MagicianController(MagicianService magicianService, MagicApplicationMapper magicApplicationMapper1) {
        this.magicianService = magicianService;
        this.magicApplicationMapper = magicApplicationMapper1;
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
            return ResponseEntity.badRequest().build();
        }
        MagicApplicationDTO response = magicApplicationMapper.DTOFromPattern(magicianService.createAppPattern(magicianId, magicApplicationDTO));
        return ResponseEntity.ok(response);
    }

    /**
     * 3. Получить все шаблоны заявок для определенного мага. (Данная функция должна вызываться на главной странице у мага, чтобы вывести ему все его шаблоны.
     */
    @GetMapping("/getAllAppPatterns")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getAllMagicAppPatterns(@RequestParam String magicianId) {

        return null;
    }

    /**
     * 4. Получить всех сотрудников
     */
    @GetMapping("/getAllEmployees")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getAllEmployees() {

        return null;
    }

    /**
     * 5. Получить определенного сотрудника
     */
    @GetMapping("/getEmployee")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> getEmployeeById(@RequestParam String employeeId) {

        return null;
    }

    /**
     * 6. Назначить магическое поощрение
     */
    @PostMapping("/assignMagicalReward")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> assignMagicalReward(@RequestParam String employeeId, @RequestParam int rewardCount) {

        return null;
    }

    /**
     * 7. Оформить поджопник
     */
    @PostMapping("/assignMagicalPenalty")
//    @PreAuthorize("hasAuthority('ROLE_MAGICIAN')")
    public ResponseEntity<?> assignMagicalPenalty(@RequestParam String employeeId, @RequestParam int penaltyCount) {

        return null;
    }
}
