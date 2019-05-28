package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ExerciseMapper.class)
public interface IExerciseMapper {

    /**
     * @param exerciseDto to be mapped to an entity
     * @return Exercise-entity mapped from given Exercise-DTO
     */
    Exercise exerciseDtoToExercise(ExerciseDto exerciseDto);

    /**
     * @param exercise to be mapped to a DTO
     * @return Exercise-DTO mapped from given Exercise-entity
     */
    ExerciseDto exerciseToExerciseDto(Exercise exercise);

}
