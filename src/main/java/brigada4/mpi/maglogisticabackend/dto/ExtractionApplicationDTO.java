package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtractionApplicationDTO {

    private String id;
    private Magic magic;
    private MagicApplication magicApp;
    private Storekeeper storekeeper;
    private ExtractionResponse extractionResponse;
    private HunterApplication hunterApp;
    private ApplicationStatus status;
    private int volume;
    private Date initDate;
    private Date deadline;

}
