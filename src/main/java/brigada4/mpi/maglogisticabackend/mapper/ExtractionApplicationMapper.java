package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.models.ExtractionApplication;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExtractionApplicationMapper {

    ExtractionApplicationDTO fromExtractionApplication(ExtractionApplication extractionApplication);

    ExtractionApplication fromExtractionApplicationDTO(ExtractionApplicationDTO extractionApplicationDTO);

    List<ExtractionApplicationDTO> fromExtractionApplications(List<ExtractionApplication> extractionApplications);

    List<ExtractionApplication> fromExtractionApplicationDTOs(List<ExtractionApplicationDTO> extractionApplicationDTOS);

}
