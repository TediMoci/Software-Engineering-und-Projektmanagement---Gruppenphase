package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkoutExerciseMapper implements IWorkoutExerciseMapper {

    private final IWorkoutMapper workoutMapper;
    private final IExerciseMapper exerciseMapper;

    @Autowired
    public WorkoutExerciseMapper(IWorkoutMapper workoutMapper, IExerciseMapper exerciseMapper) {
        this.workoutMapper = workoutMapper;
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    public WorkoutExercise workoutExerciseDtoToWorkoutExercise(WorkoutExerciseDto workoutExerciseDto) {
        WorkoutExercise.WorkoutExerciseBuilder builder = new WorkoutExercise.WorkoutExerciseBuilder();

        builder.id(null);
        builder.workout(workoutMapper.workoutDtoToWorkout(workoutExerciseDto.getWorkoutDto()));
        builder.exercise(exerciseMapper.exerciseDtoToExercise(workoutExerciseDto.getExerciseDto()));
        builder.duration(workoutExerciseDto.getDuration());
        builder.repetitions(workoutExerciseDto.getRepetitions());
        builder.sets(workoutExerciseDto.getSets());

        return builder.build();
    }

    @Override
    public WorkoutExerciseDto workoutExerciseToWorkoutExerciseDto(WorkoutExercise workoutExercise) {
        WorkoutExerciseDto.WorkoutExerciseDtoBuilder builder = new WorkoutExerciseDto.WorkoutExerciseDtoBuilder();

        builder.workoutDto(workoutMapper.workoutToWorkoutDto(workoutExercise.getWorkout()));
        builder.exerciseDto(exerciseMapper.exerciseToExerciseDto(workoutExercise.getExercise()));
        builder.duration(workoutExercise.getDuration());
        builder.repetitions(workoutExercise.getRepetitions());
        builder.sets(workoutExercise.getSets());

        return builder.build();
    }
}
