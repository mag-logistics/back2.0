package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.StatusDTO;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import brigada4.mpi.maglogisticabackend.service.HunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hunter/")
public class HunterController {

    private final HunterService hunterService;

    @Autowired
    public HunterController(HunterService hunterService) {
        this.hunterService = hunterService;
    }

    @GetMapping
    public List<HunterApplicationDTO> getAllApplications() {
        return hunterService.getAllApplications();
    }

    @GetMapping("/getApplicationById")
    public HunterApplicationDTO getApplicationById(@RequestParam String id) {
        return hunterService.getApplicationById(id);
    }


    @PostMapping("/changeApplicationStatus")
    public HunterApplicationDTO changeApplicationStatus(@RequestParam String id, @RequestBody StatusDTO statusDTO) {
        return null;
    }

    @PostMapping("/closeApplication")
    public void closeApplication(@RequestBody HunterApplicationDTO hunterApplicationDTO) {

    }
}
