package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sex")
@Data
public class Sex extends GenericEntity implements Comparable<Sex> {
    @Column(name = "name")
    private String name;

    @Override
    public int compareTo(Sex o) {
        return this.getName().compareTo(o.getName());
    }
}
