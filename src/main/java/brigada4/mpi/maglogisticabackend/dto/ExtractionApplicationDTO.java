package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExtractionApplicationDTO(
        String id,
        MagicDTO magic,
        MagicApplicationDTO magicApp,
        StorekeeperDTO storekeeper,
        ExtractionResponseDTO extractionResponse,
        HunterApplicationDTO hunterApp,
        ApplicationStatus status,
        int volume,
        Date initDate,
        Date deadline
) {}
