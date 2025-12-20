package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.MagicApplicationDTO;
import brigada4.mpi.maglogisticabackend.mapper.MagicApplicationMapper;
import brigada4.mpi.maglogisticabackend.models.ApplicationStatus;
import brigada4.mpi.maglogisticabackend.models.MagicAppPattern;
import brigada4.mpi.maglogisticabackend.models.MagicApplication;
import brigada4.mpi.maglogisticabackend.models.Magician;
import brigada4.mpi.maglogisticabackend.repositories.MagicAppPatternRepository;
import brigada4.mpi.maglogisticabackend.repositories.MagicApplicationRepository;
import brigada4.mpi.maglogisticabackend.repositories.MagicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class MagicianService {

    private final MagicianRepository magicianRepository;
    private final MagicApplicationMapper magicApplicationMapper;
    private final MagicApplicationRepository magicApplicationRepository;
    private final MagicAppPatternRepository magicAppPatternRepository;

    @Autowired
    public MagicianService(MagicianRepository magicianRepository, MagicApplicationMapper magicApplicationMapper, MagicApplicationRepository magicApplicationRepository, MagicAppPatternRepository magicAppPatternRepository) {
        this.magicianRepository = magicianRepository;
        this.magicApplicationMapper = magicApplicationMapper;
        this.magicApplicationRepository = magicApplicationRepository;
        this.magicAppPatternRepository = magicAppPatternRepository;
    }

    @Transactional
    public MagicApplication createMagicApplication(String magicianId, MagicApplicationDTO magicApplicationDTO) {
        Magician magician = magicianRepository.findById(magicianId).orElse(null);
        if (magician == null) {
            return null;
        }
        MagicApplication magicApplication = magicApplicationMapper.fromMagicApplicationDTO(magicApplicationDTO);
        magicApplication.setMagician(magician);
        Date date = new Date();
        magicApplication.setInitDate(date);
        magicApplication.setStatus(ApplicationStatus.CREATED);
        return magicApplicationRepository.save(magicApplication);
    }

    @Transactional
    public MagicAppPattern createAppPattern(String magicianId, MagicApplicationDTO magicApplicationDTO) {
        Magician magician = magicianRepository.findById(magicianId).orElse(null);
        if (magician == null) {
            return null;
        }
        MagicAppPattern pattern = magicApplicationMapper.fromMagicAppDTO(magicApplicationDTO);
        pattern.setMagician(magician);
        return  magicAppPatternRepository.save(pattern);
    }
}
