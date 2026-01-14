package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.ExtractionResponseDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.payload.request.CreateHunterApplicationRequest;
import brigada4.mpi.maglogisticabackend.service.ExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/extractor")
public class ExtractorController {

    private final ExtractorService extractorService;

    @Autowired
    public ExtractorController(ExtractorService extractorService) {
        this.extractorService = extractorService;
    }

    @GetMapping("/applications")
    @ResponseStatus(HttpStatus.OK)
    public List<ExtractionApplicationDTO> getAllApplications() {
        return extractorService.getAllApplications();
    }

    @GetMapping("/my-applications")
    @ResponseStatus(HttpStatus.OK)
    public List<ExtractionApplicationDTO> getMyApplications(Authentication authentication) {
        return extractorService.getMyApplications(authentication.getName());
    }

    @GetMapping("/applications/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExtractionApplicationDTO getApplicationById(@PathVariable String id) {
        return extractorService.getApplicationById(id);
    }

    @PostMapping("/hunter-application")
    @ResponseStatus(HttpStatus.CREATED)
    public HunterApplicationDTO createHunterApplication(Authentication authentication, @RequestParam String extrAppId, @RequestBody CreateHunterApplicationRequest request) {
        return extractorService.createHunterApplication(authentication.getName(), extrAppId, request);
    }

    @PostMapping("/applications/{id}/take")
    @ResponseStatus(HttpStatus.OK)
    public ExtractionApplicationDTO takeApplication(Authentication authentication, @PathVariable String id) {
        return extractorService.takeApplication(authentication.getName(), id);
    }

    @GetMapping("/my-responses")
    @ResponseStatus(HttpStatus.OK)
    public List<ExtractionResponseDTO> getMyResponses(Authentication authentication) {
        return extractorService.getMyResponses(authentication.getName());
    }

    @PostMapping("/applications/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    public ExtractionResponseDTO completeApplication(Authentication authentication, @PathVariable String id) {
        return extractorService.completeApplication(authentication.getName(), id);
    }

}
