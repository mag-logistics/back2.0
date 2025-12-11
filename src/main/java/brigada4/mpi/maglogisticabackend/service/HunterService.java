package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.repositories.HunterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HunterService {

    private final HunterRepository hunterRepository;

    @Autowired
    public HunterService(HunterRepository hunterRepository) {
        this.hunterRepository = hunterRepository;
    }
}
