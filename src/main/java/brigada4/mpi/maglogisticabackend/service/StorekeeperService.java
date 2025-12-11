package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.repositories.StorekeeperRepository;
import org.springframework.stereotype.Service;

@Service
public class StorekeeperService {

    private final StorekeeperRepository storekeeperRepository;

    public StorekeeperService(StorekeeperRepository storekeeperRepository) {
        this.storekeeperRepository = storekeeperRepository;
    }
}
