package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "application_status")
public class ApplicationStatus extends GenericEntity{

    @NotEmpty
    @Column(name = "name", unique = true)
    private String name;
}
