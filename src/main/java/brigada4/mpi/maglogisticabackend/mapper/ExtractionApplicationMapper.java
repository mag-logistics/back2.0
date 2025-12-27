package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.models.ExtractionApplication;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import brigada4.mpi.maglogisticabackend.payload.CreateHunterApplicationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExtractionApplicationMapper {

    // DTO
    ExtractionApplicationDTO toDTO(ExtractionApplication entity);
    List<ExtractionApplicationDTO> toDTOList(List<ExtractionApplication> entities);

    // to Entity
    ExtractionApplication toEntity(ExtractionApplicationDTO dto);


}
