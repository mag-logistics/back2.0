package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterResponseDTO;
import brigada4.mpi.maglogisticabackend.dto.StatusDTO;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import brigada4.mpi.maglogisticabackend.service.HunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/applications")
    @ResponseStatus(HttpStatus.OK)
    public List<HunterApplicationDTO> getAllApplications() {
        return hunterService.getAllApplications();
    }


    @GetMapping("/my-appplications")
    @ResponseStatus(HttpStatus.OK)
    public List<HunterApplicationDTO> getMyApplications(Authentication authentication) {
        return hunterService.getMyApplications(authentication.getName());
    }

    @GetMapping("/applications/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HunterApplicationDTO getApplicationById(@PathVariable String id) {
        return hunterService.getApplicationById(id);
    }

    @PostMapping("/applications/{id}/take")
    @ResponseStatus(HttpStatus.OK)
    public HunterApplicationDTO takeApplication(Authentication authentication, @PathVariable String id) {
        return hunterService.takeApplication(authentication.getName(), id);
    }

    @GetMapping("/my-responses")
    @ResponseStatus(HttpStatus.OK)
    public List<HunterResponseDTO> getMyResponses(Authentication authentication) {
        return hunterService.getMyResponses(authentication.getName());
    }

}
