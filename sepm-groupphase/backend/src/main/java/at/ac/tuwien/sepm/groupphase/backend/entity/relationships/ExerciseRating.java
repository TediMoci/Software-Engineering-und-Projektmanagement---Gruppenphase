package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseRatingKey;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "exercise_rating")
@IdClass(ExerciseRatingKey.class)
public class ExerciseRating {

    @Id
    @Column(name = "dude_id")
    private Long dudeId;

    @Id
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(nullable = false, name = "rating")
    @Min(1)
    private Integer rating;

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseRating that = (ExerciseRating) o;
        return Objects.equals(getDudeId(), that.getDudeId()) &&
            Objects.equals(getExerciseId(), that.getExerciseId()) &&
            Objects.equals(getRating(), that.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDudeId(), getExerciseId(), getRating());
    }

    @Override
    public String toString() {
        return "ExerciseRating{" +
            "dudeId=" + dudeId +
            ", exerciseId=" + exerciseId +
            ", rating=" + rating +
            '}';
    }
}

