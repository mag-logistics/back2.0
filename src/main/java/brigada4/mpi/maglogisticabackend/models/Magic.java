package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "magic")
public class Magic extends GenericEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_type_id", referencedColumnName = "id")
    private MagicType magicType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_colour_id", referencedColumnName = "id")
    private MagicColour magicColour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_state_id", referencedColumnName = "id")
    private MagicState magicState;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_power_id", referencedColumnName = "id")
    private MagicPower magicPower;

    @OneToOne(mappedBy = "magic")
    private MagicStorage magicStorage;

}
