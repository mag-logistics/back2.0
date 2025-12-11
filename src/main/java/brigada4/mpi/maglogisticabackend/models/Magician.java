package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "magicians")
public class Magician extends User {
}
