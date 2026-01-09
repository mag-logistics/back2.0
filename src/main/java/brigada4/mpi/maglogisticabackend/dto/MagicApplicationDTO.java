package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MagicApplicationDTO {
    private String id;
    private MagicianDTO magician;
    private MagicResponseDTO magicResponse;
    private MagicDTO magic;
    private ExtractionApplicationDTO extractionApp;
    private StorekeeperDTO storekeeper;
    private ApplicationStatus status;
    private int volume;
    private Date initDate;
    private Date deadline;
}
