package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.Magic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagicRepository extends JpaRepository<Magic, String> {
}
