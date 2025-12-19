package brigada4.mpi.maglogisticabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record HunterApplicationDTO(
        String id,
        String hunterResponseId,
        String extractionAppId,
        String magicId,
        String extractorId,
        String status,
        int volume,
        Date initDate,
        Date deadline
) {}
