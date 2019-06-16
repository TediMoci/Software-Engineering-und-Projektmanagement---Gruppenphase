package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutRatingKey;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "workout_rating")
@IdClass(WorkoutRatingKey.class)
public class WorkoutRating {

    @Id
    @Column(name = "dude_id")
    private Long dudeId;

    @Id
    @Column(name = "workout_id")
    private Long workoutId;

    @Column(nullable = false, name = "rating")
    @Min(1)
    private Integer rating;

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "WorkoutRating{" +
            "dudeId=" + dudeId +
            ", workoutId=" + workoutId +
            ", rating=" + rating +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutRating that = (WorkoutRating) o;
        return Objects.equals(getDudeId(), that.getDudeId()) &&
            Objects.equals(getWorkoutId(), that.getWorkoutId()) &&
            Objects.equals(getRating(), that.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDudeId(), getWorkoutId(), getRating());
    }
}
