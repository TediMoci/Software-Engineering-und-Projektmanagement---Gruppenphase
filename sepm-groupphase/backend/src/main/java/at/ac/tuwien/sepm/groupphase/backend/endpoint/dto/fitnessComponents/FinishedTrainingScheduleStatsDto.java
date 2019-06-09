package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(value = "FinishedTrainingScheduleStatsDto", description = "A dto for FinishedTrainingScheduleStats entries via rest")
public class FinishedTrainingScheduleStatsDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(required = true, name = "Id of Dude")
    @NotNull(message = "DudeId must be given")
    private Long dudeId;

    @ApiModelProperty(required = true, name = "Id of TrainingSchedule formerly activated")
    @NotNull(message = "TrainingScheduleId must be given")
    private Long trainingScheduleId;

    @ApiModelProperty(required = true, name = "Version of TrainingSchedule formerly activated")
    @NotNull(message = "TrainingScheduleVersion must be given")
    private Integer trainingScheduleVersion;

    @ApiModelProperty(required = true, name = "Original version of TrainingSchedule formerly activated")
    @NotNull(message = "TrainingSchedule must be given")
    private TrainingScheduleDto trainingSchedule;

    @ApiModelProperty(required = true, name = "Sum of all hours trained of TrainingSchedule formerly activated")
    @NotNull(message = "TotalHours must be given")
    @Min(0)
    private Double totalHours;

    @ApiModelProperty(required = true, name = "Sum of all days trained of TrainingSchedule formerly activated")
    @NotNull(message = "TotalDays must be given")
    @Min(0)
    private Integer totalDays;

    @ApiModelProperty(required = true, name = "Sum of all calories burned during activation period of TrainingSchedule formerly activated")
    @NotNull(message = "totalCalorieConsumption must be given")
    @Min(0)
    private Double totalCalorieConsumption;

    @ApiModelProperty(required = true, name = "Number of intervals activated of TrainingSchedule formerly activated")
    @NotNull(message = "TotalIntervalRepetitions must be given")
    @Min(0)
    private Integer totalIntervalRepetitions;

    @ApiModelProperty(required = true, name = "Percentage of exercises with category strength of TrainingSchedule formerly activated")
    @NotNull(message = "StrengthPercent must be given")
    @Min(0)
    private Double strengthPercent;

    @ApiModelProperty(required = true, name = "Percentage of exercises with category endurance of TrainingSchedule formerly activated")
    @NotNull(message = "EndurancePercent must be given")
    @Min(0)
    private Double endurancePercent;

    @ApiModelProperty(required = true, name = "Percentage of exercises with category other of TrainingSchedule formerly activated")
    @NotNull(message = "OtherPercent must be given")
    @Min(0)
    private Double otherPercent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TrainingScheduleDto getTrainingScheduleDto() {
        return trainingSchedule;
    }

    public void setTrainingScheduleDto(TrainingScheduleDto trainingSchedule) {
        this.trainingSchedule = trainingSchedule;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Double getTotalCalorieConsumption() {
        return totalCalorieConsumption;
    }

    public void setTotalCalorieConsumption(Double totalCalorieConsumption) {
        this.totalCalorieConsumption = totalCalorieConsumption;
    }

    public Integer getTotalIntervalRepetitions() {
        return totalIntervalRepetitions;
    }

    public void setTotalIntervalRepetitions(Integer totalIntervalRepetitions) {
        this.totalIntervalRepetitions = totalIntervalRepetitions;
    }

    public Double getStrengthPercent() {
        return strengthPercent;
    }

    public void setStrengthPercent(Double strengthPercent) {
        this.strengthPercent = strengthPercent;
    }

    public Double getEndurancePercent() {
        return endurancePercent;
    }

    public void setEndurancePercent(Double endurancePercent) {
        this.endurancePercent = endurancePercent;
    }

    public Double getOtherPercent() {
        return otherPercent;
    }

    public void setOtherPercent(Double otherPercent) {
        this.otherPercent = otherPercent;
    }

    @Override
    public String toString() {
        return "FinishedTrainingScheduleStatsDto{" +
            "id=" + id +
            ", dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", trainingSchedule=" + trainingSchedule +
            ", totalHours=" + totalHours +
            ", totalDays=" + totalDays +
            ", totalCalorieConsumption=" + totalCalorieConsumption +
            ", totalIntervalRepetitions=" + totalIntervalRepetitions +
            ", strengthPercent=" + strengthPercent +
            ", endurancePercent=" + endurancePercent +
            ", otherPercent=" + otherPercent +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinishedTrainingScheduleStatsDto that = (FinishedTrainingScheduleStatsDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dudeId != null ? !dudeId.equals(that.dudeId) : that.dudeId != null) return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        if (trainingScheduleVersion != null ? !trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion != null)
            return false;
        if (trainingSchedule != null ? !trainingSchedule.equals(that.trainingSchedule) : that.trainingSchedule != null)
            return false;
        if (totalHours != null ? !totalHours.equals(that.totalHours) : that.totalHours != null) return false;
        if (totalDays != null ? !totalDays.equals(that.totalDays) : that.totalDays != null) return false;
        if (totalCalorieConsumption != null ? !totalCalorieConsumption.equals(that.totalCalorieConsumption) : that.totalCalorieConsumption != null)
            return false;
        if (totalIntervalRepetitions != null ? !totalIntervalRepetitions.equals(that.totalIntervalRepetitions) : that.totalIntervalRepetitions != null)
            return false;
        if (strengthPercent != null ? !strengthPercent.equals(that.strengthPercent) : that.strengthPercent != null)
            return false;
        if (endurancePercent != null ? !endurancePercent.equals(that.endurancePercent) : that.endurancePercent != null)
            return false;
        return otherPercent != null ? otherPercent.equals(that.otherPercent) : that.otherPercent == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dudeId != null ? dudeId.hashCode() : 0);
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        result = 31 * result + (trainingSchedule != null ? trainingSchedule.hashCode() : 0);
        result = 31 * result + (totalHours != null ? totalHours.hashCode() : 0);
        result = 31 * result + (totalDays != null ? totalDays.hashCode() : 0);
        result = 31 * result + (totalCalorieConsumption != null ? totalCalorieConsumption.hashCode() : 0);
        result = 31 * result + (totalIntervalRepetitions != null ? totalIntervalRepetitions.hashCode() : 0);
        result = 31 * result + (strengthPercent != null ? strengthPercent.hashCode() : 0);
        result = 31 * result + (endurancePercent != null ? endurancePercent.hashCode() : 0);
        result = 31 * result + (otherPercent != null ? otherPercent.hashCode() : 0);
        return result;
    }

    public static final class FinishedTrainingScheduleStatsDtoBuilder {
        private Long id;
        private Long dudeId;
        private Long trainingScheduleId;
        private Integer trainingScheduleVersion;
        private TrainingScheduleDto trainingSchedule;
        private Double totalHours;
        private Integer totalDays;
        private Double totalCalorieConsumption;
        private Integer totalIntervalRepetitions;
        private Double strengthPercent;
        private Double endurancePercent;
        private Double otherPercent;

        public FinishedTrainingScheduleStatsDtoBuilder() {
        }

        public FinishedTrainingScheduleStatsDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder dudeId(Long dudeId) {
            this.dudeId = dudeId;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder trainingScheduleId(Long trainingScheduleId) {
            this.trainingScheduleId = trainingScheduleId;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder trainingScheduleVersion(Integer trainingScheduleVersion) {
            this.trainingScheduleVersion = trainingScheduleVersion;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder trainingSchedule(TrainingScheduleDto trainingSchedule) {
            this.trainingSchedule = trainingSchedule;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder totalHours(Double totalHours) {
            this.totalHours = totalHours;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder totalDays(Integer totalDays) {
            this.totalDays = totalDays;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder totalCalorieConsumption(Double totalCalorieConsumption) {
            this.totalCalorieConsumption = totalCalorieConsumption;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder totalIntervalRepetitions(Integer totalIntervalRepetitions) {
            this.totalIntervalRepetitions = totalIntervalRepetitions;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder strengthPercent(Double strengthPercent) {
            this.strengthPercent = strengthPercent;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder endurancePercent(Double endurancePercent) {
            this.endurancePercent = endurancePercent;
            return this;
        }

        public FinishedTrainingScheduleStatsDtoBuilder otherPercent(Double otherPercent) {
            this.otherPercent = otherPercent;
            return this;
        }

        public FinishedTrainingScheduleStatsDto build() {
            FinishedTrainingScheduleStatsDto finishedTrainingScheduleStats = new FinishedTrainingScheduleStatsDto();
            finishedTrainingScheduleStats.setId(id);
            finishedTrainingScheduleStats.setDudeId(dudeId);
            finishedTrainingScheduleStats.setTrainingScheduleId(trainingScheduleId);
            finishedTrainingScheduleStats.setTrainingScheduleVersion(trainingScheduleVersion);
            finishedTrainingScheduleStats.setTrainingScheduleDto(trainingSchedule);
            finishedTrainingScheduleStats.setTotalHours(totalHours);
            finishedTrainingScheduleStats.setTotalDays(totalDays);
            finishedTrainingScheduleStats.setTotalCalorieConsumption(totalCalorieConsumption);
            finishedTrainingScheduleStats.setTotalIntervalRepetitions(totalIntervalRepetitions);
            finishedTrainingScheduleStats.setStrengthPercent(strengthPercent);
            finishedTrainingScheduleStats.setEndurancePercent(endurancePercent);
            finishedTrainingScheduleStats.setOtherPercent(otherPercent);
            return finishedTrainingScheduleStats;
        }
    }
}
