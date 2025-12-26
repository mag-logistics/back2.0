package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.MagicApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.UserDTO;
import brigada4.mpi.maglogisticabackend.mapper.MagicApplicationMapper;
import brigada4.mpi.maglogisticabackend.models.*;
import brigada4.mpi.maglogisticabackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MagicianService {

    private final MagicianRepository magicianRepository;
    private final MagicApplicationMapper magicApplicationMapper;
    private final MagicApplicationRepository magicApplicationRepository;
    private final MagicAppPatternRepository magicAppPatternRepository;
    private final StorekeeperRepository storekeeperRepository;
    private final HunterRepository hunterRepository;
    private final ExtractorRepository extractorRepository;
    private final UserRepository userRepository;

    @Autowired
    public MagicianService(MagicianRepository magicianRepository, MagicApplicationMapper magicApplicationMapper, MagicApplicationRepository magicApplicationRepository, MagicAppPatternRepository magicAppPatternRepository, StorekeeperRepository storekeeperRepository, HunterRepository hunterRepository, ExtractorRepository extractorRepository, UserRepository userRepository) {
        this.magicianRepository = magicianRepository;
        this.magicApplicationMapper = magicApplicationMapper;
        this.magicApplicationRepository = magicApplicationRepository;
        this.magicAppPatternRepository = magicAppPatternRepository;
        this.storekeeperRepository = storekeeperRepository;
        this.hunterRepository = hunterRepository;
        this.extractorRepository = extractorRepository;
        this.userRepository = userRepository;
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

    public List<MagicApplication> getAllMagicApp(String magicianId) {
        return magicApplicationRepository.findAllByMagician(magicianId);
    }


    public List<MagicAppPattern> getAllMagicAppPatterns(String magicianId) {
        return magicAppPatternRepository.findAllByMagician(magicianId);
    }

    public List<Storekeeper> findAllStorekeepers() {
        return storekeeperRepository.findAll();
    }

    public List<Hunter> findAllHunters() {
        return hunterRepository.findAll();
    }

    public List<Extractor> findAllExtractors() {
        return extractorRepository.findAll();
    }

    public User findEmployeeById(String employeeId) {
        User user = userRepository.findById(employeeId).orElse(null);
        return user;
    }

    public User assignMagicalReward(String employeeId, int rewardCount) {
        User user = userRepository.findById(employeeId).orElse(null);
        if (user == null) {
            return null;
        }
        int oldRewardPoints = user.getRewardPoints();
        user.setRewardPoints(oldRewardPoints + rewardCount);
        return userRepository.save(user);
    }

    public User assignMagicalPenalty(String employeeId, int penaltyCount) {
        User user = userRepository.findById(employeeId).orElse(null);
        if (user == null) {
            return null;
        }
        int oldPenaltyPoints = user.getPenaltyPoints();
        user.setPenaltyPoints(oldPenaltyPoints + penaltyCount);
        return userRepository.save(user);
    }

    public List<User> findAllEmployees() {
        List<Storekeeper> storekeepers = storekeeperRepository.findAll();
        List<Hunter> hunters = hunterRepository.findAll();
        List<Extractor> extractors = extractorRepository.findAll();
        List<User> users = new ArrayList<>();
        users.addAll(storekeepers);
        users.addAll(hunters);
        users.addAll(extractors);
        return users;
    }
}
