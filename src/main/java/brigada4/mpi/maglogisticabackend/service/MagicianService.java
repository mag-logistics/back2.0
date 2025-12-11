package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.repositories.MagicianRepository;
import org.springframework.stereotype.Service;

@Service
public class MagicianService {

    private final MagicianRepository magicianRepository;

    public MagicianService(MagicianRepository magicianRepository) {
        this.magicianRepository = magicianRepository;
    }
}
