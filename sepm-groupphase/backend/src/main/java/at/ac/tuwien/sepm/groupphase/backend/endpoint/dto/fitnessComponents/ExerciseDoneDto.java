package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "ExerciseDoneDto", description = "A dto for exercise-done entries in active training schedule via rest")
public class ExerciseDoneDto {

    @ApiModelProperty(name = "Id of ActiveTrainingSchedule")
    private Long activeTrainingScheduleId;

    @ApiModelProperty(required = true, name = "Id of Dude")
    @NotNull(message = "dudeId must be given")
    private Long dudeId;

    @ApiModelProperty(required = true, name = "Id of TrainingSchedule")
    @NotNull(message = "trainingScheduleId must be given")
    private Long trainingScheduleId;

    @ApiModelProperty(required = true, name = "Version of TrainingSchedule")
    @NotNull(message = "trainingScheduleVersion must be given")
    private Integer trainingScheduleVersion;

    @ApiModelProperty(required = true, name = "Id of Exercise")
    @NotNull(message = "exerciseId must be given")
    private Long exerciseId;

    @ApiModelProperty(required = true, name = "Version of Exercise")
    @NotNull(message = "exerciseVersion must be given")
    private Integer exerciseVersion;

    @ApiModelProperty(required = true, name = "Id of Workout")
    @NotNull(message = "workoutId must be given")
    private Long workoutId;

    @ApiModelProperty(required = true, name = "Version of Workout")
    @NotNull(message = "workoutVersion must be given")
    private Integer workoutVersion;

    @ApiModelProperty(required = true, name = "Day in TrainingSchedule")
    @NotNull(message = "day must be given")
    private Integer day;

    @ApiModelProperty(required = true, name = "Exercise done or not")
    @NotNull(message = "done must be given")
    private Boolean done;

    public Long getActiveTrainingScheduleId() {
        return activeTrainingScheduleId;
    }

    public void setActiveTrainingScheduleId(Long activeTrainingScheduleId) {
        this.activeTrainingScheduleId = activeTrainingScheduleId;
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

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getExerciseVersion() {
        return exerciseVersion;
    }

    public void setExerciseVersion(Integer exerciseVersion) {
        this.exerciseVersion = exerciseVersion;
    }

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

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public static ExerciseDoneDtoBuilder builder() {
        return new ExerciseDoneDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExerciseDoneDto{" +
            "activeTrainingScheduleId=" + activeTrainingScheduleId +
            ", dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", exerciseId=" + exerciseId +
            ", exerciseVersion=" + exerciseVersion +
            ", workoutId=" + workoutId +
            ", workoutVersion=" + workoutVersion +
            ", day=" + day +
            ", done=" + done +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseDoneDto that = (ExerciseDoneDto) o;

        if (activeTrainingScheduleId != null ? !activeTrainingScheduleId.equals(that.activeTrainingScheduleId) : that.activeTrainingScheduleId != null)
            return false;
        if (dudeId != null ? !dudeId.equals(that.dudeId) : that.dudeId != null) return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        if (trainingScheduleVersion != null ? !trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion != null)
            return false;
        if (exerciseId != null ? !exerciseId.equals(that.exerciseId) : that.exerciseId != null) return false;
        if (exerciseVersion != null ? !exerciseVersion.equals(that.exerciseVersion) : that.exerciseVersion != null)
            return false;
        if (workoutId != null ? !workoutId.equals(that.workoutId) : that.workoutId != null) return false;
        if (workoutVersion != null ? !workoutVersion.equals(that.workoutVersion) : that.workoutVersion != null)
            return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        return done != null ? done.equals(that.done) : that.done == null;

    }

    @Override
    public int hashCode() {
        int result = activeTrainingScheduleId != null ? activeTrainingScheduleId.hashCode() : 0;
        result = 31 * result + (dudeId != null ? dudeId.hashCode() : 0);
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        result = 31 * result + (exerciseId != null ? exerciseId.hashCode() : 0);
        result = 31 * result + (exerciseVersion != null ? exerciseVersion.hashCode() : 0);
        result = 31 * result + (workoutId != null ? workoutId.hashCode() : 0);
        result = 31 * result + (workoutVersion != null ? workoutVersion.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (done != null ? done.hashCode() : 0);
        return result;
    }

    public static final class ExerciseDoneDtoBuilder {
        private Long activeTrainingScheduleId;
        private Long dudeId;
        private Long trainingScheduleId;
        private Integer trainingScheduleVersion;
        private Long exerciseId;
        private Integer exerciseVersion;
        private Long workoutId;
        private Integer workoutVersion;
        private Integer day;
        private Boolean done;

        public ExerciseDoneDtoBuilder() {
        }

        public ExerciseDoneDtoBuilder activeTrainingScheduleId(Long activeTrainingScheduleId) {
            this.activeTrainingScheduleId = activeTrainingScheduleId;
            return this;
        }

        public ExerciseDoneDtoBuilder dudeId(Long dudeId) {
            this.dudeId = dudeId;
            return this;
        }

        public ExerciseDoneDtoBuilder trainingScheduleId(Long trainingScheduleId) {
            this.trainingScheduleId = trainingScheduleId;
            return this;
        }

        public ExerciseDoneDtoBuilder trainingScheduleVersion(Integer trainingScheduleVersion) {
            this.trainingScheduleVersion = trainingScheduleVersion;
            return this;
        }

        public ExerciseDoneDtoBuilder exerciseId(Long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public ExerciseDoneDtoBuilder exerciseVersion(Integer exerciseVersion) {
            this.exerciseVersion = exerciseVersion;
            return this;
        }

        public ExerciseDoneDtoBuilder workoutId(Long workoutId) {
            this.workoutId = workoutId;
            return this;
        }

        public ExerciseDoneDtoBuilder workoutVersion(Integer workoutVersion) {
            this.workoutVersion = workoutVersion;
            return this;
        }

        public ExerciseDoneDtoBuilder day(Integer day) {
            this.day = day;
            return this;
        }

        public ExerciseDoneDtoBuilder done(Boolean done) {
            this.done = done;
            return this;
        }

        public ExerciseDoneDto build() {
            ExerciseDoneDto exerciseDoneDto = new ExerciseDoneDto();
            exerciseDoneDto.setActiveTrainingScheduleId(activeTrainingScheduleId);
            exerciseDoneDto.setDudeId(dudeId);
            exerciseDoneDto.setTrainingScheduleId(trainingScheduleId);
            exerciseDoneDto.setTrainingScheduleVersion(trainingScheduleVersion);
            exerciseDoneDto.setExerciseId(exerciseId);
            exerciseDoneDto.setExerciseVersion(exerciseVersion);
            exerciseDoneDto.setWorkoutId(workoutId);
            exerciseDoneDto.setWorkoutVersion(workoutVersion);
            exerciseDoneDto.setDay(day);
            exerciseDoneDto.setDone(done);
            return exerciseDoneDto;
        }
    }
}
