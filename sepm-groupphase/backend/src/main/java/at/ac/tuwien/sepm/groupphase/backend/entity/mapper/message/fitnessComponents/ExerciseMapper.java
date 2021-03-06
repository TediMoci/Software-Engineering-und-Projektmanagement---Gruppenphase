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
        builder.ratingNum(0);
        builder.ratingSum(0);
        builder.muscleGroup(exerciseDto.getMuscleGroup());
        builder.category(exerciseDto.getCategory());
        builder.isHistory(false);
        builder.imagePath(exerciseDto.getImagePath());
        builder.isPrivate(exerciseDto.getIsPrivate());
        builder.version(exerciseDto.getVersion());

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

        if (exercise.getRatingNum()==0){
            builder.rating(0.0);
        } else {
            builder.rating(roundToOne((double)(exercise.getRatingSum())/(double)(exercise.getRatingNum())));
        }

        builder.category(exercise.getCategory());
        builder.imagePath(exercise.getImagePath());
        builder.isPrivate(exercise.getIsPrivate());
        builder.version(exercise.getVersion());
        builder.creatorId(exercise.getCreator().getId());

        return builder.build();
    }

    private double roundToOne(double value){
        return Math.round(value * 10.0) / 10.0;
    }
}
