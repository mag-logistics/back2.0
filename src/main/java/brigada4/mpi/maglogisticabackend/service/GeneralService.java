package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.models.Animal;
import brigada4.mpi.maglogisticabackend.models.Magic;
import brigada4.mpi.maglogisticabackend.repositories.AnimalRepository;
import brigada4.mpi.maglogisticabackend.repositories.MagicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralService {
    private final AnimalRepository animalRepository;
    private final MagicRepository magicRepository;

    @Autowired
    public GeneralService(AnimalRepository animalRepository, MagicRepository magicRepository) {
        this.animalRepository = animalRepository;
        this.magicRepository = magicRepository;
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public List<Magic> getAllMagic() {
        List<Magic> magics = magicRepository.findAll();
        return magics;
    }
}
