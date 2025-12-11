package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.service.MagicianService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/magician/")
public class MagicianController {

    private final MagicianService magicianService;

    public MagicianController(final MagicianService magicianService) {
        this.magicianService = magicianService;
    }
}
