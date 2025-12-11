package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.Extractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtractorRepository extends JpaRepository<Extractor, String> {

    Optional<Extractor> findById(String id);

}
