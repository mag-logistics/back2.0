package brigada4.mpi.maglogisticabackend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "magic_storage")
public class MagicStorage extends GenericEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magic_id")
    private Magic magic;

    @NotEmpty
    @Column(name = "volume")
    private int volume;

    @Override
    public String toString() {
        return "MagicStorage [magic=" + magic.getId() + ", volume=" + volume + "]";
    }

}
