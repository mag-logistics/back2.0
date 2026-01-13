package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.ExtractionResponseDTO;
import brigada4.mpi.maglogisticabackend.models.ExtractionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExtractionResponseMapper {
    ExtractionResponseDTO toDTO(ExtractionResponse extractionResponse);
    ExtractionResponse toEntity(ExtractionResponseDTO extractionResponseDTO);
}
