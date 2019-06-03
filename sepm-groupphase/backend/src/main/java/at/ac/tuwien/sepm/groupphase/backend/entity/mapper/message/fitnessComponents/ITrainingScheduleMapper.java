package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ActiveTrainingScheduleDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDoneDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleWorkoutDtoOut;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TrainingScheduleMapper.class)
public interface ITrainingScheduleMapper {

    /**
     * @param trainingScheduleDto to be mapped to an entity
     * @return TrainingSchedule-entity mapped from given TrainingSchedule-DTO
     */
    TrainingSchedule trainingScheduleDtoToTrainingSchedule(TrainingScheduleDto trainingScheduleDto);

    /**
     * @param trainingSchedule to be mapped to a DTO
     * @return TrainingSchedule-DTO mapped from given TrainingSchedule-entity
     */
    TrainingScheduleDto trainingScheduleToTrainingScheduleDto(TrainingSchedule trainingSchedule);

    /**
     * @param trainingScheduleWorkout to be mapped to a DTO-out
     * @return TrainingScheduleWorkout-DTO-out mapped from given TrainingScheduleWorkout-entity
     */
    TrainingScheduleWorkoutDtoOut trainingScheduleWorkoutToTrainingScheduleWorkoutDtoOut(TrainingScheduleWorkout trainingScheduleWorkout);

    /**
     * @param exerciseDoneDto to be mapped to an entity
     * @return ExerciseDone-entity mapped from given ExerciseDone-DTO
     */
    ExerciseDone exerciseDoneDtoToExerciseDone(ExerciseDoneDto exerciseDoneDto);

    /**
     * @param activeTrainingScheduleDto to be mapped to an entity
     * @return ActiveTrainingSchedule-entity mapped from given ActiveTrainingSchedule-DTO
     */
    ActiveTrainingSchedule activeTrainingScheduleDtoToActiveTrainingSchedule(ActiveTrainingScheduleDto activeTrainingScheduleDto);

}
