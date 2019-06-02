package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleWorkoutDtoOut;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainingScheduleMapper implements ITrainingScheduleMapper {

    @Override
    public TrainingSchedule trainingScheduleDtoToTrainingSchedule(TrainingScheduleDto trainingScheduleDto) {
        TrainingSchedule.TrainingScheduleBuilder builder = new TrainingSchedule.TrainingScheduleBuilder();

        builder.id(trainingScheduleDto.getId());
        builder.version(trainingScheduleDto.getVersion());
        builder.name(trainingScheduleDto.getName());
        builder.description(trainingScheduleDto.getDescription());
        builder.difficulty(trainingScheduleDto.getDifficulty());
        builder.rating(trainingScheduleDto.getRating());
        builder.isHistory(false);

        List<TrainingScheduleWorkout> trainingScheduleWorkouts = new ArrayList<>();
        TrainingScheduleWorkout.TrainingScheduleWorkoutBuilder trainingScheduleWorkoutBuilder;
        for (int i = 0; i < trainingScheduleDto.getTrainingScheduleWorkouts().length; i++) {
            trainingScheduleWorkoutBuilder = new TrainingScheduleWorkout.TrainingScheduleWorkoutBuilder();
            trainingScheduleWorkoutBuilder.workoutId(trainingScheduleDto.getTrainingScheduleWorkouts()[i].getWorkoutId());
            trainingScheduleWorkoutBuilder.workoutVersion(trainingScheduleDto.getTrainingScheduleWorkouts()[i].getWorkoutVersion());
            trainingScheduleWorkoutBuilder.trainingScheduleId(trainingScheduleDto.getId());
            trainingScheduleWorkoutBuilder.trainingScheduleVersion(trainingScheduleDto.getVersion());
            trainingScheduleWorkoutBuilder.day(trainingScheduleDto.getTrainingScheduleWorkouts()[i].getDay());
            trainingScheduleWorkouts.add(trainingScheduleWorkoutBuilder.build());
        }
        builder.workouts(trainingScheduleWorkouts);

        Dude.DudeBuilder dudeBuilder = new Dude.DudeBuilder();
        dudeBuilder.id(trainingScheduleDto.getCreatorId());
        Dude dude = dudeBuilder.build();
        builder.creator(dude);

        return builder.build();
    }

    @Override
    public TrainingScheduleDto trainingScheduleToTrainingScheduleDto(TrainingSchedule trainingSchedule) {
        TrainingScheduleDto.TrainingScheduleDtoBuilder builder = new TrainingScheduleDto.TrainingScheduleDtoBuilder();

        builder.id(trainingSchedule.getId());
        builder.version(trainingSchedule.getVersion());
        builder.name(trainingSchedule.getName());
        builder.description(trainingSchedule.getDescription());
        builder.difficulty(trainingSchedule.getDifficulty());
        builder.rating(trainingSchedule.getRating());
        builder.creatorId(trainingSchedule.getCreator().getId());

        return builder.build();
    }

    @Override
    public TrainingScheduleWorkoutDtoOut trainingScheduleWorkoutToTrainingScheduleWorkoutDtoOut(TrainingScheduleWorkout trainingScheduleWorkout) {
        TrainingScheduleWorkoutDtoOut.TrainingScheduleWorkoutDtoOutBuilder builder = new TrainingScheduleWorkoutDtoOut.TrainingScheduleWorkoutDtoOutBuilder();

        builder.id(trainingScheduleWorkout.getWorkout().getId());
        builder.version(trainingScheduleWorkout.getWorkout().getVersion());
        builder.name(trainingScheduleWorkout.getWorkout().getName());
        builder.description(trainingScheduleWorkout.getWorkout().getDescription());
        builder.difficulty(trainingScheduleWorkout.getWorkout().getDifficulty());
        builder.calorieConsumption(trainingScheduleWorkout.getWorkout().getCalorieConsumption());
        builder.rating(trainingScheduleWorkout.getWorkout().getRating());
        builder.creatorName(trainingScheduleWorkout.getWorkout().getCreator().getName());
        builder.day(trainingScheduleWorkout.getDay());

        return builder.build();
    }
}
