package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "hunter_application")
public class HunterApplication extends GenericEntity {

    @OneToOne
    @JoinColumn(name = "hunter_response_id")
    private HunterResponse hunterResponse;

    @OneToOne
    @JoinColumn(name = "extraction_app_id")
    private ExtractionApplication extractionApp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_id", referencedColumnName = "id")
    private Magic magic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "extractor_id", referencedColumnName = "id")
    private Extractor extractor;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    //Данные, которые заполняет высасыватель в форме заявки
    @NotEmpty
    @Column(name = "animal_count")
    private int animalCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="animal_id", referencedColumnName = "id")
    private Animal animal;

    @NotEmpty
    @Column(name = "init_date")
    private Date initDate;

    @NotEmpty
    @Column(name = "deadline")
    private Date deadline;
}
