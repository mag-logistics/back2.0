package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HunterApplicationMapper {
    @Mapping(source = "hunterResponse.id", target = "hunterResponseId")
    @Mapping(source = "extractionApp.id", target = "extractionAppId")
    @Mapping(source = "magic.id", target = "magicId")
    @Mapping(source = "extractor.id", target = "extractorId")
    @Mapping(source = "status.name", target = "status")
    HunterApplicationDTO toResponse(HunterApplication entity);

    List<HunterApplicationDTO> toResponseList(List<HunterApplication> entities);
}
