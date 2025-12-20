package brigada4.mpi.maglogisticabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MagicAppPatternDTO {

    private String id;
    private MagicianDTO magician;
    private MagicDTO magic;
    private int volume;
    private Date deadline;
}
