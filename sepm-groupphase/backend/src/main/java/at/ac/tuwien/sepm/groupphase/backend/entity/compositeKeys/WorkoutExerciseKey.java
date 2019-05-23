package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import java.io.Serializable;

public class WorkoutExerciseKey implements Serializable {

    private Long exerciseId;
    private Integer exerciseVersion;
    private Long workoutId;
    private Integer workoutVersion;

    public WorkoutExerciseKey() {
    }

    public WorkoutExerciseKey(Long exerciseId, Integer exerciseVersion, Long workoutId, Integer workoutVersion) {
        this.exerciseId = exerciseId;
        this.exerciseVersion = exerciseVersion;
        this.workoutId = workoutId;
        this.workoutVersion = workoutVersion;
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

    @Override
    public String toString() {
        return "WorkoutExerciseKey{" +
            "exerciseId=" + exerciseId +
            ", exerciseVersion=" + exerciseVersion +
            ", workoutId=" + workoutId +
            ", workoutVersion=" + workoutVersion +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExerciseKey that = (WorkoutExerciseKey) o;

        if (exerciseId != null ? !exerciseId.equals(that.exerciseId) : that.exerciseId != null) return false;
        if (exerciseVersion != null ? !exerciseVersion.equals(that.exerciseVersion) : that.exerciseVersion != null)
            return false;
        if (workoutId != null ? !workoutId.equals(that.workoutId) : that.workoutId != null) return false;
        return workoutVersion != null ? workoutVersion.equals(that.workoutVersion) : that.workoutVersion == null;

    }

    @Override
    public int hashCode() {
        int result = exerciseId != null ? exerciseId.hashCode() : 0;
        result = 31 * result + (exerciseVersion != null ? exerciseVersion.hashCode() : 0);
        result = 31 * result + (workoutId != null ? workoutId.hashCode() : 0);
        result = 31 * result + (workoutVersion != null ? workoutVersion.hashCode() : 0);
        return result;
    }
}
