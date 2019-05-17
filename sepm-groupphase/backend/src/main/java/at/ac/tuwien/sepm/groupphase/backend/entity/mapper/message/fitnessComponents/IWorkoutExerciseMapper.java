package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = WorkoutExerciseMapper.class)
public interface IWorkoutExerciseMapper {

    WorkoutExercise workoutExerciseDtoToWorkoutExercise(WorkoutExerciseDto workoutExerciseDto);

    WorkoutExerciseDto workoutExerciseToWorkoutExerciseDto(WorkoutExercise workoutExercise);

}
