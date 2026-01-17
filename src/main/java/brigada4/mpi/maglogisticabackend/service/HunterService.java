package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterHuntingResultRequestDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterResponseDTO;
import brigada4.mpi.maglogisticabackend.dto.StatusDTO;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import brigada4.mpi.maglogisticabackend.repositories.HunterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface HunterService {

    List<HunterApplicationDTO> getAllApplications();

    HunterApplicationDTO getApplicationById(String id);

    List<HunterApplicationDTO> getMyApplications(String email);

    HunterApplicationDTO takeApplication(String email, String id);

    List<HunterResponseDTO> getMyResponses(String email);

    HunterResponseDTO completeApplication(String email, String applicationId, HunterHuntingResultRequestDTO result);

    ByteArrayInputStream generateReportOne(String userId, String applicationId);

    ByteArrayInputStream generateReportTwo(String userId, String applicationId);
}
