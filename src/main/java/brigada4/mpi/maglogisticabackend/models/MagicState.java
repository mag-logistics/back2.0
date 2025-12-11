package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "magic_state")
public class MagicState extends GenericEntity {
    @NotEmpty
    @Column(name = "name")
    private String name;
}
