package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.FinishedTrainingScheduleStatsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.FinishedTrainingScheduleStats;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = StatsMapper.class)
public interface IStatsMapper {

    /**
     *
     * @param stats to be mapped to a DTO
     * @return FinishedTrainingScheduleStats-DTO mapped from given FinishedTrainingScheduleStats-entity
     */
    FinishedTrainingScheduleStatsDto finishedTrainingScheduleStatsToFinishedTrainingScheduleStatsDto(FinishedTrainingScheduleStats stats);
}