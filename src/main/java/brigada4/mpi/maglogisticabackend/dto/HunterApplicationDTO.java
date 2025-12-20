package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HunterApplicationDTO {
    private String id;
    private HunterResponseDTO hunterResponse;
    private ExtractionApplicationDTO extractionApp;
    private MagicDTO magic;
    private ExtractorDTO extractor;
    private ApplicationStatus status;
    private int animalCount;
    private AnimalDTO animal;
    private Date initDate;
    private Date deadline;
}
