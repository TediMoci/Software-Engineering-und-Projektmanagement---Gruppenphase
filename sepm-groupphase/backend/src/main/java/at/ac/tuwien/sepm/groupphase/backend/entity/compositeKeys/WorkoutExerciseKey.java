package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class WorkoutExerciseKey implements Serializable {

    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(name = "workout_id")
    private Long workoutId;

    public WorkoutExerciseKey(Long exerciseId, Long workoutId) {
        this.exerciseId = exerciseId;
        this.workoutId = workoutId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    @Override
    public String toString() {
        return "WorkoutExerciseKey{" +
            "exerciseId=" + exerciseId +
            ", workoutId=" + workoutId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExerciseKey that = (WorkoutExerciseKey) o;

        if (exerciseId != null ? !exerciseId.equals(that.exerciseId) : that.exerciseId != null) return false;
        return workoutId != null ? workoutId.equals(that.workoutId) : that.workoutId == null;

    }

    @Override
    public int hashCode() {
        int result = exerciseId != null ? exerciseId.hashCode() : 0;
        result = 31 * result + (workoutId != null ? workoutId.hashCode() : 0);
        return result;
    }
}
