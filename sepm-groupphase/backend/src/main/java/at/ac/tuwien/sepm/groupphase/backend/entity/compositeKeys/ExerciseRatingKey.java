package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import java.io.Serializable;
import java.util.Objects;

public class ExerciseRatingKey implements Serializable {

    private Long dudeId;
    private Long exerciseId;

    public ExerciseRatingKey() {
    }

    public ExerciseRatingKey(Long dudeId, Long exerciseId) {
        this.dudeId = dudeId;
        this.exerciseId = exerciseId;
    }

    public Long getDudeId() {
        return dudeId;
    }

    public void setDudeId(Long dudeId) {
        this.dudeId = dudeId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseRatingKey that = (ExerciseRatingKey) o;
        return Objects.equals(getDudeId(), that.getDudeId()) &&
            Objects.equals(getExerciseId(), that.getExerciseId());
    }

    @Override
    public int hashCode() {
        int result = exerciseId != null ? exerciseId.hashCode() : 0;
        result = 31 * result + (dudeId != null ? dudeId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExerciseRatingKey{" +
            "dudeId=" + dudeId +
            ", exerciseId=" + exerciseId +
            '}';
    }
}