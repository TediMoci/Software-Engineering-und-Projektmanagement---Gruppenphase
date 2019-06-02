package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(value = "TrainingScheduleWorkoutDtoIn", description = "A dto for TrainingSchedule-Workout-relationships entries via rest")
public class TrainingScheduleWorkoutDtoIn {

    @ApiModelProperty(required = true, name = "Id of Workout of TrainingSchedule-Workout-Relationship")
    private Long workoutId;

    @ApiModelProperty(required = true, name = "Version of Workout of TrainingSchedule-Workout-Relationship")
    private Integer workoutVersion;

    @ApiModelProperty(required = true, name = "Day of TrainingSchedule-Workout-Relationship")
    @NotNull(message = "Day must not be null")
    @Min(value = 1, message = "Min for day is 1") @Max(value = 7, message = "Max for day is 7")
    private Integer day;

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getWorkoutVersion() {
        return workoutVersion;
    }

    public void setWorkoutVersion(Integer workoutVersion) {
        this.workoutVersion = workoutVersion;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public static TrainingScheduleWorkoutDtoInBuilder builder() {
        return new TrainingScheduleWorkoutDtoInBuilder();
    }

    @Override
    public String toString() {
        return "TrainingScheduleWorkoutDtoIn{" +
            "workoutId=" + workoutId +
            ", workoutVersion=" + workoutVersion +
            ", day=" + day +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingScheduleWorkoutDtoIn that = (TrainingScheduleWorkoutDtoIn) o;

        if (workoutId != null ? !workoutId.equals(that.workoutId) : that.workoutId != null) return false;
        if (workoutVersion != null ? !workoutVersion.equals(that.workoutVersion) : that.workoutVersion != null)
            return false;
        return day != null ? day.equals(that.day) : that.day == null;

    }

    @Override
    public int hashCode() {
        int result = workoutId != null ? workoutId.hashCode() : 0;
        result = 31 * result + (workoutVersion != null ? workoutVersion.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }

    public static final class TrainingScheduleWorkoutDtoInBuilder {
        private Long workoutId;
        private Integer workoutVersion;
        private Integer day;

        public TrainingScheduleWorkoutDtoInBuilder() {
        }

        public TrainingScheduleWorkoutDtoInBuilder workoutId(Long workoutId) {
            this.workoutId = workoutId;
            return this;
        }

        public TrainingScheduleWorkoutDtoInBuilder workoutVersion(Integer workoutVersion) {
            this.workoutVersion = workoutVersion;
            return this;
        }

        public TrainingScheduleWorkoutDtoInBuilder day(Integer day) {
            this.day = day;
            return this;
        }

        public TrainingScheduleWorkoutDtoIn build() {
            TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutDtoIn = new TrainingScheduleWorkoutDtoIn();
            trainingScheduleWorkoutDtoIn.setWorkoutId(workoutId);
            trainingScheduleWorkoutDtoIn.setWorkoutVersion(workoutVersion);
            trainingScheduleWorkoutDtoIn.setDay(day);
            return trainingScheduleWorkoutDtoIn;
        }
    }
}
