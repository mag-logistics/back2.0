package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.repositories.ExtractorRepository;
import org.springframework.stereotype.Service;

@Service
public class ExtractorService {

    private final ExtractorRepository extractorRepository;

    public ExtractorService(ExtractorRepository extractorRepository) {
        this.extractorRepository = extractorRepository;
    }
}
