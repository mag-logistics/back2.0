package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String id;
    private String email;
    private Role role;
    private SexDTO sexDTO;
    private String name;
    private String surname;
    private String patronymic;
    private Date birthDate;
    private int rewardPoints;
    private int penaltyPoints;
}
