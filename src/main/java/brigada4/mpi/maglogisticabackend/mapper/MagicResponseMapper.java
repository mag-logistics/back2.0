package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.MagicResponseDTO;
import brigada4.mpi.maglogisticabackend.models.MagicResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MagicResponseMapper {

    MagicResponseDTO magicResponseToMagicResponseDTO(MagicResponse magicResponse);

    MagicResponse magicResponseDTOToMagicResponse(MagicResponseDTO magicResponseDTO);

    List<MagicResponse> magicResponseDTOListToMagicResponseList(List<MagicResponseDTO> magicResponseDTOList);

    List<MagicResponseDTO> magicResponseListToMagicResponseDTOList(List<MagicResponse> magicResponseList);

}
