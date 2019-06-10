package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import java.io.Serializable;

public class ActiveTrainingScheduleKey implements Serializable {

    private Long dudeId;
    private Long trainingScheduleId;
    private Integer trainingScheduleVersion;

    public ActiveTrainingScheduleKey() {
    }

    public ActiveTrainingScheduleKey(Long dudeId, Long trainingScheduleId, Integer trainingScheduleVersion) {
        this.dudeId = dudeId;
        this.trainingScheduleId = trainingScheduleId;
        this.trainingScheduleVersion = trainingScheduleVersion;
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

    public Integer getTrainingScheduleVersion() {
        return trainingScheduleVersion;
    }

    public void setTrainingScheduleVersion(Integer trainingScheduleVersion) {
        this.trainingScheduleVersion = trainingScheduleVersion;
    }

    @Override
    public String toString() {
        return "ActiveTrainingScheduleKey{" +
            "dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiveTrainingScheduleKey that = (ActiveTrainingScheduleKey) o;

        if (dudeId != null ? !dudeId.equals(that.dudeId) : that.dudeId != null) return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        return trainingScheduleVersion != null ? trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion == null;

    }

    @Override
    public int hashCode() {
        int result = dudeId != null ? dudeId.hashCode() : 0;
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        return result;
    }
}
