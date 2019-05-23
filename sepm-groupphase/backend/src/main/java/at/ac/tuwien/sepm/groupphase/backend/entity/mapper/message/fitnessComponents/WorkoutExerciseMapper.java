package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDtoIn;
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
    public WorkoutExercise workoutExerciseDtoToWorkoutExercise(WorkoutExerciseDtoIn workoutExerciseDtoIn) {
        WorkoutExercise.WorkoutExerciseBuilder builder = new WorkoutExercise.WorkoutExerciseBuilder();

        //TODO: exercise
        builder.exDuration(workoutExerciseDtoIn.getExDuration());
        builder.repetitions(workoutExerciseDtoIn.getRepetitions());
        builder.sets(workoutExerciseDtoIn.getSets());

        return builder.build();
    }

    @Override
    public WorkoutExerciseDtoIn workoutExerciseToWorkoutExerciseDto(WorkoutExercise workoutExercise) {
        WorkoutExerciseDtoIn.WorkoutExerciseDtoInBuilder builder = new WorkoutExerciseDtoIn.WorkoutExerciseDtoInBuilder();

        //TODO: exercise
        builder.exDuration(workoutExercise.getExDuration());
        builder.repetitions(workoutExercise.getRepetitions());
        builder.sets(workoutExercise.getSets());

        return builder.build();
    }
}
