package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ExerciseMapper.class)
public interface IExerciseMapper {

    Exercise exerciseDtoToExercise(ExerciseDto exerciseDto);

    ExerciseDto exerciseToExerciseDto(Exercise exercise);

}
