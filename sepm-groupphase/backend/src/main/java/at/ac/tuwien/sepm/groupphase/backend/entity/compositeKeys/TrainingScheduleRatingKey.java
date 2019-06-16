package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import java.io.Serializable;
import java.util.Objects;

public class TrainingScheduleRatingKey implements Serializable {

    private Long dudeId;
    private Long trainingScheduleId;

    public TrainingScheduleRatingKey() {
    }

    public TrainingScheduleRatingKey(Long dudeId, Long trainingScheduleId) {
        this.dudeId = dudeId;
        this.trainingScheduleId = trainingScheduleId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingScheduleRatingKey that = (TrainingScheduleRatingKey) o;
        return Objects.equals(getDudeId(), that.getDudeId()) &&
            Objects.equals(getTrainingScheduleId(), that.getTrainingScheduleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDudeId(), getTrainingScheduleId());
    }

    @Override
    public String toString() {
        return "TrainingScheduleRatingKey{" +
            "dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            '}';
    }
}
