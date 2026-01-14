package brigada4.mpi.maglogisticabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtractionResponseDTO {
    private String id;
    private String extractionAppId;
    private ExtractorDTO extractor;
    private Date date;
}
