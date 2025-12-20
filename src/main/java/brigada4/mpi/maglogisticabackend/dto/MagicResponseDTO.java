package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.MagicApplication;
import brigada4.mpi.maglogisticabackend.models.Storekeeper;
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
public class MagicResponseDTO {
    private String id;
    private MagicApplicationDTO magicApp;
    private StorekeeperDTO storekeeper;
    private Date date;
}
