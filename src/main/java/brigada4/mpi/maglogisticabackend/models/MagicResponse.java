package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "magic_responses")
public class MagicResponse extends GenericEntity {

    @OneToOne(mappedBy = "magicResponse", cascade = CascadeType.ALL)
    private MagicApplication magicApp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storekeeper_id", referencedColumnName = "id")
    private Storekeeper storekeeper;

    @NotEmpty
    @Column(name = "date")
    private Date date;

}
