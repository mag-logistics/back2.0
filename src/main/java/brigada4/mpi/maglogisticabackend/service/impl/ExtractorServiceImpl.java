package brigada4.mpi.maglogisticabackend.service.impl;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.exception.NotFoundException;
import brigada4.mpi.maglogisticabackend.mapper.ExtractionApplicationMapper;
import brigada4.mpi.maglogisticabackend.mapper.HunterApplicationMapper;
import brigada4.mpi.maglogisticabackend.models.ApplicationStatus;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import brigada4.mpi.maglogisticabackend.payload.CreateHunterApplicationRequest;
import brigada4.mpi.maglogisticabackend.repositories.ExtractionApplicationRepository;
import brigada4.mpi.maglogisticabackend.repositories.ExtractorRepository;
import brigada4.mpi.maglogisticabackend.repositories.HunterApplicationRepository;
import brigada4.mpi.maglogisticabackend.repositories.MagicRepository;
import brigada4.mpi.maglogisticabackend.service.ExtractorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ExtractorServiceImpl implements ExtractorService {

//    private final ExtractorRepository extractorRepository;
    private final ExtractionApplicationRepository extractionApplicationRepository;
    private final ExtractionApplicationMapper extractionApplicationMapper;

    private final HunterApplicationRepository hunterApplicationRepository;
    private final HunterApplicationMapper hunterApplicationMapper;

    private final MagicRepository magicRepository;


    public ExtractorServiceImpl(/*ExtractorRepository extractorRepository,*/ ExtractionApplicationRepository extractionApplicationRepository, ExtractionApplicationMapper extractionApplicationMapper, HunterApplicationMapper hunterApplicationMapper, HunterApplicationRepository hunterApplicationRepository, MagicRepository magicRepository) {
//        this.extractorRepository = extractorRepository;
        this.magicRepository = magicRepository;
        this.extractionApplicationRepository = extractionApplicationRepository;
        this.extractionApplicationMapper = extractionApplicationMapper;
        this.hunterApplicationMapper = hunterApplicationMapper;
        this.hunterApplicationRepository = hunterApplicationRepository;
    }

    @Override
    public List<ExtractionApplicationDTO> getAllApplications() {
        return extractionApplicationRepository.findAll()
                .stream()
                .map(extractionApplicationMapper::toDTO)
                .toList();
    }

    @Override
    public ExtractionApplicationDTO getApplicationById(String id) {
        return extractionApplicationRepository.findById(id)
                .map(extractionApplicationMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Application with id " + id + " not found"));
    }

    @Override
    @Transactional
    public HunterApplicationDTO createHunterApplication(CreateHunterApplicationRequest request) {
//        extractorRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Extractor with id " + id + " not found"));

        magicRepository.findById(request.magicId())
                .orElseThrow(() -> new NotFoundException("Magic with id " + request.magicId() + " not found"));

        HunterApplication hunterApplication = hunterApplicationMapper.toEntity(request);

        Date date = new Date();
        hunterApplication.setInitDate(new Date());
        hunterApplication.setStatus(ApplicationStatus.CREATED);
        date.setTime(date.getTime() + 24 * 60 * 60 * 1000);

        hunterApplicationRepository.save(hunterApplication);

        return hunterApplicationMapper.toDTO(hunterApplication);
    }


}
