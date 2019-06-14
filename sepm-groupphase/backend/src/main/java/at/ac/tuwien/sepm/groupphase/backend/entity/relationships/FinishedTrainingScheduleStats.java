package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import javax.persistence.*;

@Entity
@Table(name = "finished_training_schedule_stats")
public class FinishedTrainingScheduleStats {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_finished_id")
    @SequenceGenerator(name = "seq_finished_id", sequenceName = "seq_finished_id")
    private Long id;

    @Column(name = "dude_id")
    private Long dudeId;

    @Column(name = "training_schedule_id")
    private Long trainingScheduleId;

    @Column(name = "training_schedule_version")
    private Integer trainingScheduleVersion;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("dude_id")
    @JoinColumn(name = "dude_id", referencedColumnName = "id")
    private Dude dude;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("training_schedule_id")
    @JoinColumns({
        @JoinColumn(name = "training_schedule_id", referencedColumnName = "id"),
        @JoinColumn(name = "training_schedule_version", referencedColumnName = "version")
    })
    private TrainingSchedule trainingSchedule;

    @Column(nullable = false, name = "total_hours")
    private Double totalHours;

    @Column(nullable = false, name = "total_days")
    private Integer totalDays;

    @Column(nullable = false, name = "total_calorie_consumption")
    private Double totalCalorieConsumption;

    @Column(nullable = false, name = "total_interval_repetitions")
    private Integer totalIntervalRepetitions;

    @Column(nullable = false, name = "strength_percent")
    private Double strengthPercent;

    @Column(nullable = false, name = "endurance_percent")
    private Double endurancePercent;

    @Column(nullable = false, name = "other_percent")
    private Double otherPercent;

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

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Double getTotalCalorieConsumption() {
        return totalCalorieConsumption;
    }

    public void setTotalCalorieConsumption(Double totalCalorieConsumption) {
        this.totalCalorieConsumption = totalCalorieConsumption;
    }

    public Integer getTotalIntervalRepetitions() {
        return totalIntervalRepetitions;
    }

    public void setTotalIntervalRepetitions(Integer totalIntervalRepetitions) {
        this.totalIntervalRepetitions = totalIntervalRepetitions;
    }

    public Double getStrengthPercent() {
        return strengthPercent;
    }

    public void setStrengthPercent(Double strengthPercent) {
        this.strengthPercent = strengthPercent;
    }

    public Double getEndurancePercent() {
        return endurancePercent;
    }

    public void setEndurancePercent(Double endurancePercent) {
        this.endurancePercent = endurancePercent;
    }

    public Double getOtherPercent() {
        return otherPercent;
    }

    public void setOtherPercent(Double otherPercent) {
        this.otherPercent = otherPercent;
    }

    public static FinishedTrainingScheduleStatsBuilder builder() {
        return new FinishedTrainingScheduleStatsBuilder();
    }

    @Override
    public String toString() {
        return "FinishedTrainingScheduleStats{" +
            "id=" + id +
            ", dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", totalHours=" + totalHours +
            ", totalDays=" + totalDays +
            ", totalCalorieConsumption=" + totalCalorieConsumption +
            ", totalIntervalRepetitions=" + totalIntervalRepetitions +
            ", strengthPercent=" + strengthPercent +
            ", endurancePercent=" + endurancePercent +
            ", otherPercent=" + otherPercent +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinishedTrainingScheduleStats that = (FinishedTrainingScheduleStats) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dudeId != null ? !dudeId.equals(that.dudeId) : that.dudeId != null) return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        if (trainingScheduleVersion != null ? !trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion != null)
            return false;
        if (totalHours != null ? !totalHours.equals(that.totalHours) : that.totalHours != null) return false;
        if (totalDays != null ? !totalDays.equals(that.totalDays) : that.totalDays != null) return false;
        if (totalCalorieConsumption != null ? !totalCalorieConsumption.equals(that.totalCalorieConsumption) : that.totalCalorieConsumption != null)
            return false;
        if (totalIntervalRepetitions != null ? !totalIntervalRepetitions.equals(that.totalIntervalRepetitions) : that.totalIntervalRepetitions != null)
            return false;
        if (strengthPercent != null ? !strengthPercent.equals(that.strengthPercent) : that.strengthPercent != null)
            return false;
        if (endurancePercent != null ? !endurancePercent.equals(that.endurancePercent) : that.endurancePercent != null)
            return false;
        return otherPercent != null ? otherPercent.equals(that.otherPercent) : that.otherPercent == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dudeId != null ? dudeId.hashCode() : 0);
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        result = 31 * result + (totalHours != null ? totalHours.hashCode() : 0);
        result = 31 * result + (totalDays != null ? totalDays.hashCode() : 0);
        result = 31 * result + (totalCalorieConsumption != null ? totalCalorieConsumption.hashCode() : 0);
        result = 31 * result + (totalIntervalRepetitions != null ? totalIntervalRepetitions.hashCode() : 0);
        result = 31 * result + (strengthPercent != null ? strengthPercent.hashCode() : 0);
        result = 31 * result + (endurancePercent != null ? endurancePercent.hashCode() : 0);
        result = 31 * result + (otherPercent != null ? otherPercent.hashCode() : 0);
        return result;
    }

    public static final class FinishedTrainingScheduleStatsBuilder {
        private Long id;
        private Long dudeId;
        private Long trainingScheduleId;
        private Integer trainingScheduleVersion;
        private Dude dude;
        private TrainingSchedule trainingSchedule;
        private Double totalHours;
        private Integer totalDays;
        private Double totalCalorieConsumption;
        private Integer totalIntervalRepetitions;
        private Double strengthPercent;
        private Double endurancePercent;
        private Double otherPercent;

        public FinishedTrainingScheduleStatsBuilder() {
        }

        public FinishedTrainingScheduleStatsBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder dudeId(Long dudeId) {
            this.dudeId = dudeId;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder trainingScheduleId(Long trainingScheduleId) {
            this.trainingScheduleId = trainingScheduleId;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder trainingScheduleVersion(Integer trainingScheduleVersion) {
            this.trainingScheduleVersion = trainingScheduleVersion;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder dude(Dude dude) {
            this.dude = dude;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder trainingSchedule(TrainingSchedule trainingSchedule) {
            this.trainingSchedule = trainingSchedule;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder totalHours(Double totalHours) {
            this.totalHours = totalHours;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder totalDays(Integer totalDays) {
            this.totalDays = totalDays;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder totalCalorieConsumption(Double totalCalorieConsumption) {
            this.totalCalorieConsumption = totalCalorieConsumption;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder totalIntervalRepetitions(Integer totalIntervalRepetitions) {
            this.totalIntervalRepetitions = totalIntervalRepetitions;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder strengthPercent(Double strengthPercent) {
            this.strengthPercent = strengthPercent;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder endurancePercent(Double endurancePercent) {
            this.endurancePercent = endurancePercent;
            return this;
        }

        public FinishedTrainingScheduleStatsBuilder otherPercent(Double otherPercent) {
            this.otherPercent = otherPercent;
            return this;
        }

        public FinishedTrainingScheduleStats build() {
            FinishedTrainingScheduleStats finishedTrainingScheduleStats = new FinishedTrainingScheduleStats();
            finishedTrainingScheduleStats.setId(id);
            finishedTrainingScheduleStats.setDudeId(dudeId);
            finishedTrainingScheduleStats.setTrainingScheduleId(trainingScheduleId);
            finishedTrainingScheduleStats.setTrainingScheduleVersion(trainingScheduleVersion);
            finishedTrainingScheduleStats.setDude(dude);
            finishedTrainingScheduleStats.setTrainingSchedule(trainingSchedule);
            finishedTrainingScheduleStats.setTotalHours(totalHours);
            finishedTrainingScheduleStats.setTotalDays(totalDays);
            finishedTrainingScheduleStats.setTotalCalorieConsumption(totalCalorieConsumption);
            finishedTrainingScheduleStats.setTotalIntervalRepetitions(totalIntervalRepetitions);
            finishedTrainingScheduleStats.setStrengthPercent(strengthPercent);
            finishedTrainingScheduleStats.setEndurancePercent(endurancePercent);
            finishedTrainingScheduleStats.setOtherPercent(otherPercent);
            return finishedTrainingScheduleStats;
        }
    }
}
