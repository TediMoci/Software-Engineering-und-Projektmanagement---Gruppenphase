package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto.ExerciseDtoBuilder;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise.ExerciseBuilder;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-02T11:22:30+0200",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class IExerciseMapperImpl implements IExerciseMapper {

    @Override
    public Exercise exerciseDtoToExercise(ExerciseDto exerciseDto) {
        if ( exerciseDto == null ) {
            return null;
        }

        ExerciseBuilder exercise = Exercise.builder();

        exercise.id( exerciseDto.getId() );
        exercise.version( exerciseDto.getVersion() );
        exercise.name( exerciseDto.getName() );
        exercise.description( exerciseDto.getDescription() );
        exercise.equipment( exerciseDto.getEquipment() );
        exercise.muscleGroup( exerciseDto.getMuscleGroup() );
        exercise.rating( exerciseDto.getRating() );
        exercise.category( exerciseDto.getCategory() );
        Set<WorkoutExercise> set = exerciseDto.getWorkouts();
        if ( set != null ) {
            exercise.workouts( new HashSet<WorkoutExercise>( set ) );
        }

        return exercise.build();
    }

    @Override
    public ExerciseDto exerciseToExerciseDto(Exercise exercise) {
        if ( exercise == null ) {
            return null;
        }

        ExerciseDtoBuilder exerciseDto = ExerciseDto.builder();

        exerciseDto.id( exercise.getId() );
        exerciseDto.version( exercise.getVersion() );
        exerciseDto.name( exercise.getName() );
        exerciseDto.description( exercise.getDescription() );
        exerciseDto.equipment( exercise.getEquipment() );
        exerciseDto.muscleGroup( exercise.getMuscleGroup() );
        exerciseDto.rating( exercise.getRating() );
        exerciseDto.category( exercise.getCategory() );
        Set<WorkoutExercise> set = exercise.getWorkouts();
        if ( set != null ) {
            exerciseDto.workouts( new HashSet<WorkoutExercise>( set ) );
        }

        return exerciseDto.build();
    }
}
