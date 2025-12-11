package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.service.ExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/extractor/")
public class ExtractorController {

    private final ExtractorService extractorService;

    @Autowired
    public ExtractorController(ExtractorService extractorService) {
        this.extractorService = extractorService;
    }

}
