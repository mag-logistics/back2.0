package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.ExtractionResponseDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.payload.request.CreateHunterApplicationRequest;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ExtractorService {

    List<ExtractionApplicationDTO> getAllApplications();

    List<ExtractionApplicationDTO> getMyApplications(String email);

    ExtractionApplicationDTO getApplicationById(String id);

    HunterApplicationDTO createHunterApplication(String email, String extrAppId, CreateHunterApplicationRequest request);

    ExtractionApplicationDTO takeApplication(String email, String application_id);

    List<ExtractionResponseDTO> getMyResponses(String email);

    ExtractionResponseDTO completeApplication(String name, String application_id);

    Boolean canCollectMagic(String applicationId);

    ByteArrayInputStream generateReportOne(String userId, String applicationId);

    ByteArrayInputStream generateReportTwo(String userId, String applicationId);
}

