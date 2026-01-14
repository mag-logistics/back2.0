package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "hunter_response")
public class HunterResponse extends GenericEntity{

    @OneToOne(mappedBy = "hunterResponse", cascade = CascadeType.ALL)
    @JoinColumn(name = "hunter_application_id")
    private HunterApplication hunterApp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hunter_id", referencedColumnName = "id")
    private Hunter hunter;

    @Column(name = "response_date")
    private Date date;

    @Column(name = "volume")
    private int volume;
}
