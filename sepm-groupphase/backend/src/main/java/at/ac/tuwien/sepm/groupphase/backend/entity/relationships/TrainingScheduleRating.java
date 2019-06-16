package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.TrainingScheduleRatingKey;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "training_schedule_rating")
@IdClass(TrainingScheduleRatingKey.class)
public class TrainingScheduleRating {

    @Id
    @Column(name = "dude_id")
    private Long dudeId;

    @Id
    @Column(name = "training_schedule_id")
    private Long trainingScheduleId;

    @Column(nullable = false, name = "rating")
    @Min(1)
    private Integer rating;

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "TrainingScheduleRating{" +
            "dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", rating=" + rating +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingScheduleRating that = (TrainingScheduleRating) o;
        return Objects.equals(getDudeId(), that.getDudeId()) &&
            Objects.equals(getTrainingScheduleId(), that.getTrainingScheduleId()) &&
            Objects.equals(getRating(), that.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDudeId(), getTrainingScheduleId(), getRating());
    }
}
