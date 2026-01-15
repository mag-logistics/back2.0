package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.Hunter;
import brigada4.mpi.maglogisticabackend.models.HunterApplication;
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
public class HunterResponseDTO {
    private String id;
    private String hunterAppId;
    private HunterDTO hunter;
    private Date date;
    private int volume;
}
