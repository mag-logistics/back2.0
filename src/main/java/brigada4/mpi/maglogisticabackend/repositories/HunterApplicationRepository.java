package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.HunterApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HunterApplicationRepository extends JpaRepository<HunterApplication, String> {
    List<HunterApplication> findByHunterId(String id);
}
