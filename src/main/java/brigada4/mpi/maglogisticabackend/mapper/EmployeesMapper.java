package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.ExtractorDTO;
import brigada4.mpi.maglogisticabackend.dto.HunterDTO;
import brigada4.mpi.maglogisticabackend.dto.StorekeeperDTO;
import brigada4.mpi.maglogisticabackend.dto.UserDTO;
import brigada4.mpi.maglogisticabackend.models.Extractor;
import brigada4.mpi.maglogisticabackend.models.Hunter;
import brigada4.mpi.maglogisticabackend.models.Storekeeper;
import brigada4.mpi.maglogisticabackend.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeesMapper {

    UserDTO fromUser(User user);

    User fromUserDTO(UserDTO userDTO);


    Storekeeper fromStorekeeperDTO(StorekeeperDTO storekeeperDTO);

    StorekeeperDTO toStorekeeperDTO(Storekeeper storekeeper);

    List<Storekeeper> fromStorekeeperDTOList(List<StorekeeperDTO> storekeeperDTOList);

    List<StorekeeperDTO> toStorekeeperDTOList(List<Storekeeper> storekeeperDTOList);


    Hunter fromHunterDTO(HunterDTO hunterDTO);

    HunterDTO toHunterDTO(Hunter hunter);

    List<Hunter> fromHunterDTOList(List<HunterDTO> hunterDTOList);

    List<HunterDTO> toHunterDTOList(List<Hunter> hunterDTOList);


    Extractor fromExtractorDTO(ExtractorDTO extractorDTO);

    ExtractorDTO toExtractorDTO(Extractor extractor);

    List<Extractor> fromExtractorDTOList(List<ExtractorDTO> extractorDTOList);

    List<ExtractorDTO> toExtractorDTOList(List<Extractor> extractorDTOList);
}


