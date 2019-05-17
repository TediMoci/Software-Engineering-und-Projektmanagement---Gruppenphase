package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "WorkoutExerciseDto", description = "A dto for workout-exercise-relationships entries via rest")
public class WorkoutExerciseDto {

    @ApiModelProperty(required = true, name = "Workout of Workout-Exercise-Relationship")
    private WorkoutDto workoutDto;

    @ApiModelProperty(required = true, name = "Exercise of Workout-Exercise-Relationship")
    private ExerciseDto exerciseDto;

    @ApiModelProperty(required = true, name = "Duration of Workout-Exercise-Relationship")
    private Integer duration = 1;
    // in seconds

    @ApiModelProperty(required = true, name = "Repetitions of Workout-Exercise-Relationship")
    private Integer repetitions = 1;

    @ApiModelProperty(required = true, name = "Sets of Workout-Exercise-Relationship")
    private Integer sets = 1;

    public WorkoutDto getWorkoutDto() {
        return workoutDto;
    }

    public void setWorkoutDto(WorkoutDto workoutDto) {
        this.workoutDto = workoutDto;
    }

    public ExerciseDto getExerciseDto() {
        return exerciseDto;
    }

    public void setExerciseDto(ExerciseDto exerciseDto) {
        this.exerciseDto = exerciseDto;
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

    public static WorkoutExerciseDtoBuilder builder() {
        return new WorkoutExerciseDtoBuilder();
    }

    @Override
    public String toString() {
        return "WorkoutExerciseDto{" +
            "workoutDto=" + workoutDto +
            ", exerciseDto=" + exerciseDto +
            ", duration=" + duration +
            ", repetitions=" + repetitions +
            ", sets=" + sets +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExerciseDto that = (WorkoutExerciseDto) o;

        if (workoutDto != null ? !workoutDto.equals(that.workoutDto) : that.workoutDto != null) return false;
        if (exerciseDto != null ? !exerciseDto.equals(that.exerciseDto) : that.exerciseDto != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (repetitions != null ? !repetitions.equals(that.repetitions) : that.repetitions != null) return false;
        return sets != null ? sets.equals(that.sets) : that.sets == null;

    }

    @Override
    public int hashCode() {
        int result = workoutDto != null ? workoutDto.hashCode() : 0;
        result = 31 * result + (exerciseDto != null ? exerciseDto.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (repetitions != null ? repetitions.hashCode() : 0);
        result = 31 * result + (sets != null ? sets.hashCode() : 0);
        return result;
    }

    public static final class WorkoutExerciseDtoBuilder {
        private WorkoutDto workoutDto;
        private ExerciseDto exerciseDto;
        private Integer duration;
        private Integer repetitions;
        private Integer sets;

        public WorkoutExerciseDtoBuilder() {
        }

        public WorkoutExerciseDtoBuilder workoutDto(WorkoutDto workoutDto) {
            this.workoutDto = workoutDto;
            return this;
        }

        public WorkoutExerciseDtoBuilder exerciseDto(ExerciseDto exerciseDto) {
            this.exerciseDto = exerciseDto;
            return this;
        }

        public WorkoutExerciseDtoBuilder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public WorkoutExerciseDtoBuilder repetitions(Integer repetitions) {
            this.repetitions = repetitions;
            return this;
        }

        public WorkoutExerciseDtoBuilder sets(Integer sets) {
            this.sets = sets;
            return this;
        }

        public WorkoutExerciseDto build() {
            WorkoutExerciseDto workoutExerciseDto = new WorkoutExerciseDto();
            workoutExerciseDto.setWorkoutDto(workoutDto);
            workoutExerciseDto.setExerciseDto(exerciseDto);
            workoutExerciseDto.setDuration(duration);
            workoutExerciseDto.setRepetitions(repetitions);
            workoutExerciseDto.setSets(sets);
            return workoutExerciseDto;
        }
    }
}
