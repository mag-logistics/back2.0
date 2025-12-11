package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "magic_storage")
public class MagicStorage extends GenericEntity {

    @OneToOne
    @JoinColumn(name = "magic_id")
    private Magic magic;

    @NotEmpty
    @Column(name = "volume")
    private int volume;

}
