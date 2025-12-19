package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "hunters")
public class Hunter extends User {
}
