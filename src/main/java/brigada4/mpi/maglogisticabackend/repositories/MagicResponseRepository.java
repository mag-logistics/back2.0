package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.MagicResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagicResponseRepository extends JpaRepository<MagicResponse, String> {

    List<MagicResponse> findAllByStorekeeperId(String storekeeperId);

}
