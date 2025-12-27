package brigada4.mpi.maglogisticabackend.mapper;

import brigada4.mpi.maglogisticabackend.dto.AnimalDTO;
import brigada4.mpi.maglogisticabackend.models.Animal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    Animal animalFromAnimalDTO(AnimalDTO animalDTO);

    AnimalDTO animalToAnimalDTO(Animal animal);

    List<Animal> animalsFromAnimalDTOList(List<AnimalDTO> animalDTOList);

    List<AnimalDTO> animalsToAnimalDTOList(List<Animal> animals);

}
