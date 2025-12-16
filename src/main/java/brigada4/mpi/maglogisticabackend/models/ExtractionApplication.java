package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "extraction_applications")
public class ExtractionApplication extends GenericEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_id", referencedColumnName = "id")
    private Magic magic;

    @OneToOne(mappedBy = "extractionApp", cascade = CascadeType.ALL, orphanRemoval = true)
    private MagicApplication magicApp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storekeeper_id", referencedColumnName = "id")
    private Storekeeper storekeeper;

    @OneToOne
    @JoinColumn(name = "extraction_response_id")
    private ExtractionResponse extractionResponse;

    @OneToOne(mappedBy = "extractionApp")
    private HunterApplication hunterApp;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @NotEmpty
    @Column(name = "volume")
    private int volume;

    @NotEmpty
    @Column(name = "init_date")
    private Date initDate;

    @NotEmpty
    @Column(name = "deadline")
    private Date deadline;

}
