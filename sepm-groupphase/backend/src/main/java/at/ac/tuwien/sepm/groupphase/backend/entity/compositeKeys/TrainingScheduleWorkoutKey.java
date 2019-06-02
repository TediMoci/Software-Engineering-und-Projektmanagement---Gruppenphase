package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import java.io.Serializable;

public class TrainingScheduleWorkoutKey implements Serializable {

    private Long workoutId;
    private Integer workoutVersion;
    private Long trainingScheduleId;
    private Integer trainingScheduleVersion;
    private Integer day;

    public TrainingScheduleWorkoutKey() {
    }

    public TrainingScheduleWorkoutKey(Long workoutId, Integer workoutVersion, Long trainingScheduleId, Integer trainingScheduleVersion, Integer day) {
        this.workoutId = workoutId;
        this.workoutVersion = workoutVersion;
        this.trainingScheduleId = trainingScheduleId;
        this.trainingScheduleVersion = trainingScheduleVersion;
        this.day = day;
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "TrainingScheduleWorkoutKey{" +
            "workoutId=" + workoutId +
            ", workoutVersion=" + workoutVersion +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", day=" + day +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingScheduleWorkoutKey that = (TrainingScheduleWorkoutKey) o;

        if (workoutId != null ? !workoutId.equals(that.workoutId) : that.workoutId != null) return false;
        if (workoutVersion != null ? !workoutVersion.equals(that.workoutVersion) : that.workoutVersion != null)
            return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        if (trainingScheduleVersion != null ? !trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion != null)
            return false;
        return day != null ? day.equals(that.day) : that.day == null;

    }

    @Override
    public int hashCode() {
        int result = workoutId != null ? workoutId.hashCode() : 0;
        result = 31 * result + (workoutVersion != null ? workoutVersion.hashCode() : 0);
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }
}
