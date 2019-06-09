package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "active_training_schedule")
public class ActiveTrainingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_active_id")
    @SequenceGenerator(name = "seq_active_id", sequenceName = "seq_active_id")
    private Long id;

    @Column(name = "dude_id")
    private Long dudeId;

    @Column(name = "training_schedule_id")
    private Long trainingScheduleId;

    @Column(name = "training_schedule_version")
    private Integer trainingScheduleVersion;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "interval_repetitions")
    private Integer intervalRepetitions;

    @Column(nullable = false, name = "is_adaptive")
    private Boolean isAdaptive;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getAdaptive() {
        return isAdaptive;
    }

    public void setAdaptive(Boolean adaptive) {
        isAdaptive = adaptive;
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
            "id=" + id +
            ", dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", startDate=" + startDate +
            ", intervalRepetitions=" + intervalRepetitions +
            ", isAdaptive=" + isAdaptive +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiveTrainingSchedule that = (ActiveTrainingSchedule) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dudeId != null ? !dudeId.equals(that.dudeId) : that.dudeId != null) return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        if (trainingScheduleVersion != null ? !trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (intervalRepetitions != null ? !intervalRepetitions.equals(that.intervalRepetitions) : that.intervalRepetitions != null)
            return false;
        return isAdaptive != null ? isAdaptive.equals(that.isAdaptive) : that.isAdaptive == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dudeId != null ? dudeId.hashCode() : 0);
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (intervalRepetitions != null ? intervalRepetitions.hashCode() : 0);
        result = 31 * result + (isAdaptive != null ? isAdaptive.hashCode() : 0);
        return result;
    }

    public static final class ActiveTrainingScheduleBuilder {
        private Long id;
        private Long dudeId;
        private Long trainingScheduleId;
        private Integer trainingScheduleVersion;
        private LocalDate startDate;
        private Integer intervalRepetitions;
        private Boolean isAdaptive;
        private List<ExerciseDone> done;
        private Dude dude;
        private TrainingSchedule trainingSchedule;

        public ActiveTrainingScheduleBuilder() {
        }

        public ActiveTrainingScheduleBuilder id(Long id) {
            this.id = id;
            return this;
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

        public ActiveTrainingScheduleBuilder isAdaptive(Boolean isAdaptive) {
            this.isAdaptive = isAdaptive;
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
            activeTrainingSchedule.setId(id);
            activeTrainingSchedule.setDudeId(dudeId);
            activeTrainingSchedule.setTrainingScheduleId(trainingScheduleId);
            activeTrainingSchedule.setTrainingScheduleVersion(trainingScheduleVersion);
            activeTrainingSchedule.setStartDate(startDate);
            activeTrainingSchedule.setIntervalRepetitions(intervalRepetitions);
            activeTrainingSchedule.setAdaptive(isAdaptive);
            activeTrainingSchedule.setDone(done);
            activeTrainingSchedule.setDude(dude);
            activeTrainingSchedule.setTrainingSchedule(trainingSchedule);
            return activeTrainingSchedule;
        }
    }
}
