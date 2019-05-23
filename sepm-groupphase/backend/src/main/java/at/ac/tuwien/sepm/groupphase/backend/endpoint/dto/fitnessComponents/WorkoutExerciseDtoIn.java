package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

@ApiModel(value = "WorkoutExerciseDtoIn", description = "A dto for workout-exercise-relationships entries via rest")
public class WorkoutExerciseDtoIn {

    @ApiModelProperty(required = true, name = "Id of Exercise of Workout-Exercise-Relationship")
    private Long exerciseId;

    @ApiModelProperty(required = true, name = "Version of Exercise of Workout-Exercise-Relationship")
    private Integer exerciseVersion;

    @ApiModelProperty(required = true, name = "Duration of Workout-Exercise-Relationship")
    @Min(value = 1, message = "Min for exDuration is 1")
    private Integer exDuration = 1;
    // in seconds

    @ApiModelProperty(required = true, name = "Repetitions of Workout-Exercise-Relationship")
    @Min(value = 1, message = "Min for repetitions is 1")
    private Integer repetitions = 1;

    @ApiModelProperty(required = true, name = "Sets of Workout-Exercise-Relationship")
    @Min(value = 1, message = "Min for sets is 1")
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

    public static WorkoutExerciseDtoInBuilder builder() {
        return new WorkoutExerciseDtoInBuilder();
    }

    @Override
    public String toString() {
        return "WorkoutExerciseDtoIn{" +
            "exerciseId=" + exerciseId +
            ", exerciseVersion=" + exerciseVersion +
            ", exDuration=" + exDuration +
            ", repetitions=" + repetitions +
            ", sets=" + sets +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExerciseDtoIn that = (WorkoutExerciseDtoIn) o;

        if (exerciseId != null ? !exerciseId.equals(that.exerciseId) : that.exerciseId != null) return false;
        if (exerciseVersion != null ? !exerciseVersion.equals(that.exerciseVersion) : that.exerciseVersion != null)
            return false;
        if (exDuration != null ? !exDuration.equals(that.exDuration) : that.exDuration != null) return false;
        if (repetitions != null ? !repetitions.equals(that.repetitions) : that.repetitions != null) return false;
        return sets != null ? sets.equals(that.sets) : that.sets == null;

    }

    @Override
    public int hashCode() {
        int result = exerciseId != null ? exerciseId.hashCode() : 0;
        result = 31 * result + (exerciseVersion != null ? exerciseVersion.hashCode() : 0);
        result = 31 * result + (exDuration != null ? exDuration.hashCode() : 0);
        result = 31 * result + (repetitions != null ? repetitions.hashCode() : 0);
        result = 31 * result + (sets != null ? sets.hashCode() : 0);
        return result;
    }

    public static final class WorkoutExerciseDtoInBuilder {
        private Long exerciseId;
        private Integer exerciseVersion;
        private Integer exDuration;
        private Integer repetitions;
        private Integer sets;

        public WorkoutExerciseDtoInBuilder() {
        }

        public WorkoutExerciseDtoInBuilder exerciseId(Long exerciseId) {
            this.exerciseId = exerciseId;
            return this;
        }

        public WorkoutExerciseDtoInBuilder exerciseVersion(Integer exerciseVersion) {
            this.exerciseVersion = exerciseVersion;
            return this;
        }

        public WorkoutExerciseDtoInBuilder exDuration(Integer duration) {
            this.exDuration = duration;
            return this;
        }

        public WorkoutExerciseDtoInBuilder repetitions(Integer repetitions) {
            this.repetitions = repetitions;
            return this;
        }

        public WorkoutExerciseDtoInBuilder sets(Integer sets) {
            this.sets = sets;
            return this;
        }

        public WorkoutExerciseDtoIn build() {
            WorkoutExerciseDtoIn workoutExerciseDtoIn = new WorkoutExerciseDtoIn();
            workoutExerciseDtoIn.setExerciseId(exerciseId);
            workoutExerciseDtoIn.setExerciseVersion(exerciseVersion);
            workoutExerciseDtoIn.setExDuration(exDuration);
            workoutExerciseDtoIn.setRepetitions(repetitions);
            workoutExerciseDtoIn.setSets(sets);
            return workoutExerciseDtoIn;
        }
    }
}
