package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.ExtractionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractionResponseRepository extends JpaRepository<ExtractionResponse, String> {
}
