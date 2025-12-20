package brigada4.mpi.maglogisticabackend.dto;

import brigada4.mpi.maglogisticabackend.models.RefreshToken;
import brigada4.mpi.maglogisticabackend.models.Role;
import brigada4.mpi.maglogisticabackend.models.Sex;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
