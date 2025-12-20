package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.Magic;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MagicStorageDTO {
    private String id;
    private MagicDTO magicDTO;
    private int volume;
}
