package brigada4.mpi.maglogisticabackend.repositories;

import brigada4.mpi.maglogisticabackend.models.Sex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexRepository extends JpaRepository<Sex, String> {

    Sex findByName(String name);

}
