package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HunterApplicationMapper {
    HunterApplicationDTO toResponse(HunterApplication entity);

    List<HunterApplicationDTO> toResponseList(List<HunterApplication> entities);
}
