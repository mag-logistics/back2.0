package brigada4.mpi.maglogisticabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatusDTO(String name) {
}
