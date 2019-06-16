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
        builder.ratingNum(0);
        builder.ratingSum(0);
        builder.isHistory(false);

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

        if (workout.getRatingNum()==0){
            builder.rating(0.0);
        } else {
            builder.rating(roundToOne((double)(workout.getRatingSum())/(double)(workout.getRatingNum())));
        }

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

        if (workoutExercise.getExercise().getRatingNum()==0){
            builder.rating(0.0);
        } else {
            builder.rating(roundToOne((double)(workoutExercise.getExercise().getRatingSum())/(double)(workoutExercise.getExercise().getRatingNum())));
        }

        builder.category(workoutExercise.getExercise().getCategory());
        builder.creatorName(workoutExercise.getExercise().getCreator().getName());
        builder.exDuration(workoutExercise.getExDuration());
        builder.repetitions(workoutExercise.getRepetitions());
        builder.sets(workoutExercise.getSets());

        return builder.build();
    }

    private double roundToOne(double value){
        return Math.round(value * 10.0) / 10.0;
    }

}
