package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.HunterResponseDTO;
import brigada4.mpi.maglogisticabackend.models.HunterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HunterResponseMapper {
    HunterResponseDTO toDTO(HunterResponse hunterResponse);
    HunterResponse toEntity(HunterResponseDTO hunterResponseDTO);
}
