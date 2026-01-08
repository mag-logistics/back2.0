package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "magic_app_patterns")
public class MagicAppPattern extends GenericEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magician_id", referencedColumnName = "id")
    private Magician magician;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_id", referencedColumnName = "id")
    private Magic magic;

    @Column(name = "volume")
    private int volume;

    @Column(name = "deadline")
    private Date deadline;

}
