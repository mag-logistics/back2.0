package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "hunter_application")
public class HunterApplication extends GenericEntity {

//    @OneToOne
    @OneToOne
    @JoinColumn(name = "hunter_response_id")
    private HunterResponse hunterResponse;

    @Column(name = "extraction_app_id")
    private String extractionAppId;
//    @OneToOne
//    @JoinColumn(name = "extraction_app_id")
//    private ExtractionApplication extractionApp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "magic_id", referencedColumnName = "id")
    private Magic magic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "extractor_id", referencedColumnName = "id")
    private Extractor extractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hunter_id", referencedColumnName = "id")
    private Hunter hunter;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    //Данные, которые заполняет высасыватель в форме заявки
    @Column(name = "animal_count")
    private int animalCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="animal_id", referencedColumnName = "id")
    private Animal animal;

    @Column(name = "init_date")
    private Date initDate;

    @Column(name = "deadline")
    private Date deadline;
}
