package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.AnimalDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicDTO;
import brigada4.mpi.maglogisticabackend.mapper.AnimalMapper;
import brigada4.mpi.maglogisticabackend.mapper.MagicMapper;
import brigada4.mpi.maglogisticabackend.service.GeneralService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/general/")
public class GeneralController {

    private final GeneralService generalSerivce;
    private final AnimalMapper animalMapper;
    private final MagicMapper magicMapper;

    public GeneralController(GeneralService generalService, AnimalMapper animalMapper, MagicMapper magicMapper) {
        this.generalSerivce = generalService;
        this.animalMapper = animalMapper;
        this.magicMapper = magicMapper;
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

}
