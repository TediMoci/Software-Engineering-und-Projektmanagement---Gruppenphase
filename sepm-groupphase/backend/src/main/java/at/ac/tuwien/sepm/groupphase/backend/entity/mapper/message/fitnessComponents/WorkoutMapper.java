package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutExerciseKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutKey;
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

        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        WorkoutExercise.WorkoutExerciseBuilder workoutExerciseBuilder;
        //Exercise exercise = new Exercise();
        //Workout workout = new Workout();
        //workout.setVersion(1);
        for (int i = 0; i < workoutDto.getWorkoutExercises().length; i++) {
            workoutExerciseBuilder = new WorkoutExercise.WorkoutExerciseBuilder();

            //workoutExerciseBuilder.id(new WorkoutExerciseKey(new ExerciseKey(workoutDto.getWorkoutExercises()[i].getExerciseId(),
            //    workoutDto.getWorkoutExercises()[i].getExerciseVersion()), new WorkoutKey(null, 1)));

            workoutExerciseBuilder.exerciseId(workoutDto.getWorkoutExercises()[i].getExerciseId());
            workoutExerciseBuilder.exerciseVersion(workoutDto.getWorkoutExercises()[i].getExerciseVersion());
            workoutExerciseBuilder.workoutId(null);
            workoutExerciseBuilder.workoutVersion(1);

            //workoutExerciseBuilder.id(new WorkoutExerciseKey(new ExerciseKey(), new WorkoutKey()));

            workoutExerciseBuilder.exDuration(workoutDto.getWorkoutExercises()[i].getExDuration());
            workoutExerciseBuilder.repetitions(workoutDto.getWorkoutExercises()[i].getRepetitions());
            workoutExerciseBuilder.sets(workoutDto.getWorkoutExercises()[i].getSets());

            //exercise.setId(workoutDto.getWorkoutExercises()[i].getExerciseId());
            //exercise.setVersion(workoutDto.getWorkoutExercises()[i].getExerciseVersion());
            //workoutExerciseBuilder.exercise(exercise);
            //workoutExerciseBuilder.workout(workout);

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
        builder.creatorId(workout.getCreator().getId());

        return builder.build();
    }

}
