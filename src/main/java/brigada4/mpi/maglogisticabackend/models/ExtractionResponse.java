package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "extraction_response")
@NoArgsConstructor
@AllArgsConstructor
public class ExtractionResponse extends GenericEntity {

    @OneToOne(mappedBy = "extractionResponse", cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id", nullable = false)
    private ExtractionApplication extractionApp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "extractor_id", referencedColumnName = "id")
    private Extractor extractor;

    @Column(name = "response_date")
    private Date date;

}
