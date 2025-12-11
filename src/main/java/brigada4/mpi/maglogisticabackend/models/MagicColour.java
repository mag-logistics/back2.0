package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "magic_colour")
public class MagicColour extends GenericEntity {
    @NotEmpty
    @Column(name = "name")
    private String name;
}
