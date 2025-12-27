package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.MagicDTO;
import brigada4.mpi.maglogisticabackend.models.Magic;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MagicMapper {

    Magic magicFromMagicDTO(MagicDTO magicDTO);

    MagicDTO magicDTOFromMagic(Magic magic);

    List<Magic> magicListFromMagicDTOList(List<MagicDTO> magicDTOList);

    List<MagicDTO> magicDTOListFromMagicList(List<Magic> magicDTOList);

}
