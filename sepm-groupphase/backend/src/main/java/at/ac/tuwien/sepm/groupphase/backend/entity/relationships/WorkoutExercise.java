package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutExerciseKey;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "workout_exercise")
@IdClass(WorkoutExerciseKey.class)
public class WorkoutExercise {

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("workout_id")
    @JoinColumns({
        @JoinColumn(name = "workout_id", referencedColumnName = "id"),
        @JoinColumn(name = "workout_version", referencedColumnName = "version")
    })
    private Workout workout;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("exercise_id")
    @JoinColumns({
        @JoinColumn(name = "exercise_id", referencedColumnName = "id"),
        @JoinColumn(name = "exercise_version", referencedColumnName = "version")
    })
    private Exercise exercise;

    @Column(nullable = false, name = "ex_duration")
    @Min(1)
    private Integer exDuration = 1;

    @Column(nullable = false)
    @Min(1)
    private Integer repetitions = 1;

    @Column(nullable = false)
    @Min(1)
    private Integer sets = 1;

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

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getExDuration() {
        return exDuration;
    }

    public void setExDuration(Integer exDuration) {
        this.exDuration = exDuration;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public static WorkoutExerciseBuilder builder() {
        return new WorkoutExerciseBuilder();
    }

    @Override
    public String toString() {
        return "WorkoutExercise{" +
            "exerciseId=" + exerciseId +
            ", exerciseVersion=" + exerciseVersion +
            ", workoutId=" + workoutId +
            ", workoutVersion=" + workoutVersion +
            ", exDuration=" + exDuration +
            ", repetitions=" + repetitions +
            ", sets=" + sets +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExercise that = (WorkoutExercise) o;

        if (exerciseId != null ? !exerciseId.equals(that.exerciseId) : that.exerciseId != null) return false;
        if (exerciseVersion != null ? !exerciseVersion.equals(that.exerciseVersion) : that.exerciseVersion != null)
            return false;
        if (workoutId != null ? !workoutId.equals(that.workoutId) : that.workoutId != null) return false;
        if (workoutVersion != null ? !workoutVersion.equals(that.workoutVersion) : that.workoutVersion != null)
            return false;
        if (exDuration != null ? !exDuration.equals(that.exDuration) : that.exDuration != null) return false;
        if (repetitions != null ? !repetitions.equals(that.repetitions) : that.repetitions != null) return false;
        return sets != null ? sets.equals(that.sets) : that.sets == null;

    }

    @Override
    public int hashCode() {
        int result = exerciseId != null ? exerciseId.hashCode() : 0;
        result = 31 * result + (exerciseVersion != null ? exerciseVersion.hashCode() : 0);
        result = 31 * result + (workoutId != null ? workoutId.hashCode() : 0);
        result = 31 * result + (workoutVersion != null ? workoutVersion.hashCode() : 0);
        result = 31 * result + (exDuration != null ? exDuration.hashCode() : 0);
        result = 31 * result + (repetitions != null ? repetitions.hashCode() : 0);
        result = 31 * result + (sets != null ? sets.hashCode() : 0);
        return result;
    }

    public static final class WorkoutExerciseBuilder {
        private Long exerciseId;
        private Integer exerciseVersion;
        private Long workoutId;
        private Integer workoutVersion;
        private Workout workout;
        private Exercise exercise;
        private Integer exDuration;
        private Integer repetitions;
        private Integer sets;

        public WorkoutExerciseBuilder() {
        }

        public WorkoutExerciseBuilder exerciseId(Long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public WorkoutExerciseBuilder exerciseVersion(Integer exerciseVersion) {
            this.exerciseVersion = exerciseVersion;
            return this;
        }

        public WorkoutExerciseBuilder workoutId(Long workoutId) {
            this.workoutId = workoutId;
            return this;
        }

        public WorkoutExerciseBuilder workoutVersion(Integer workoutVersion) {
            this.workoutVersion = workoutVersion;
            return this;
        }

        public WorkoutExerciseBuilder workout(Workout workout) {
            this.workout = workout;
            return this;
        }

        public WorkoutExerciseBuilder exercise(Exercise exercise) {
            this.exercise = exercise;
            return this;
        }

        public WorkoutExerciseBuilder exDuration(Integer duration) {
            this.exDuration = duration;
            return this;
        }

        public WorkoutExerciseBuilder repetitions(Integer repetitions) {
            this.repetitions = repetitions;
            return this;
        }

        public WorkoutExerciseBuilder sets(Integer sets) {
            this.sets = sets;
            return this;
        }

        public WorkoutExercise build() {
            WorkoutExercise workoutExercise = new WorkoutExercise();
            workoutExercise.setExerciseId(exerciseId);
            workoutExercise.setExerciseVersion(exerciseVersion);
            workoutExercise.setWorkoutId(workoutId);
            workoutExercise.setWorkoutVersion(workoutVersion);
            workoutExercise.setWorkout(workout);
            workoutExercise.setExercise(exercise);
            workoutExercise.setExDuration(exDuration);
            workoutExercise.setRepetitions(repetitions);
            workoutExercise.setSets(sets);
            return workoutExercise;
        }
    }
}
