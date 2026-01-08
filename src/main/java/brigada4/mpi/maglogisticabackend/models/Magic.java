package brigada4.mpi.maglogisticabackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "magic")
public class Magic extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magic_type_id", referencedColumnName = "id")
    private MagicType magicType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magic_colour_id", referencedColumnName = "id")
    private MagicColour magicColour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magic_state_id", referencedColumnName = "id")
    private MagicState magicState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magic_power_id", referencedColumnName = "id")
    private MagicPower magicPower;

//    @OneToOne(mappedBy = "magic", fetch = FetchType.LAZY)
//    private MagicStorage magicStorage;

    @Override
    public String toString() {
        return "Magic with id: " + this.getId();
    }

}
