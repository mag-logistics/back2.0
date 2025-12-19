package brigada4.mpi.maglogisticabackend.service.impl;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.StatusDTO;
import brigada4.mpi.maglogisticabackend.exception.NotFoundException;
import brigada4.mpi.maglogisticabackend.mapper.HunterApplicationMapper;
import brigada4.mpi.maglogisticabackend.repositories.ApplicationStatusRepository;
import brigada4.mpi.maglogisticabackend.repositories.HunterApplicationRepository;
import brigada4.mpi.maglogisticabackend.repositories.HunterRepository;
import brigada4.mpi.maglogisticabackend.repositories.HunterResponseRepository;
import brigada4.mpi.maglogisticabackend.service.HunterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HunterServiceImpl implements HunterService {

    private final HunterRepository hunterRepository;
    private final HunterApplicationRepository hunterApplicationRepository;
    private final HunterResponseRepository hunterResponseRepository;
    private final HunterApplicationMapper hunterApplicationMapper;
    private final ApplicationStatusRepository applicationStatusRepository;

    public HunterServiceImpl(HunterRepository hunterRepository, HunterApplicationRepository hunterApplicationRepository, HunterResponseRepository hunterResponseRepository, HunterApplicationMapper hunterApplicationMapper, ApplicationStatusRepository applicationStatusRepository) {
        this.hunterRepository = hunterRepository;
        this.hunterApplicationRepository = hunterApplicationRepository;
        this.hunterResponseRepository = hunterResponseRepository;
        this.hunterApplicationMapper = hunterApplicationMapper;
        this.applicationStatusRepository = applicationStatusRepository;
    }

    @Override
    public List<HunterApplicationDTO> getAllApplications() {
        return hunterApplicationRepository.findAll()
                .stream()
                .map(hunterApplicationMapper::toResponse)
                .toList();
    }

    @Override
    public HunterApplicationDTO getApplicationById(String id) {
        return hunterApplicationRepository.findById(id)
                .map(hunterApplicationMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Application with id " + id + " not found"));
    }

    @Override
    public HunterApplicationDTO changeApplicationStatus(String id, StatusDTO newStatus) throws IllegalAccessException {
        return null;
    }

}
