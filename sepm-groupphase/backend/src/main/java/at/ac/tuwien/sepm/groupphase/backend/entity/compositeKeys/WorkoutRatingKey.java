package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import java.io.Serializable;
import java.util.Objects;

public class WorkoutRatingKey implements Serializable {

    private Long dudeId;
    private Long workoutId;

    public WorkoutRatingKey() {
    }

    public WorkoutRatingKey(Long dudeId, Long workoutId) {
        this.dudeId = dudeId;
        this.workoutId = workoutId;
    }

    public Long getDudeId() {
        return dudeId;
    }

    public void setDudeId(Long dudeId) {
        this.dudeId = dudeId;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    @Override
    public String toString() {
        return "WorkoutRatingKey{" +
            "dudeId=" + dudeId +
            ", workoutId=" + workoutId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutRatingKey that = (WorkoutRatingKey) o;
        return Objects.equals(getDudeId(), that.getDudeId()) &&
            Objects.equals(getWorkoutId(), that.getWorkoutId());
    }

    @Override
    public int hashCode() {
        int result = workoutId != null ? workoutId.hashCode() : 0;
        result = 31 * result + (dudeId != null ? dudeId.hashCode() : 0);
        return result;
    }
}
