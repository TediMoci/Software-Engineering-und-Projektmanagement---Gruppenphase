package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(value = "ActiveTrainingScheduleDto", description = "A dto for setting active training schedule via rest")
public class ActiveTrainingScheduleDto {

    @ApiModelProperty(required = true, name = "Id of Dude")
    @NotNull(message = "dudeId must be given")
    private Long dudeId;

    @ApiModelProperty(required = true, name = "Id of TrainingSchedule")
    @NotNull(message = "trainingScheduleId must be given")
    private Long trainingScheduleId;

    @ApiModelProperty(required = true, name = "Version of TrainingSchedule")
    @NotNull(message = "trainingScheduleVersion must be given")
    private Integer trainingScheduleVersion;

    @ApiModelProperty(name = "StartDate of TrainingSchedule")
    private LocalDate startDate;    // Only for output

    @ApiModelProperty(required = true, name = "Number of interval-repetitions")
    @NotNull(message = "intervalRepetitions must be given")
    @Min(value = 1, message = "Min for intervalRepetitions is 1")
    private Integer intervalRepetitions;

    public Long getDudeId() {
        return dudeId;
    }

    public void setDudeId(Long dudeId) {
        this.dudeId = dudeId;
    }

    public Long getTrainingScheduleId() {
        return trainingScheduleId;
    }

    public void setTrainingScheduleId(Long trainingScheduleId) {
        this.trainingScheduleId = trainingScheduleId;
    }

    public Integer getTrainingScheduleVersion() {
        return trainingScheduleVersion;
    }

    public void setTrainingScheduleVersion(Integer trainingScheduleVersion) {
        this.trainingScheduleVersion = trainingScheduleVersion;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getIntervalRepetitions() {
        return intervalRepetitions;
    }

    public void setIntervalRepetitions(Integer intervalRepetitions) {
        this.intervalRepetitions = intervalRepetitions;
    }

    public static ActiveTrainingScheduleDtoBuilder builder() {
        return new ActiveTrainingScheduleDtoBuilder();
    }

    @Override
    public String toString() {
        return "ActiveTrainingScheduleDto{" +
            "dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", startDate=" + startDate +
            ", intervalRepetitions=" + intervalRepetitions +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiveTrainingScheduleDto that = (ActiveTrainingScheduleDto) o;

        if (dudeId != null ? !dudeId.equals(that.dudeId) : that.dudeId != null) return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        if (trainingScheduleVersion != null ? !trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        return intervalRepetitions != null ? intervalRepetitions.equals(that.intervalRepetitions) : that.intervalRepetitions == null;

    }

    @Override
    public int hashCode() {
        int result = dudeId != null ? dudeId.hashCode() : 0;
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (intervalRepetitions != null ? intervalRepetitions.hashCode() : 0);
        return result;
    }

    public static final class ActiveTrainingScheduleDtoBuilder {
        private Long dudeId;
        private Long trainingScheduleId;
        private Integer trainingScheduleVersion;
        private LocalDate startDate;
        private Integer intervalRepetitions;

        public ActiveTrainingScheduleDtoBuilder() {
        }

        public ActiveTrainingScheduleDtoBuilder dudeId(Long dudeId) {
            this.dudeId = dudeId;
            return this;
        }

        public ActiveTrainingScheduleDtoBuilder trainingScheduleId(Long trainingScheduleId) {
            this.trainingScheduleId = trainingScheduleId;
            return this;
        }

        public ActiveTrainingScheduleDtoBuilder trainingScheduleVersion(Integer trainingScheduleVersion) {
            this.trainingScheduleVersion = trainingScheduleVersion;
            return this;
        }

        public ActiveTrainingScheduleDtoBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ActiveTrainingScheduleDtoBuilder intervalRepetitions(Integer intervalRepetitions) {
            this.intervalRepetitions = intervalRepetitions;
            return this;
        }

        public ActiveTrainingScheduleDto build() {
            ActiveTrainingScheduleDto activeTrainingScheduleDto = new ActiveTrainingScheduleDto();
            activeTrainingScheduleDto.setDudeId(dudeId);
            activeTrainingScheduleDto.setTrainingScheduleId(trainingScheduleId);
            activeTrainingScheduleDto.setTrainingScheduleVersion(trainingScheduleVersion);
            activeTrainingScheduleDto.setStartDate(startDate);
            activeTrainingScheduleDto.setIntervalRepetitions(intervalRepetitions);
            return activeTrainingScheduleDto;
        }
    }
}
