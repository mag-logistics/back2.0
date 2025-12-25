package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.MagicAppPatternDTO;
import brigada4.mpi.maglogisticabackend.dto.MagicApplicationDTO;
import brigada4.mpi.maglogisticabackend.models.MagicAppPattern;
import brigada4.mpi.maglogisticabackend.models.MagicApplication;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MagicApplicationMapper {

    MagicApplication fromMagicApplicationDTO(MagicApplicationDTO magicApplicationDTO);

    MagicApplicationDTO fromMagicApplication(MagicApplication magicApplication);

    List<MagicApplicationDTO> fromMagicApplications(List<MagicApplication> magicApplications);

    List<MagicApplication> fromMagicApplicationDTOs(List<MagicApplicationDTO> magicApplicationDTOS);

    MagicAppPattern fromMagicApp(MagicApplication magicApplication);

    MagicApplication fromPattern(MagicAppPattern magicAppPattern);

    MagicAppPattern fromMagicAppDTO(MagicApplicationDTO magicApplication);

    MagicApplication fromPatternDTO(MagicAppPatternDTO magicAppPattern);

    MagicApplicationDTO DTOFromPatternDTO(MagicAppPatternDTO magicAppPattern);

    MagicApplicationDTO DTOFromPattern(MagicAppPattern magicAppPattern);

    List<MagicAppPatternDTO> listPatterDTOFromPatterns(List<MagicAppPattern> magicAppPatterns);
}
