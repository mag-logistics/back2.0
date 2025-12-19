package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.StatusDTO;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import brigada4.mpi.maglogisticabackend.repositories.HunterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HunterService {

    List<HunterApplicationDTO> getAllApplications();

    HunterApplicationDTO getApplicationById(String id);

    HunterApplicationDTO changeApplicationStatus(String id, StatusDTO statusDTO) throws IllegalAccessException;
}
