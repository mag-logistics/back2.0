package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MagicDTO {
    private String id;
    private MagicTypeDTO magicTypeDTO;
    private MagicColourDTO magicColourDTO;
    private MagicStateDTO magicStateDTO;
    private MagicPowerDTO magicPowerDTO;
    private MagicStorageDTO magicStorageDTO;
}
