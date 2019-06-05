package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseDoneKey;

import javax.persistence.*;

@Entity
@Table(name = "exercise_done")
@IdClass(ExerciseDoneKey.class)
public class ExerciseDone {

    @Id
    @Column(name = "dude_id")
    private Long dudeId;

    @Id
    @Column(name = "training_schedule_id")
    private Long trainingScheduleId;

    @Id
    @Column(name = "training_schedule_version")
    private Integer trainingScheduleVersion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("dude_id")
    @JoinColumns({
        @JoinColumn(name = "dude_id", referencedColumnName = "dude_id"),
        @JoinColumn(name = "training_schedule_id", referencedColumnName = "training_schedule_id"),
        @JoinColumn(name = "training_schedule_version", referencedColumnName = "training_schedule_version")
    })
    private ActiveTrainingSchedule activeTrainingSchedule;

    @Id
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Id
    @Column(name = "exercise_version")
    private Integer exerciseVersion;

    @Id
    @Column(name = "workout_id")
    private Long workoutId;

    @Id
    @Column(name = "workout_version")
    private Integer workoutVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workout_id")
    @JoinColumns({
        @JoinColumn(name = "workout_id", referencedColumnName = "id"),
        @JoinColumn(name = "workout_version", referencedColumnName = "version")
    })
    private Workout workout;

    @Id
    private Integer day;

    @Column(nullable = false)
    private Boolean done;

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

    public ActiveTrainingSchedule getActiveTrainingSchedule() {
        return activeTrainingSchedule;
    }

    public void setActiveTrainingSchedule(ActiveTrainingSchedule activeTrainingSchedule) {
        this.activeTrainingSchedule = activeTrainingSchedule;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getExerciseVersion() {
        return exerciseVersion;
    }

    public void setExerciseVersion(Integer exerciseVersion) {
        this.exerciseVersion = exerciseVersion;
    }

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

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public static ExerciseDoneBuilder builder() {
        return new ExerciseDoneBuilder();
    }

    @Override
    public String toString() {
        return "ExerciseDone{" +
            "dudeId=" + dudeId +
            ", trainingScheduleId=" + trainingScheduleId +
            ", trainingScheduleVersion=" + trainingScheduleVersion +
            ", exerciseId=" + exerciseId +
            ", exerciseVersion=" + exerciseVersion +
            ", workoutId=" + workoutId +
            ", workoutVersion=" + workoutVersion +
            ", day=" + day +
            ", done=" + done +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseDone that = (ExerciseDone) o;

        if (dudeId != null ? !dudeId.equals(that.dudeId) : that.dudeId != null) return false;
        if (trainingScheduleId != null ? !trainingScheduleId.equals(that.trainingScheduleId) : that.trainingScheduleId != null)
            return false;
        if (trainingScheduleVersion != null ? !trainingScheduleVersion.equals(that.trainingScheduleVersion) : that.trainingScheduleVersion != null)
            return false;
        if (exerciseId != null ? !exerciseId.equals(that.exerciseId) : that.exerciseId != null) return false;
        if (exerciseVersion != null ? !exerciseVersion.equals(that.exerciseVersion) : that.exerciseVersion != null)
            return false;
        if (workoutId != null ? !workoutId.equals(that.workoutId) : that.workoutId != null) return false;
        if (workoutVersion != null ? !workoutVersion.equals(that.workoutVersion) : that.workoutVersion != null)
            return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        return done != null ? done.equals(that.done) : that.done == null;

    }

    @Override
    public int hashCode() {
        int result = dudeId != null ? dudeId.hashCode() : 0;
        result = 31 * result + (trainingScheduleId != null ? trainingScheduleId.hashCode() : 0);
        result = 31 * result + (trainingScheduleVersion != null ? trainingScheduleVersion.hashCode() : 0);
        result = 31 * result + (exerciseId != null ? exerciseId.hashCode() : 0);
        result = 31 * result + (exerciseVersion != null ? exerciseVersion.hashCode() : 0);
        result = 31 * result + (workoutId != null ? workoutId.hashCode() : 0);
        result = 31 * result + (workoutVersion != null ? workoutVersion.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (done != null ? done.hashCode() : 0);
        return result;
    }

    public static final class ExerciseDoneBuilder {
        private Long dudeId;
        private Long trainingScheduleId;
        private Integer trainingScheduleVersion;
        private ActiveTrainingSchedule activeTrainingSchedule;
        private Long exerciseId;
        private Integer exerciseVersion;
        private Long workoutId;
        private Integer workoutVersion;
        private Workout workout;
        private Integer day;
        private Boolean done;

        public ExerciseDoneBuilder() {
        }

        public ExerciseDoneBuilder dudeId(Long dudeId) {
            this.dudeId = dudeId;
            return this;
        }

        public ExerciseDoneBuilder trainingScheduleId(Long trainingScheduleId) {
            this.trainingScheduleId = trainingScheduleId;
            return this;
        }

        public ExerciseDoneBuilder trainingScheduleVersion(Integer trainingScheduleVersion) {
            this.trainingScheduleVersion = trainingScheduleVersion;
            return this;
        }

        public ExerciseDoneBuilder activeTrainingSchedule(ActiveTrainingSchedule activeTrainingSchedule) {
            this.activeTrainingSchedule = activeTrainingSchedule;
            return this;
        }

        public ExerciseDoneBuilder exerciseId(Long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public ExerciseDoneBuilder exerciseVersion(Integer exerciseVersion) {
            this.exerciseVersion = exerciseVersion;
            return this;
        }

        public ExerciseDoneBuilder workoutId(Long workoutId) {
            this.workoutId = workoutId;
            return this;
        }

        public ExerciseDoneBuilder workoutVersion(Integer workoutVersion) {
            this.workoutVersion = workoutVersion;
            return this;
        }

        public ExerciseDoneBuilder workout(Workout workout) {
            this.workout = workout;
            return this;
        }

        public ExerciseDoneBuilder day(Integer day) {
            this.day = day;
            return this;
        }

        public ExerciseDoneBuilder done(Boolean done) {
            this.done = done;
            return this;
        }

        public ExerciseDone build() {
            ExerciseDone exerciseDone = new ExerciseDone();
            exerciseDone.setDudeId(dudeId);
            exerciseDone.setTrainingScheduleId(trainingScheduleId);
            exerciseDone.setTrainingScheduleVersion(trainingScheduleVersion);
            exerciseDone.setActiveTrainingSchedule(activeTrainingSchedule);
            exerciseDone.setExerciseId(exerciseId);
            exerciseDone.setExerciseVersion(exerciseVersion);
            exerciseDone.setWorkoutId(workoutId);
            exerciseDone.setWorkoutVersion(workoutVersion);
            exerciseDone.setWorkout(workout);
            exerciseDone.setDay(day);
            exerciseDone.setDone(done);
            return exerciseDone;
        }
    }
}
