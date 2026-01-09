package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.payload.CreateHunterApplicationRequest;
import brigada4.mpi.maglogisticabackend.service.ExtractorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ExtractionApplicationDTO> getAllApplications() {
        return extractorService.getAllApplications();
    }

    @GetMapping("/applications/{id}")
    public ExtractionApplicationDTO getApplicationById(@PathVariable String id) {
        return extractorService.getApplicationById(id);
    }

    @PostMapping("/hunter-application")
    public HunterApplicationDTO createHunterApplication(@RequestBody CreateHunterApplicationRequest request) {
        return extractorService.createHunterApplication(request);
    }

}
