package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "extractors")
public class Extractor extends User{
}
