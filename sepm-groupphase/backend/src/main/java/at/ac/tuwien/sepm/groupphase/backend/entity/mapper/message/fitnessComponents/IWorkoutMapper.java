package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDtoOut;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = WorkoutMapper.class)
public interface IWorkoutMapper {

    Workout workoutDtoToWorkout(WorkoutDto workoutDto);

    WorkoutDto workoutToWorkoutDto(Workout workout);

    WorkoutExerciseDtoOut workoutExerciseToWorkoutExerciseDtoOut(WorkoutExercise workoutExercise);

}
