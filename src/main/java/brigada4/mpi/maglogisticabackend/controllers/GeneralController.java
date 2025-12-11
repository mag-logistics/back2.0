package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.service.GeneralService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/general/")
public class GeneralController {

    private final GeneralService generalSerivce;

    public GeneralController(GeneralService generalService) {
        this.generalSerivce = generalService;
    }

    /**
     * Здесь скорее всего нужно реализовывать функционал, связанный с отчетами
     * Логика такая, что делаем общий метод для всех и просто при генерации определяем от кого пришел запрос и выполняем сохранение в нужную папку fileStorage/...
     * в зависимости от пользователя, который отправил запрос.
     */

}
