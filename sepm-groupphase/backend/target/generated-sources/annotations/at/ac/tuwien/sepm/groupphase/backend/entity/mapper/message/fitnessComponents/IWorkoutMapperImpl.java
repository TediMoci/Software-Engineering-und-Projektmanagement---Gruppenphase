package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutDto.WorkoutDtoBuilder;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDtoOut;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout.WorkoutBuilder;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-02T22:09:01+0200",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class IWorkoutMapperImpl implements IWorkoutMapper {

    @Override
    public Workout workoutDtoToWorkout(WorkoutDto workoutDto) {
        if ( workoutDto == null ) {
            return null;
        }

        WorkoutBuilder workout = Workout.builder();

        workout.id( workoutDto.getId() );
        workout.version( workoutDto.getVersion() );
        workout.name( workoutDto.getName() );
        workout.description( workoutDto.getDescription() );
        workout.difficulty( workoutDto.getDifficulty() );
        workout.calorieConsumption( workoutDto.getCalorieConsumption() );
        workout.rating( workoutDto.getRating() );

        return workout.build();
    }

    @Override
    public WorkoutDto workoutToWorkoutDto(Workout workout) {
        if ( workout == null ) {
            return null;
        }

        WorkoutDtoBuilder workoutDto = WorkoutDto.builder();

        workoutDto.id( workout.getId() );
        workoutDto.version( workout.getVersion() );
        workoutDto.name( workout.getName() );
        workoutDto.description( workout.getDescription() );
        workoutDto.difficulty( workout.getDifficulty() );
        workoutDto.calorieConsumption( workout.getCalorieConsumption() );
        workoutDto.rating( workout.getRating() );

        return workoutDto.build();
    }

    @Override
    public WorkoutExerciseDtoOut workoutExerciseToWorkoutExerciseDtoOut(WorkoutExercise workoutExercise) {
        if ( workoutExercise == null ) {
            return null;
        }

        WorkoutExerciseDtoOut workoutExerciseDtoOut = new WorkoutExerciseDtoOut();

        workoutExerciseDtoOut.setExDuration( workoutExercise.getExDuration() );
        workoutExerciseDtoOut.setRepetitions( workoutExercise.getRepetitions() );
        workoutExerciseDtoOut.setSets( workoutExercise.getSets() );

        return workoutExerciseDtoOut;
    }
}
