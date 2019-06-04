package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.TrainingScheduleWorkoutKey;

import javax.persistence.*;

@Entity
@Table(name = "training_schedule_workout")
@IdClass(TrainingScheduleWorkoutKey.class)
public class TrainingScheduleWorkout {

    @Id
    @Column(name = "workout_id")
    private Long workoutId;

    @Id
    @Column(name = "workout_version")
    private Integer workoutVersion;

    @Id
    @Column(name = "training_schedule_id")
    private Long trainingScheduleId;

    @Id
    @Column(name = "training_schedule_version")
    private Integer trainingScheduleVersion;

    @Id
    private Integer day;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("workout_id")
    @JoinColumns({
        @JoinColumn(name = "workout_id", referencedColumnName = "id"),
        @JoinColumn(name = "workout_version", referencedColumnName = "version")
    })
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("training_schedule_id")
    @JoinColumns({
        @JoinColumn(name = "training_schedule_id", referencedColumnName = "id"),
        @JoinColumn(name = "training_schedule_version", referencedColumnName = "version")
    })
    private TrainingSchedule trainingSchedule;

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getWorkoutVersion() {
        return workoutVersion;
    }

    public void setWorkoutVersion(Integer workoutVersion) {
        this.workoutVersion = workoutVersion;
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public TrainingSchedule getTrainingSchedule() {
        return trainingSchedule;
    }

    public void setTrainingSchedule(TrainingSchedule trainingSchedule) {
        this.trainingSchedule = trainingSchedule;
    }

    public static TrainingScheduleWorkoutBuilder builder() {
        return new TrainingScheduleWorkoutBuilder();
    }

    @Override
    public String toString() {
        return "TrainingScheduleWorkout{" +
            "workoutId=" + workoutId +
            ", workoutVersion=" + workoutVersion +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", day=" + day +
            ", workout=" + workout +
            ", trainingSchedule=" + trainingSchedule +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingScheduleWorkout that = (TrainingScheduleWorkout) o;

        if (workoutId != null ? !workoutId.equals(that.workoutId) : that.workoutId != null) return false;
        if (workoutVersion != null ? !workoutVersion.equals(that.workoutVersion) : that.workoutVersion != null)
            return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        if (trainingScheduleVersion != null ? !trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion != null)
            return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        if (workout != null ? !workout.equals(that.workout) : that.workout != null) return false;
        return trainingSchedule != null ? trainingSchedule.equals(that.trainingSchedule) : that.trainingSchedule == null;

    }

    @Override
    public int hashCode() {
        int result = workoutId != null ? workoutId.hashCode() : 0;
        result = 31 * result + (workoutVersion != null ? workoutVersion.hashCode() : 0);
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (workout != null ? workout.hashCode() : 0);
        result = 31 * result + (trainingSchedule != null ? trainingSchedule.hashCode() : 0);
        return result;
    }

    public static final class TrainingScheduleWorkoutBuilder {
        private Long workoutId;
        private Integer workoutVersion;
        private Long trainingScheduleId;
        private Integer trainingScheduleVersion;
        private Integer day;
        private Workout workout;
        private TrainingSchedule trainingSchedule;

        public TrainingScheduleWorkoutBuilder() {
        }

        public TrainingScheduleWorkoutBuilder workoutId(Long workoutId) {
            this.workoutId = workoutId;
            return this;
        }

        public TrainingScheduleWorkoutBuilder workoutVersion(Integer workoutVersion) {
            this.workoutVersion = workoutVersion;
            return this;
        }

        public TrainingScheduleWorkoutBuilder trainingScheduleId(Long trainingScheduleId) {
            this.trainingScheduleId = trainingScheduleId;
            return this;
        }

        public TrainingScheduleWorkoutBuilder trainingScheduleVersion(Integer trainingScheduleVersion) {
            this.trainingScheduleVersion = trainingScheduleVersion;
            return this;
        }

        public TrainingScheduleWorkoutBuilder day(Integer day) {
            this.day = day;
            return this;
        }

        public TrainingScheduleWorkoutBuilder workout(Workout workout) {
            this.workout = workout;
            return this;
        }

        public TrainingScheduleWorkoutBuilder trainingSchedule(TrainingSchedule trainingSchedule) {
            this.trainingSchedule = trainingSchedule;
            return this;
        }

        public TrainingScheduleWorkout build() {
            TrainingScheduleWorkout trainingScheduleWorkout = new TrainingScheduleWorkout();
            trainingScheduleWorkout.setWorkoutId(workoutId);
            trainingScheduleWorkout.setWorkoutVersion(workoutVersion);
            trainingScheduleWorkout.setTrainingScheduleId(trainingScheduleId);
            trainingScheduleWorkout.setTrainingScheduleVersion(trainingScheduleVersion);
            trainingScheduleWorkout.setDay(day);
            trainingScheduleWorkout.setWorkout(workout);
            trainingScheduleWorkout.setTrainingSchedule(trainingSchedule);
            return trainingScheduleWorkout;
        }
    }
}
