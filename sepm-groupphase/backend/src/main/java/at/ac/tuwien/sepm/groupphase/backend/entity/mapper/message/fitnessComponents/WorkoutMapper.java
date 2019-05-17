package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import org.springframework.stereotype.Component;

@Component
public class WorkoutMapper implements IWorkoutMapper {

    @Override
    public Workout workoutDtoToWorkout(WorkoutDto workoutDto) {
        Workout.WorkoutBuilder builder = new Workout.WorkoutBuilder();

        builder.id(workoutDto.getId());
        builder.name(workoutDto.getName());
        builder.description(workoutDto.getDescription());
        builder.difficulty(workoutDto.getDifficulty());
        builder.calorieConsumption(workoutDto.getCalorieConsumption());
        builder.rating(workoutDto.getRating());
        builder.exercises(workoutDto.getExercises());
        builder.creator(workoutDto.getCreator());

        return builder.build();
    }

    @Override
    public WorkoutDto workoutToWorkoutDto(Workout workout) {
        WorkoutDto.WorkoutDtoBuilder builder = new WorkoutDto.WorkoutDtoBuilder();

        builder.id(workout.getId());
        builder.name(workout.getName());
        builder.description(workout.getDescription());
        builder.difficulty(workout.getDifficulty());
        builder.calorieConsumption(workout.getCalorieConsumption());
        builder.rating(workout.getRating());
        builder.exercises(workout.getExercises());
        builder.creator(workout.getCreator());

        return builder.build();
    }

}
