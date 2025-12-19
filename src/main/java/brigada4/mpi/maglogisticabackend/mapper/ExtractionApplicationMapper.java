package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.ExtractionApplicationDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterApplicationDTO;
import brigada4.mpi.maglogisticabackend.models.ExtractionApplication;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExtractionApplicationMapper {

    @Mapping(source = "magic", target = "magic")
    @Mapping(source = "magicApp", target = "magicApp")
    @Mapping(source = "storekeeper", target = "storekeeper")
    @Mapping(source = "extractionResponse", target = "extractionResponse")
    @Mapping(source = "hunterApp", target = "hunterApp")
    @Mapping(source = "status", target = "status")
    ExtractionApplicationDTO toDTO(ExtractionApplication entity);

    List<ExtractionApplicationDTO> toDTOList(List<ExtractionApplication> entities);
}
