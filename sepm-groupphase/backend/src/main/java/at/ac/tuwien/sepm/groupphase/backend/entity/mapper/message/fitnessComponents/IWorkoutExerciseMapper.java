package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDtoIn;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = WorkoutExerciseMapper.class)
public interface IWorkoutExerciseMapper {

    WorkoutExercise workoutExerciseDtoToWorkoutExercise(WorkoutExerciseDtoIn workoutExerciseDtoIn);

    WorkoutExerciseDtoIn workoutExerciseToWorkoutExerciseDto(WorkoutExercise workoutExercise);

}
