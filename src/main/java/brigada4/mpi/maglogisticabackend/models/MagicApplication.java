package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "magic_applications")
public class MagicApplication extends GenericEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magician_id", referencedColumnName = "id")
    private Magician magician;

    @OneToOne
    @JoinColumn(name = "magic_response_id")
    private MagicResponse magicResponse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_id", referencedColumnName = "id")
    private Magic magic;

    @OneToOne
    @JoinColumn(name = "extraction_app_id")
    private ExtractionApplication extractionApp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storekeeper_id", referencedColumnName = "id")
    private Storekeeper storekeeper;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "volume")
    private int volume;

    @Column(name = "init_date")
    private Date initDate;

    @Column(name = "deadline")
    private Date deadline;
}
