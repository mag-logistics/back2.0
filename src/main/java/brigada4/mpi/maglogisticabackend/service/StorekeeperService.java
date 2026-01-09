package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicResponseDTO;
import brigada4.mpi.maglogisticabackend.mapper.ExtractionApplicationMapper;
import brigada4.mpi.maglogisticabackend.mapper.MagicMapper;
import brigada4.mpi.maglogisticabackend.models.*;
import brigada4.mpi.maglogisticabackend.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StorekeeperService {

    private final StorekeeperRepository storekeeperRepository;
    private final MagicApplicationRepository magicApplicationRepository;
    private final MagicResponseRepository magicResponseRepository;
    private final ExtractionApplicationMapper extractionApplicationMapper;
    private final ExtractionApplicationRepository extractionApplicationRepository;
    private final MagicStorageRepository magicStorageRepository;

    public StorekeeperService(StorekeeperRepository storekeeperRepository, MagicApplicationRepository magicApplicationRepository, MagicResponseRepository magicResponseRepository, ExtractionApplicationMapper extractionApplicationMapper, ExtractionApplicationRepository extractionApplicationRepository, MagicStorageRepository magicStorageRepository) {
        this.storekeeperRepository = storekeeperRepository;
        this.magicApplicationRepository = magicApplicationRepository;
        this.magicResponseRepository = magicResponseRepository;
        this.extractionApplicationMapper = extractionApplicationMapper;
        this.extractionApplicationRepository = extractionApplicationRepository;
        this.magicStorageRepository = magicStorageRepository;
    }

    public List<MagicApplication> getAllMagicApplication() {
        return magicApplicationRepository.findAllByStatus(ApplicationStatus.CREATED);
    }


    public List<MagicApplication> getMyMagicApplication(String storekeeperId) {
        return magicApplicationRepository.findAllByStorekeeperId(storekeeperId);
    }

    public List<MagicResponse> getMyMagicResponses(String storekeeperId) {
        return magicResponseRepository.findAllByStorekeeperId(storekeeperId);
    }

    public MagicApplication getMagicApplication(String magicAppId) {
        return magicApplicationRepository.findById(magicAppId).orElse(null);
    }

    @Transactional
    public MagicResponse processMagicApplication(String storekeeperId, String magicApplicationId, MagicResponseDTO magicResponseDTO) {
        MagicResponse magicResponse = new MagicResponse();
        Storekeeper storekeeper = storekeeperRepository.findById(storekeeperId).orElse(null);
        MagicApplication magicApplication = magicApplicationRepository.findById(magicApplicationId).orElse(null);
        if (storekeeper == null || magicApplication == null) {
            return null;
        }
        magicResponse.setStorekeeper(storekeeper);
        magicResponse.setMagicApplicationId(magicApplication.getId());

        magicResponse.setDate(magicResponseDTO.getDate());

        return magicResponseRepository.save(magicResponse);
    }

    @Transactional
    public ExtractionApplication createExtractionApp(String storekeeperId, ExtractionApplicationDTO extractionApplicationDTO) {
        Storekeeper storekeeper = storekeeperRepository.findById(storekeeperId).orElse(null);
        if (storekeeper == null) {
            return null;
        }

        ExtractionApplication extractionApplication = extractionApplicationMapper.toEntity(extractionApplicationDTO);
        MagicApplication magicApp = magicApplicationRepository.findById(extractionApplicationDTO.magicApp().getId()).orElse(null);
        if (magicApp == null) {
            return null;
        }
        extractionApplication.setMagicAppId(magicApp.getId());
        Magic magic = magicApp.getMagic();
        extractionApplication.setMagic(magic);
        extractionApplication.setStorekeeper(storekeeper);
        Date date = new Date();
        extractionApplication.setInitDate(date);
        extractionApplication.setStatus(ApplicationStatus.CREATED);
        return extractionApplicationRepository.save(extractionApplication);
    }

    public boolean checkMagicAvailability(String magicApplicationId) {
        MagicApplication magicApplication = magicApplicationRepository.findById(magicApplicationId).orElse(null);
        if (magicApplication == null) {
            return false;
        }
        MagicStorage magicStorage = magicStorageRepository.findByMagicId(magicApplication.getMagic().getId()).orElse(null);
        if (magicStorage == null) {
            return false;
        }
        return magicApplication.getVolume() <= magicStorage.getVolume();
    }

    @Transactional
    public void saveResponseInMagicApplication(String magicApplicationId, MagicResponse magicResponse) {
        MagicApplication magicApplication = magicApplicationRepository.findById(magicApplicationId).orElse(null);
        if (magicApplication == null) {
            return;
        }
        magicApplication.setMagicResponse(magicResponse);
        magicApplication.setStatus(ApplicationStatus.FINISHED);
        magicApplicationRepository.save(magicApplication);
        MagicStorage magicStorage = magicStorageRepository.findByMagicId(magicApplication.getMagic().getId()).orElse(null);
        if (magicStorage != null) {
            magicStorage.setVolume(magicStorage.getVolume() - magicApplication.getVolume());
        }
        magicApplicationRepository.save(magicApplication);
    }

    @Transactional
    public void saveExtrAppInMagicApp(ExtractionApplicationDTO extractionApplicationDTO, ExtractionApplication extractionApplication) {
        MagicApplication magicApplication = magicApplicationRepository.findById(extractionApplicationDTO.magicApp().getId()).orElse(null);
        if (magicApplication == null) {
            return;
        }
        magicApplication.setExtractionApp(extractionApplication);
        magicApplicationRepository.save(magicApplication);
    }

    @Transactional
    public MagicApplication takeMagicApplication(String storekeeperId, String magicApplicationId) {
        Storekeeper storekeeper = storekeeperRepository.findById(storekeeperId).orElse(null);
        MagicApplication magicApplication = magicApplicationRepository.findById(magicApplicationId).orElse(null);
        if (magicApplication == null || storekeeper == null) {
            return null;
        }
        magicApplication.setStorekeeper(storekeeper);
        magicApplication.setStatus(ApplicationStatus.WORKED);
        return magicApplicationRepository.save(magicApplication);
    }
}
