package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.MagicApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagicApplicationRepository extends JpaRepository<MagicApplication, String> {

    List<MagicApplication> findAllByMagician(String magicianId);

}
