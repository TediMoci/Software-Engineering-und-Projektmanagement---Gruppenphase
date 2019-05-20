package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper implements IExerciseMapper {


    @Override
    public Exercise exerciseDtoToExercise(ExerciseDto exerciseDto) {
        Exercise.ExerciseBuilder builder = new Exercise.ExerciseBuilder();

        builder.id(exerciseDto.getId());
        builder.name(exerciseDto.getName());
        builder.description(exerciseDto.getDescription());
        builder.equipment(exerciseDto.getEquipment());
        builder.muscleGroup(exerciseDto.getMuscleGroup());
        builder.rating(exerciseDto.getRating());
        builder.category(exerciseDto.getCategory());
        builder.isHistory(false);
        builder.version(1);
        builder.workouts(exerciseDto.getWorkouts());

        Dude.DudeBuilder dudeBuilder = new Dude.DudeBuilder();
        dudeBuilder.id(exerciseDto.getCreatorId());
        Dude dude = dudeBuilder.build();
        builder.creator(dude);

        return builder.build();
    }

    @Override
    public ExerciseDto exerciseToExerciseDto(Exercise exercise) {
        ExerciseDto.ExerciseDtoBuilder builder = new ExerciseDto.ExerciseDtoBuilder();

        builder.id(exercise.getId());
        builder.name(exercise.getName());
        builder.description(exercise.getDescription());
        builder.equipment(exercise.getEquipment());
        builder.muscleGroup(exercise.getMuscleGroup());
        builder.rating(exercise.getRating());
        builder.category(exercise.getCategory());
        builder.version(exercise.getVersion());
        builder.workouts(exercise.getWorkouts());
        builder.creatorId(exercise.getCreator().getId());

        return builder.build();
    }
}
