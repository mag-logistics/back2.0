package brigada4.mpi.maglogisticabackend.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Data
@MappedSuperclass
@ToString(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericEntity implements Serializable {

    private static final long serialVersionUID = 7219760563483429945L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36)
    @NotNull
    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GenericEntity that = (GenericEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
