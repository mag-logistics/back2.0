package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.ApplicationStatus;
import brigada4.mpi.maglogisticabackend.models.MagicApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagicApplicationRepository extends JpaRepository<MagicApplication, String> {

    List<MagicApplication> findAllByStatus(ApplicationStatus status);

    MagicApplication findByMagicResponseId(String magicResponseId);


    List<MagicApplication> findAllByMagician(String magicianId);

}
