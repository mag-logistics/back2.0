package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.payload.CreateHunterApplicationRequest;
import brigada4.mpi.maglogisticabackend.repositories.ExtractorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ExtractorService {

    List<ExtractionApplicationDTO> getAllApplications();

    ExtractionApplicationDTO getApplicationById(String id);

    HunterApplicationDTO createHunterApplication(String extractorId, CreateHunterApplicationRequest request);

}
