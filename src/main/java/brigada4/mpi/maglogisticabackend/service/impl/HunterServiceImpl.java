package brigada4.mpi.maglogisticabackend.service.impl;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterResponseDTO;
import brigada4.mpi.maglogisticabackend.dto.StatusDTO;
import brigada4.mpi.maglogisticabackend.exception.ConflictException;
import brigada4.mpi.maglogisticabackend.exception.NotFoundException;
import brigada4.mpi.maglogisticabackend.mapper.HunterApplicationMapper;
import brigada4.mpi.maglogisticabackend.mapper.HunterResponseMapper;
import brigada4.mpi.maglogisticabackend.models.ApplicationStatus;
import brigada4.mpi.maglogisticabackend.models.Hunter;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import brigada4.mpi.maglogisticabackend.models.User;
import brigada4.mpi.maglogisticabackend.repositories.HunterApplicationRepository;
import brigada4.mpi.maglogisticabackend.repositories.HunterRepository;
import brigada4.mpi.maglogisticabackend.repositories.HunterResponseRepository;
import brigada4.mpi.maglogisticabackend.repositories.UserRepository;
import brigada4.mpi.maglogisticabackend.service.HunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HunterServiceImpl implements HunterService {

    @Autowired
    private HunterApplicationRepository hunterApplicationRepository;

    @Autowired
    private HunterApplicationMapper hunterApplicationMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HunterRepository hunterRepository;

    @Autowired
    private HunterResponseRepository hunterResponseRepository;

    @Autowired
    private HunterResponseMapper hunterResponseMapper;


    @Override
    public List<HunterApplicationDTO> getAllApplications() {
        return hunterApplicationRepository.findAllByStatus(ApplicationStatus.CREATED)
                .stream()
                .map(hunterApplicationMapper::toDTO)
                .toList();
    }

    @Override
    public HunterApplicationDTO getApplicationById(String id) {
        return hunterApplicationRepository.findById(id)
                .map(hunterApplicationMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Заявка " + id + " не найдена"));
    }

    @Override
    public List<HunterApplicationDTO> getMyApplications(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь " + email + " не найден"));

        return hunterApplicationRepository.findByHunterId(user.getId())
                .stream()
                .map(hunterApplicationMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public HunterApplicationDTO takeApplication(String email, String application_id) {
        HunterApplication app = hunterApplicationRepository.findById(application_id)
                .orElseThrow(() -> new NotFoundException("Заявка " + application_id + " не найдена"));

        if (app.getHunter() != null) {
            throw new ConflictException("Заявка уже взята");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь " + email + " не найден"));

        Hunter hunter = hunterRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Охотник " + user.getId() + " не найден"));

        app.setHunter(hunter);
        app.setStatus(ApplicationStatus.WORKED);

        hunterApplicationRepository.save(app);

        return hunterApplicationMapper.toDTO(app);
    }

    @Override
    public List<HunterResponseDTO> getMyResponses(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь " + email + " не найден"));

        Hunter hunter = hunterRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Охотник " + user.getId() + " не найден"));

        return hunterResponseRepository.findAllByHunterId(hunter.getId())
                .stream()
                .map(hunterResponseMapper::toDTO)
                .toList();
    }

}
