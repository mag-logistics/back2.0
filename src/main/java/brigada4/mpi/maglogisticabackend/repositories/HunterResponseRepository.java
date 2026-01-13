package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.HunterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HunterResponseRepository extends JpaRepository<HunterResponse, String> {
    List<HunterResponse> findAllByHunterId(String hunterId);
}
