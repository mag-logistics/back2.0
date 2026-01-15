package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.AnimalDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicDTO;
import brigada4.mpi.maglogisticabackend.mapper.AnimalMapper;
import brigada4.mpi.maglogisticabackend.mapper.MagicMapper;
import brigada4.mpi.maglogisticabackend.service.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/general/")
public class GeneralController {

    private final GeneralService generalSerivce;
    private final AnimalMapper animalMapper;
    private final MagicMapper magicMapper;
    private final MagicianService magicianService;
    private final StorekeeperService storekeeperService;
    private final ExtractorService extractorService;
    private final HunterService hunterService;

    public GeneralController(GeneralService generalService, AnimalMapper animalMapper, MagicMapper magicMapper, MagicianService magicianService, StorekeeperService storekeeperService, ExtractorService extractorService, HunterService hunterService) {
        this.generalSerivce = generalService;
        this.animalMapper = animalMapper;
        this.magicMapper = magicMapper;
        this.magicianService = magicianService;
        this.storekeeperService = storekeeperService;
        this.extractorService = extractorService;
        this.hunterService = hunterService;
    }

    /**
     * Здесь скорее всего нужно реализовывать функционал, связанный с отчетами
     * Логика такая, что делаем общий метод для всех и просто при генерации определяем от кого пришел запрос и выполняем сохранение в нужную папку fileStorage/...
     * в зависимости от пользователя, который отправил запрос.
     */

    /**
     * 1. Получение всех животных
     */
    @GetMapping("getAnimals")
    public ResponseEntity<?> getAllAnimals() {
        List<AnimalDTO> animals = animalMapper.animalsToAnimalDTOList(generalSerivce.getAllAnimals());
        return ResponseEntity.ok(animals);
    }

    /**
     * 2. Получение всех магиий
     */
    @GetMapping("getAllMagic")
    public ResponseEntity<?> getAllMagic() {
        List<MagicDTO> magicList = magicMapper.magicDTOListFromMagicList(generalSerivce.getAllMagic());
        return ResponseEntity.ok(magicList);
    }

    /**
     * Запрос на генерацию pdf отчета
     * @param userId (id пользователя, который делает запрос на генерацию отчета)
     * @param applicationId (id заявки, которую хотим обработать)
     * @param applicationType (Magic / Extraction / Hunter)
     * @return
     */
    @PostMapping("generateReportOne")
    public ResponseEntity<?> generateReportOne(@RequestParam String userId, @RequestParam String applicationId, @RequestParam String applicationType) {
        ByteArrayInputStream bis = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
        switch (applicationType) {
            case "Magic":
                bis = magicianService.generateReportOne(userId, applicationId);
                break;
            case "Extraction":
                bis = storekeeperService.generateReportOne(userId, applicationId);
                break;
            case "Hunter":
                bis = extractorService.generateReportOne(userId, applicationId);
                break;
            case "Hunter1":
                bis = hunterService.generateReportOne(userId, applicationId);
                break;
            default:
                return ResponseEntity.badRequest().body("Не найдено соответствующей заявки");
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    /**
     * Запрос на генерацию pdf отчета
     * @param userId (id пользователя, который делает запрос на генерацию отчета)
     * @param applicationId (id заявки, которую хотим обработать)
     * @param applicationType (Magic / Extraction / Hunter)
     * @return
     */
    @PostMapping("generateReportTwo")
    public ResponseEntity<?> generateReportTwo(@RequestParam String userId, @RequestParam String applicationId, @RequestParam String applicationType) {
        ByteArrayInputStream bis = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
        switch (applicationType) {
            case "Magic":
                bis = magicianService.generateReportTwo(userId, applicationId);
                break;
            case "Extraction":
                bis = storekeeperService.generateReportTwo(userId, applicationId);
                break;
            case "Hunter":
                bis = extractorService.generateReportTwo(userId, applicationId);
                break;
            case "Hunter1":
                bis = hunterService.generateReportTwo(userId, applicationId);
                break;
            default:
                return ResponseEntity.badRequest().body("Не найдено соответствующей заявки");
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


}
