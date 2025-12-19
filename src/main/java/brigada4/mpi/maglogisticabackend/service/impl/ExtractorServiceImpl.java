package brigada4.mpi.maglogisticabackend.service.impl;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.exception.NotFoundException;
import brigada4.mpi.maglogisticabackend.mapper.ExtractionApplicationMapper;
import brigada4.mpi.maglogisticabackend.payload.CreateHunterApplicationRequest;
import brigada4.mpi.maglogisticabackend.repositories.ExtractionApplicationRepository;
import brigada4.mpi.maglogisticabackend.repositories.ExtractorRepository;
import brigada4.mpi.maglogisticabackend.service.ExtractorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExtractorServiceImpl implements ExtractorService {

    private final ExtractorRepository extractorRepository;
    private final ExtractionApplicationRepository extractionApplicationRepository;
    private final ExtractionApplicationMapper extractionApplicationMapper;

    public ExtractorServiceImpl(ExtractorRepository extractorRepository, ExtractionApplicationRepository extractionApplicationRepository, ExtractionApplicationMapper extractionApplicationMapper) {
        this.extractorRepository = extractorRepository;
        this.extractionApplicationRepository = extractionApplicationRepository;
        this.extractionApplicationMapper = extractionApplicationMapper;
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
    public HunterApplicationDTO createHunterApplication(String extractorId, CreateHunterApplicationRequest request) {
        var extractor = extractorRepository.findById(extractorId);





        return null;
    }


}
