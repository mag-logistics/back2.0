package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "animals")
public class Animal extends GenericEntity {

    @NotEmpty
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_id", referencedColumnName = "id")
    private Magic magic;

    @NotEmpty
    @Column(name = "magic_volume")
    private int magicVolume;

//    @OneToOne(mappedBy = "animal")
//    private AnimalStorage animalStorage;
}
