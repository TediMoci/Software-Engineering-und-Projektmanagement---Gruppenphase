package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDtoOut;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WorkoutMapper implements IWorkoutMapper {

    @Override
    public Workout workoutDtoToWorkout(WorkoutDto workoutDto) {
        Workout.WorkoutBuilder builder = new Workout.WorkoutBuilder();

        builder.id(workoutDto.getId());
        builder.version(workoutDto.getVersion());
        builder.name(workoutDto.getName());
        builder.description(workoutDto.getDescription());
        builder.difficulty(workoutDto.getDifficulty());
        builder.calorieConsumption(workoutDto.getCalorieConsumption());
        builder.rating(workoutDto.getRating());
        builder.isHistory(false);
        builder.isPrivate(workoutDto.getIsPrivate());

        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        WorkoutExercise.WorkoutExerciseBuilder workoutExerciseBuilder;
        for (int i = 0; i < workoutDto.getWorkoutExercises().length; i++) {
            workoutExerciseBuilder = new WorkoutExercise.WorkoutExerciseBuilder();
            workoutExerciseBuilder.exerciseId(workoutDto.getWorkoutExercises()[i].getExerciseId());
            workoutExerciseBuilder.exerciseVersion(workoutDto.getWorkoutExercises()[i].getExerciseVersion());
            workoutExerciseBuilder.workoutId(null);
            workoutExerciseBuilder.workoutVersion(1);
            workoutExerciseBuilder.exDuration(workoutDto.getWorkoutExercises()[i].getExDuration());
            workoutExerciseBuilder.repetitions(workoutDto.getWorkoutExercises()[i].getRepetitions());
            workoutExerciseBuilder.sets(workoutDto.getWorkoutExercises()[i].getSets());
            workoutExercises.add(workoutExerciseBuilder.build());
        }
        builder.exercises(workoutExercises);

        Dude.DudeBuilder dudeBuilder = new Dude.DudeBuilder();
        dudeBuilder.id(workoutDto.getCreatorId());
        Dude dude = dudeBuilder.build();
        builder.creator(dude);

        return builder.build();
    }

    @Override
    public WorkoutDto workoutToWorkoutDto(Workout workout) {
        WorkoutDto.WorkoutDtoBuilder builder = new WorkoutDto.WorkoutDtoBuilder();

        builder.id(workout.getId());
        builder.version(workout.getVersion());
        builder.name(workout.getName());
        builder.description(workout.getDescription());
        builder.difficulty(workout.getDifficulty());
        builder.calorieConsumption(workout.getCalorieConsumption());
        builder.rating(workout.getRating());
        builder.isPrivate(workout.getIsPrivate());
        builder.creatorId(workout.getCreator().getId());

        return builder.build();
    }

    @Override
    public WorkoutExerciseDtoOut workoutExerciseToWorkoutExerciseDtoOut(WorkoutExercise workoutExercise) {
        WorkoutExerciseDtoOut.WorkoutExerciseDtoOutBuilder builder = new WorkoutExerciseDtoOut.WorkoutExerciseDtoOutBuilder();

        builder.id(workoutExercise.getExercise().getId());
        builder.version(workoutExercise.getExercise().getVersion());
        builder.name(workoutExercise.getExercise().getName());
        builder.description(workoutExercise.getExercise().getDescription());
        builder.equipment(workoutExercise.getExercise().getEquipment());
        builder.muscleGroup(workoutExercise.getExercise().getMuscleGroup());
        builder.rating(workoutExercise.getExercise().getRating());
        builder.category(workoutExercise.getExercise().getCategory());
        builder.imagePath(workoutExercise.getExercise().getImagePath());
        builder.creatorName(workoutExercise.getExercise().getCreator().getName());
        builder.exDuration(workoutExercise.getExDuration());
        builder.repetitions(workoutExercise.getRepetitions());
        builder.sets(workoutExercise.getSets());

        return builder.build();
    }

}
