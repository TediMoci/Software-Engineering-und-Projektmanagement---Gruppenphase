package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.FinishedTrainingScheduleStatsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.FinishedTrainingScheduleStats;
import org.springframework.stereotype.Component;

@Component
public class StatsMapper implements IStatsMapper {

    @Override
    public FinishedTrainingScheduleStatsDto finishedTrainingScheduleStatsToFinishedTrainingScheduleStatsDto(FinishedTrainingScheduleStats stats) {
        FinishedTrainingScheduleStatsDto.FinishedTrainingScheduleStatsDtoBuilder builder = new FinishedTrainingScheduleStatsDto.FinishedTrainingScheduleStatsDtoBuilder();

        builder.id(stats.getId());
        builder.dudeId(stats.getDude().getId());
        builder.trainingScheduleId(stats.getTrainingSchedule().getId());
        builder.trainingScheduleVersion(stats.getTrainingSchedule().getVersion());

        TrainingSchedule ts = stats.getTrainingSchedule();
        TrainingScheduleDto.TrainingScheduleDtoBuilder builderTs = new TrainingScheduleDto.TrainingScheduleDtoBuilder();
        builderTs.id(ts.getId());
        builderTs.version(ts.getVersion());
        builderTs.name(ts.getName());
        builderTs.description(ts.getDescription());
        builderTs.difficulty(ts.getDifficulty());
        builderTs.intervalLength(ts.getIntervalLength());
        builderTs.rating(ts.getRating());
        builderTs.creatorId(ts.getCreator().getId());
        builder.trainingSchedule(builderTs.build());

        builder.totalHours(stats.getTotalHours());
        builder.totalDays(stats.getTotalDays());
        builder.totalCalorieConsumption(stats.getTotalCalorieConsumption());
        builder.totalIntervalRepetitions(stats.getTotalIntervalRepetitions());
        builder.strengthPercent(stats.getStrengthPercent());
        builder.endurancePercent(stats.getEndurancePercent());
        builder.otherPercent(stats.getOtherPercent());

        return builder.build();
    }
}
