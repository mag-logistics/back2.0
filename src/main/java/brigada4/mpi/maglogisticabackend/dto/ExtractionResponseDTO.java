package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.ExtractionApplication;
import brigada4.mpi.maglogisticabackend.models.Extractor;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtractionResponseDTO {
    private String id;
    private ExtractionApplicationDTO extractionAppDTO;
    private ExtractorDTO extractorDTO;
    private Date date;
}
