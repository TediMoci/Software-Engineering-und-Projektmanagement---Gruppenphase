package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDtoOut;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = WorkoutMapper.class)
public interface IWorkoutMapper {

    /**
     * @param workoutDto to be mapped to an entity
     * @return Workout-entity mapped from given Workout-DTO
     */
    Workout workoutDtoToWorkout(WorkoutDto workoutDto);

    /**
     * @param workout to be mapped to a DTO
     * @return Workout-DTO mapped from given Workout-entity
     */
    WorkoutDto workoutToWorkoutDto(Workout workout);

    /**
     * @param workoutExercise to be mapped to a DTO-out
     * @return WorkoutExercise-DTO-out mapped from given WorkoutExercise-entity
     */
    WorkoutExerciseDtoOut workoutExerciseToWorkoutExerciseDtoOut(WorkoutExercise workoutExercise);

}
