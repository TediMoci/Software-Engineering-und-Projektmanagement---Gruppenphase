package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutExerciseKey;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "workout_exercise")
public class WorkoutExercise {

    @EmbeddedId
    private WorkoutExerciseKey id;

    @ManyToOne
    @MapsId("workout_id")
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @ManyToOne
    @MapsId("exercise_id")
    @JoinColumns({
        @JoinColumn(name = "exercise_id", referencedColumnName = "id"),
        @JoinColumn(name = "exercise_version", referencedColumnName = "version")
    })
    private Exercise exercise;

    @Column(nullable = false)
    @Min(1)
    private Integer duration = 1;
    // in seconds

    @Column(nullable = false)
    @Min(1)
    private Integer repetitions = 1;

    @Column(nullable = false)
    @Min(1)
    private Integer sets = 1;

    public WorkoutExerciseKey getId() {
        return id;
    }

    public void setId(WorkoutExerciseKey id) {
        this.id = id;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
            "id=" + id +
            ", workout=" + workout +
            ", exercise=" + exercise +
            ", duration=" + duration +
            ", repetitions=" + repetitions +
            ", sets=" + sets +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExercise that = (WorkoutExercise) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (workout != null ? !workout.equals(that.workout) : that.workout != null) return false;
        if (exercise != null ? !exercise.equals(that.exercise) : that.exercise != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (repetitions != null ? !repetitions.equals(that.repetitions) : that.repetitions != null) return false;
        return sets != null ? sets.equals(that.sets) : that.sets == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (workout != null ? workout.hashCode() : 0);
        result = 31 * result + (exercise != null ? exercise.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (repetitions != null ? repetitions.hashCode() : 0);
        result = 31 * result + (sets != null ? sets.hashCode() : 0);
        return result;
    }

    public static final class WorkoutExerciseBuilder {
        private WorkoutExerciseKey id;
        private Workout workout;
        private Exercise exercise;
        private Integer duration;
        private Integer repetitions;
        private Integer sets;

        public WorkoutExerciseBuilder() {
        }

        public WorkoutExerciseBuilder id(WorkoutExerciseKey id) {
            this.id = id;
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

        public WorkoutExerciseBuilder duration(Integer duration) {
            this.duration = duration;
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
            workoutExercise.setId(id);
            workoutExercise.setWorkout(workout);
            workoutExercise.setExercise(exercise);
            workoutExercise.setDuration(duration);
            workoutExercise.setRepetitions(repetitions);
            workoutExercise.setSets(sets);
            return workoutExercise;
        }
    }
}
