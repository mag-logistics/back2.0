package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicResponseDTO;
import brigada4.mpi.maglogisticabackend.mapper.ExtractionApplicationMapper;
import brigada4.mpi.maglogisticabackend.mapper.MagicApplicationMapper;
import brigada4.mpi.maglogisticabackend.mapper.MagicResponseMapper;
import brigada4.mpi.maglogisticabackend.models.ExtractionApplication;
import brigada4.mpi.maglogisticabackend.models.MagicResponse;
import brigada4.mpi.maglogisticabackend.service.NotificationService;
import brigada4.mpi.maglogisticabackend.service.StorekeeperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/storekeeper/")
public class StorekeeperController {

    private final StorekeeperService storekeeperService;
    private final MagicApplicationMapper magicApplicationMapper;
    private final MagicResponseMapper magicResponseMapper;
    private final ExtractionApplicationMapper extractionApplicationMapper;
    private final NotificationService notificationService;

    public StorekeeperController(StorekeeperService storekeeperService, MagicApplicationMapper magicApplicationMapper, MagicResponseMapper magicResponseMapper, ExtractionApplicationMapper extractionApplicationMapper, NotificationService notificationService) {
        this.storekeeperService = storekeeperService;
        this.magicApplicationMapper = magicApplicationMapper;
        this.magicResponseMapper = magicResponseMapper;
        this.extractionApplicationMapper = extractionApplicationMapper;
        this.notificationService = notificationService;
    }

    /**
     * 1. Получить все заявки на магию со статусом CREATED
     */
    @GetMapping("/getAllMagicApp")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getAllMagicApplication() {
        List<MagicApplicationDTO> response = magicApplicationMapper.fromMagicApplications(storekeeperService.getAllMagicApplication());
        return ResponseEntity.ok(response);
    }

    /**
     * 2.1 Получить все заявки, которые уже связаны с нужным Storekeeper
     */
    @GetMapping("/getAllMagicAppByStorekeeper")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getAllMagicApplicationByStorekeeperId(@RequestParam String storekeeperId) {
        if (storekeeperId == null || storekeeperId.isEmpty()) {
            return ResponseEntity.badRequest().body("StorekeeperId shouldn't be empty");
        }
        List<MagicApplicationDTO> response = magicApplicationMapper.fromMagicApplications(storekeeperService.getMyMagicApplication(storekeeperId));
        return ResponseEntity.ok(response);
    }

    /**
     * 2.2 Получить все MagicResponse, которые оформил данный Storekeeper
     */
    @GetMapping("/getAllMagicResponses")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getAllMagicResponses(@RequestParam String storekeeperId) {
        if (storekeeperId == null || storekeeperId.isEmpty()) {
            return ResponseEntity.badRequest().body("StorekeeperId shouldn't be empty");
        }
        List<MagicResponseDTO> response = magicResponseMapper.magicResponseListToMagicResponseDTOList(storekeeperService.getMyMagicResponses(storekeeperId));
        return ResponseEntity.ok(response);
    }

    /**
     * 3. Найти требуемую заявку
     */
    @GetMapping("/getMagicApplication")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getMagicApplication(@RequestParam String magicAppId){
        MagicApplicationDTO magicApplicationDTO = magicApplicationMapper.fromMagicApplication(storekeeperService.getMagicApplication(magicAppId));
        return ResponseEntity.ok(magicApplicationDTO);
    }

    /**
     * 4. Обработать заявку на получение магии
     * По факту это обработка данных, которые получаем с формы, заполненную Кладовщиком.
     */
    @PostMapping("/processMagicApplication")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> processMagicApplication(@RequestParam String storekeeperId, @RequestParam String magicApplicationId, @RequestBody MagicResponseDTO magicResponseDTO) {
        if (storekeeperId == null || storekeeperId.isEmpty() || magicApplicationId == null || magicApplicationId.isEmpty() || magicResponseDTO == null) {
            return ResponseEntity.badRequest().body("Parameters shouldn't be empty");
        }
        MagicResponse magicResponse = storekeeperService.processMagicApplication(storekeeperId, magicApplicationId, magicResponseDTO);
        storekeeperService.saveResponseInMagicApplication(magicApplicationId, magicResponse);
        MagicResponseDTO response = magicResponseMapper.magicResponseToMagicResponseDTO(magicResponse);
        if (response == null) {
            return ResponseEntity.badRequest().body("MagicApp doesn't update");
        } else {
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 5. Проверить наличие требуемой магии
     * Тут короче мы берем тип магии и нужный нам объем, которые указаны в magicApplication (ищем по magicApplicationDTO)
     * Этот функционал должен срабатывать при нажатии на кнопку на странице Заявка на магию (то есть Кладовщик открывает заявку на магию и
     * видит кнопку "Проверить наличие магии". Нажимает на эту кнопку и получает в ответ либо Да, либо Нет.
     */
    @GetMapping("/checkMagicAvailability")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> checkMagicAvailability(@RequestParam String magicApplicationId) {
        if (magicApplicationId == null || magicApplicationId.isEmpty()) {
            return ResponseEntity.badRequest().body("Parameters shouldn't be empty");
        }
        boolean response = storekeeperService.checkMagicAvailability(magicApplicationId);
        return ResponseEntity.ok(response);
    }

    /**
     * 6. Создать заявку на высасывание
     */
    @PostMapping("/createExtractionApp")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> createExtractionApp(@RequestParam String storekeeperId, @RequestBody ExtractionApplicationDTO extractionApplicationDTO) {
        if (storekeeperId == null || storekeeperId.isEmpty() || extractionApplicationDTO == null) {
            return ResponseEntity.badRequest().body("Parameters shouldn't be empty");
        }

        ExtractionApplication extractionApplication = storekeeperService.createExtractionApp(storekeeperId, extractionApplicationDTO);
        if (extractionApplication != null) {
            storekeeperService.saveExtrAppInMagicApp(extractionApplicationDTO, extractionApplication);
        }
        ExtractionApplicationDTO response = extractionApplicationMapper.toDTO(extractionApplication);
        if (response == null) {
            return ResponseEntity.badRequest().body("ExtractionApp doesn't create");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/takeMagicApp")
    public ResponseEntity<?> takeMagicApplication(@RequestParam String storekeeperId, @RequestParam String magicApplicationId) {
        if (storekeeperId == null || storekeeperId.isEmpty() || magicApplicationId == null || magicApplicationId.isEmpty()) {
            return ResponseEntity.badRequest().body("Parameters shouldn't be empty");
        }
        MagicApplicationDTO magicApplicationDTO = magicApplicationMapper.fromMagicApplication(storekeeperService.takeMagicApplication(storekeeperId, magicApplicationId));
        if (magicApplicationDTO == null) {
            return ResponseEntity.badRequest().body("MagicApp doesn't update");
        }
        return ResponseEntity.ok(magicApplicationDTO);
    }
}
