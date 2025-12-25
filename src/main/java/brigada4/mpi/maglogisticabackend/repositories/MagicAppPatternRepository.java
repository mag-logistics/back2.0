package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.MagicAppPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagicAppPatternRepository extends JpaRepository<MagicAppPattern, String> {

    List<MagicAppPattern> findAllByMagician(String magician);

}
