package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.service.HunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hunter/")
public class HunterController {

    private final HunterService hunterService;

    @Autowired
    public HunterController(HunterService hunterService) {
        this.hunterService = hunterService;
    }
}
