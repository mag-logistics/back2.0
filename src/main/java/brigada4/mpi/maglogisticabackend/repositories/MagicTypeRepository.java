package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.MagicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagicTypeRepository extends JpaRepository<MagicType, String> {
}
