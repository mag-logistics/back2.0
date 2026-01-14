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

//    @OneToOne(mappedBy = "extractionApp", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "magic_app_id")
    private String magicAppId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storekeeper_id", referencedColumnName = "id")
    private Storekeeper storekeeper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "extractor_id", referencedColumnName = "id")
    private Extractor extractor;

    @OneToOne
    private ExtractionResponse extractionResponse;

    @OneToOne(mappedBy = "extractionApp")
    private HunterApplication hunterApp;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "volume")
    private int volume;

    @Column(name = "init_date")
    private Date initDate;

    @Column(name = "deadline")
    private Date deadline;

}
