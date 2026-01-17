package brigada4.mpi.maglogisticabackend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HunterHuntingResultRequestDTO {

    @NotEmpty
    Map<String, Integer> animals;
}
