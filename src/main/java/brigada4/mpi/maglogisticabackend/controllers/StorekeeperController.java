package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicResponseDTO;
import brigada4.mpi.maglogisticabackend.service.StorekeeperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/storekeeper/")
public class StorekeeperController {

    private final StorekeeperService storekeeperService;

    public StorekeeperController(StorekeeperService storekeeperService) {
        this.storekeeperService = storekeeperService;
    }

    /**
     * 1. Получить все заявки на магию со статусом CREATED
     */
    @GetMapping("/getAllMagicApp")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getAllMagicApplication() {

        return null;
    }

    /**
     * 2. Получить все заявки, которые уже связаны с нужным Storekeeper
     */
    @GetMapping("/getAllMagicAppByStorekeeper")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getAllMagicApplicationByStorekeeperId(@RequestParam String storekeeperId) {

        return null;
    }

    /**
     * 3. Найти требуемую заявку
     */
    @GetMapping("/getAllMagicAppByStorekeeper")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> getMagicApplication(@RequestParam String magicAppId){

        return null;
    }

    /**
     * 4. Обработать заявку на получение магии
     * По факту это обработка данных, которые получаем с формы, заполненную Кладовщиком.
     */
    @PostMapping("/processMagicApplication")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> processMagicApplication(@RequestParam String storekeeperId, @RequestParam String magicApplicationId, @RequestBody MagicResponseDTO magicResponseDTO) {

        return null;
    }

    /**
     * 5. Проверить наличие требуемой магии
     * Тут короче мы берем тип магии и нужный нам объем, которые указаны в magicApplication (ищем по magicApplicationDTO)
     * Этот функционал должен срабатывать при нажатии на кнопку на странице Заявка на магию (то есть Кладовщик открывает заявку на магию и
     * видит кнопку "Проверить наличие магии". Нажимает на эту кнопку и получает в ответ либо Да, либо Нет.
     */
    @GetMapping("/checkMagicAvailability")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> checkMagicAvailability(@RequestParam String storekeeperId, @RequestParam String magicApplicationId) {

        return null;
    }

    /**
     * 6. Создать заявку на высасывание
     */
    @PostMapping("/createExtractionApp")
//    @PreAuthorize("hasAuthority('ROLE_STOREKEEPER')")
    public ResponseEntity<?> createExtractionApp(@RequestParam String storekeeperId, @RequestBody ExtractionApplicationDTO extractionApplicationDTO) {

        return null;
    }
}
