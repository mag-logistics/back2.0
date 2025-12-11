package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.service.StorekeeperService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/storekeeper/")
public class StorekeeperController {

    private final StorekeeperService storekeeperService;

    public StorekeeperController(StorekeeperService storekeeperService) {
        this.storekeeperService = storekeeperService;
    }
}
