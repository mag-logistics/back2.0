package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.AnimalStorage;
import brigada4.mpi.maglogisticabackend.models.Magic;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimalDTO {
    private String id;
    private String name;
    private MagicDTO magic;
    private AnimalStorageDTO animalStorage;
    private int magicVolume;
}
