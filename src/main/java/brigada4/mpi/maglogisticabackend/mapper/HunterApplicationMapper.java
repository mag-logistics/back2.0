package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import brigada4.mpi.maglogisticabackend.payload.request.CreateHunterApplicationRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HunterApplicationMapper {

    // to DTO
    HunterApplicationDTO toDTO(HunterApplication entity);
    List<HunterApplicationDTO> toDTOList(List<HunterApplication> entities);

    // to Entity
    HunterApplication toEntity(HunterApplicationDTO dto);
    HunterApplication toEntity(CreateHunterApplicationRequest request);
}
