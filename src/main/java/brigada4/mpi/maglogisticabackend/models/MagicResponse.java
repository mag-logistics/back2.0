package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "magic_responses")
public class MagicResponse extends GenericEntity {

    @Column(name = "magic_application_id")
    private String magicApplicationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storekeeper_id", referencedColumnName = "id")
    private Storekeeper storekeeper;

    @Column(name = "response_date")
    private Date date;

}
