package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleDto.TrainingScheduleDtoBuilder;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleWorkoutDtoOut;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleWorkoutDtoOut.TrainingScheduleWorkoutDtoOutBuilder;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule.TrainingScheduleBuilder;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-02T11:22:30+0200",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class ITrainingScheduleMapperImpl implements ITrainingScheduleMapper {

    @Override
    public TrainingSchedule trainingScheduleDtoToTrainingSchedule(TrainingScheduleDto trainingScheduleDto) {
        if ( trainingScheduleDto == null ) {
            return null;
        }

        TrainingScheduleBuilder trainingSchedule = TrainingSchedule.builder();

        trainingSchedule.id( trainingScheduleDto.getId() );
        trainingSchedule.version( trainingScheduleDto.getVersion() );
        trainingSchedule.name( trainingScheduleDto.getName() );
        trainingSchedule.description( trainingScheduleDto.getDescription() );
        trainingSchedule.difficulty( trainingScheduleDto.getDifficulty() );
        trainingSchedule.rating( trainingScheduleDto.getRating() );

        return trainingSchedule.build();
    }

    @Override
    public TrainingScheduleDto trainingScheduleToTrainingScheduleDto(TrainingSchedule trainingSchedule) {
        if ( trainingSchedule == null ) {
            return null;
        }

        TrainingScheduleDtoBuilder trainingScheduleDto = TrainingScheduleDto.builder();

        trainingScheduleDto.id( trainingSchedule.getId() );
        trainingScheduleDto.version( trainingSchedule.getVersion() );
        trainingScheduleDto.name( trainingSchedule.getName() );
        trainingScheduleDto.description( trainingSchedule.getDescription() );
        trainingScheduleDto.difficulty( trainingSchedule.getDifficulty() );
        trainingScheduleDto.rating( trainingSchedule.getRating() );

        return trainingScheduleDto.build();
    }

    @Override
    public TrainingScheduleWorkoutDtoOut trainingScheduleWorkoutToTrainingScheduleWorkoutDtoOut(TrainingScheduleWorkout trainingScheduleWorkout) {
        if ( trainingScheduleWorkout == null ) {
            return null;
        }

        TrainingScheduleWorkoutDtoOutBuilder trainingScheduleWorkoutDtoOut = TrainingScheduleWorkoutDtoOut.builder();

        trainingScheduleWorkoutDtoOut.day( trainingScheduleWorkout.getDay() );

        return trainingScheduleWorkoutDtoOut.build();
    }
}
