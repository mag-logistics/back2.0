package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.Magic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagicRepository extends JpaRepository<Magic, String> {

}
