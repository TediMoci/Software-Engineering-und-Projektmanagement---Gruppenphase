package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ActiveTrainingScheduleKey;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "active_training_schedule")
@IdClass(ActiveTrainingScheduleKey.class)
public class ActiveTrainingSchedule {

    @Id
    @Column(name = "dude_id")
    private Long dudeId;

    @Id
    @Column(name = "training_schedule_id")
    private Long trainingScheduleId;

    @Id
    @Column(name = "training_schedule_version")
    private Integer trainingScheduleVersion;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "interval_repetitions")
    private Integer intervalRepetitions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "activeTrainingSchedule")
    private List<ExerciseDone> done;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("dude_id")
    @JoinColumn(name = "dude_id", referencedColumnName = "id")
    private Dude dude;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("training_schedule_id")
    @JoinColumns({
        @JoinColumn(name = "training_schedule_id", referencedColumnName = "id"),
        @JoinColumn(name = "training_schedule_version", referencedColumnName = "version")
    })
    private TrainingSchedule trainingSchedule;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getIntervalRepetitions() {
        return intervalRepetitions;
    }

    public void setIntervalRepetitions(Integer intervalRepetitions) {
        this.intervalRepetitions = intervalRepetitions;
    }

    public List<ExerciseDone> getDone() {
        return done;
    }

    public void setDone(List<ExerciseDone> done) {
        this.done = done;
    }

    public Dude getDude() {
        return dude;
    }

    public void setDude(Dude dude) {
        this.dude = dude;
    }

    public TrainingSchedule getTrainingSchedule() {
        return trainingSchedule;
    }

    public void setTrainingSchedule(TrainingSchedule trainingSchedule) {
        this.trainingSchedule = trainingSchedule;
    }

    public static ActiveTrainingScheduleBuilder builder() {
        return new ActiveTrainingScheduleBuilder();
    }

    @Override
    public String toString() {
        return "ActiveTrainingSchedule{" +
            "dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", startDate=" + startDate +
            ", intervalRepetitions=" + intervalRepetitions +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiveTrainingSchedule that = (ActiveTrainingSchedule) o;

        if (dudeId != null ? !dudeId.equals(that.dudeId) : that.dudeId != null) return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        if (trainingScheduleVersion != null ? !trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        return intervalRepetitions != null ? intervalRepetitions.equals(that.intervalRepetitions) : that.intervalRepetitions == null;

    }

    @Override
    public int hashCode() {
        int result = dudeId != null ? dudeId.hashCode() : 0;
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (intervalRepetitions != null ? intervalRepetitions.hashCode() : 0);
        return result;
    }

    public static final class ActiveTrainingScheduleBuilder {
        private Long dudeId;
        private Long trainingScheduleId;
        private Integer trainingScheduleVersion;
        private LocalDate startDate;
        private Integer intervalRepetitions;
        private List<ExerciseDone> done;
        private Dude dude;
        private TrainingSchedule trainingSchedule;

        public ActiveTrainingScheduleBuilder() {
        }

        public ActiveTrainingScheduleBuilder dudeId(Long dudeId) {
            this.dudeId = dudeId;
            return this;
        }

        public ActiveTrainingScheduleBuilder trainingScheduleId(Long trainingScheduleId) {
            this.trainingScheduleId = trainingScheduleId;
            return this;
        }

        public ActiveTrainingScheduleBuilder trainingScheduleVersion(Integer trainingScheduleVersion) {
            this.trainingScheduleVersion = trainingScheduleVersion;
            return this;
        }

        public ActiveTrainingScheduleBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ActiveTrainingScheduleBuilder intervalRepetitions(Integer intervalRepetitions) {
            this.intervalRepetitions = intervalRepetitions;
            return this;
        }

        public ActiveTrainingScheduleBuilder done(List<ExerciseDone> done) {
            this.done = done;
            return this;
        }

        public ActiveTrainingScheduleBuilder dude(Dude dude) {
            this.dude = dude;
            return this;
        }

        public ActiveTrainingScheduleBuilder trainingSchedule(TrainingSchedule trainingSchedule) {
            this.trainingSchedule = trainingSchedule;
            return this;
        }

        public ActiveTrainingSchedule build() {
            ActiveTrainingSchedule activeTrainingSchedule = new ActiveTrainingSchedule();
            activeTrainingSchedule.setDudeId(dudeId);
            activeTrainingSchedule.setTrainingScheduleId(trainingScheduleId);
            activeTrainingSchedule.setTrainingScheduleVersion(trainingScheduleVersion);
            activeTrainingSchedule.setStartDate(startDate);
            activeTrainingSchedule.setIntervalRepetitions(intervalRepetitions);
            activeTrainingSchedule.setDone(done);
            activeTrainingSchedule.setDude(dude);
            activeTrainingSchedule.setTrainingSchedule(trainingSchedule);
            return activeTrainingSchedule;
        }
    }
}