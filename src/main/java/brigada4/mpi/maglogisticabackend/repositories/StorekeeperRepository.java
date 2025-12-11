package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.Storekeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorekeeperRepository extends JpaRepository<Storekeeper, String> {

    Optional<Storekeeper> findById(String id);
}
