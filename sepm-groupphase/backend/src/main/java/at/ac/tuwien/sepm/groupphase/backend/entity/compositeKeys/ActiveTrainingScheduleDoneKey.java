package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import javax.persistence.Embeddable;

@Embeddable
public class ActiveTrainingScheduleDoneKey {

    private Long exerciseId;
    private Integer exerciseVersion;
    private Long workoutId;
    private Integer workoutVersion;
    private Integer interval;
    private Integer day;

    public ActiveTrainingScheduleDoneKey(Long exerciseId, Integer exerciseVersion, Long workoutId, Integer workoutVersion, Integer interval, Integer day) {
        this.exerciseId = exerciseId;
        this.exerciseVersion = exerciseVersion;
        this.workoutId = workoutId;
        this.workoutVersion = workoutVersion;
        this.interval = interval;
        this.day = day;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public Integer getExerciseVersion() {
        return exerciseVersion;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public Integer getWorkoutVersion() {
        return workoutVersion;
    }

    public Integer getInterval() {
        return interval;
    }

    public Integer getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "ActiveTrainingScheduleDoneKey{" +
            "exerciseId=" + exerciseId +
            ", exerciseVersion=" + exerciseVersion +
            ", workoutId=" + workoutId +
            ", workoutVersion=" + workoutVersion +
            ", interval=" + interval +
            ", day=" + day +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiveTrainingScheduleDoneKey that = (ActiveTrainingScheduleDoneKey) o;

        if (exerciseId != null ? !exerciseId.equals(that.exerciseId) : that.exerciseId != null) return false;
        if (exerciseVersion != null ? !exerciseVersion.equals(that.exerciseVersion) : that.exerciseVersion != null)
            return false;
        if (workoutId != null ? !workoutId.equals(that.workoutId) : that.workoutId != null) return false;
        if (workoutVersion != null ? !workoutVersion.equals(that.workoutVersion) : that.workoutVersion != null)
            return false;
        if (interval != null ? !interval.equals(that.interval) : that.interval != null) return false;
        return day != null ? day.equals(that.day) : that.day == null;

    }

    @Override
    public int hashCode() {
        int result = exerciseId != null ? exerciseId.hashCode() : 0;
        result = 31 * result + (exerciseVersion != null ? exerciseVersion.hashCode() : 0);
        result = 31 * result + (workoutId != null ? workoutId.hashCode() : 0);
        result = 31 * result + (workoutVersion != null ? workoutVersion.hashCode() : 0);
        result = 31 * result + (interval != null ? interval.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }
}
