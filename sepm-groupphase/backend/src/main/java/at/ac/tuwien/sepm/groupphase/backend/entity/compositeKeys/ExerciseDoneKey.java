package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import java.io.Serializable;

public class ExerciseDoneKey implements Serializable {

    private Long activeTrainingScheduleId;
    private Long dudeId;
    private Long trainingScheduleId;
    private Long trainingScheduleVersion;
    private Long exerciseId;
    private Integer exerciseVersion;
    private Long workoutId;
    private Integer workoutVersion;
    private Integer day;

    public ExerciseDoneKey() {
    }

    public ExerciseDoneKey(Long activeTrainingScheduleId, Long dudeId, Long trainingScheduleId, Long trainingScheduleVersion, Long exerciseId, Integer exerciseVersion, Long workoutId, Integer workoutVersion, Integer day) {
        this.activeTrainingScheduleId = activeTrainingScheduleId;
        this.dudeId = dudeId;
        this.trainingScheduleId = trainingScheduleId;
        this.trainingScheduleVersion = trainingScheduleVersion;
        this.exerciseId = exerciseId;
        this.exerciseVersion = exerciseVersion;
        this.workoutId = workoutId;
        this.workoutVersion = workoutVersion;
        this.day = day;
    }

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

    public Long getTrainingScheduleVersion() {
        return trainingScheduleVersion;
    }

    public void setTrainingScheduleVersion(Long trainingScheduleVersion) {
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

    @Override
    public String toString() {
        return "ExerciseDoneKey{" +
            "activeTrainingScheduleId=" + activeTrainingScheduleId +
            ", dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", exerciseId=" + exerciseId +
            ", exerciseVersion=" + exerciseVersion +
            ", workoutId=" + workoutId +
            ", workoutVersion=" + workoutVersion +
            ", day=" + day +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseDoneKey that = (ExerciseDoneKey) o;

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
        return day != null ? day.equals(that.day) : that.day == null;

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
        return result;
    }
}
