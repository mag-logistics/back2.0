package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "animal_storage")
public class AnimalStorage extends GenericEntity {

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(name = "quantity")
    private int quantity;

}
