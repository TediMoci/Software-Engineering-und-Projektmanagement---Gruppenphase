package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.util.Arrays;

@ApiModel(value = "WorkoutDto", description = "A dto for workout entries via rest")
public class WorkoutDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(name = "Version of Workout")
    @Min(value = 1, message = "Min version value is 1")
    private Integer version = 1;

    @ApiModelProperty(required = true, name = "Name of Workout")
    @NotBlank(message = "Name must not be empty")
    @Size(min = 1, max = 100, message = "Name length must be between 1 and 100")
    private String name;

    @ApiModelProperty(name = "Description of Workout")
    @Size(max = 3000, message = "Max description length is 3000")
    private String description = "No description given.";

    @ApiModelProperty(required = true, name = "Difficulty of Workout")
    @NotNull(message = "Difficulty must be given")
    @Min(value = 1, message = "Min difficulty value is 1") @Max(value = 3, message = "Max difficulty value is 3")
    private Integer difficulty;

    @ApiModelProperty(name = "Calorie consumption of Workout")
    @Min(value = 0, message = "Min for calorieConsumption is 0") @Max(value = 10000, message = "Max value for calorieConsumption is 10000")
    private Double calorieConsumption = 0.0;

    @ApiModelProperty(name = "Rating of Workout")
    @Min(0) @Max(5)
    private Double rating = 0.0;

    @ApiModelProperty(name = "Visibility of Workout")
    private Boolean isPrivate = false;

    @ApiModelProperty(name = "Workout-Exercises that are part of the Workout")
    private WorkoutExerciseDtoIn[] workoutExercises;

    @ApiModelProperty(required = true, name = "ID of creator of Workout")
    private Long creatorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Double getCalorieConsumption() {
        return calorieConsumption;
    }

    public void setCalorieConsumption(Double calorieConsumption) {
        this.calorieConsumption = calorieConsumption;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public WorkoutExerciseDtoIn[] getWorkoutExercises() {
        return workoutExercises;
    }

    public void setWorkoutExercises(WorkoutExerciseDtoIn[] workoutExercises) {
        this.workoutExercises = workoutExercises;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public static WorkoutDtoBuilder builder() {
        return new WorkoutDtoBuilder();
    }

    @Override
    public String toString() {
        return "WorkoutDto{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", difficulty=" + difficulty +
            ", calorieConsumption=" + calorieConsumption +
            ", rating=" + rating +
            ", workoutExercises=" + Arrays.toString(workoutExercises) +
            ", creatorId=" + creatorId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutDto that = (WorkoutDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (difficulty != null ? !difficulty.equals(that.difficulty) : that.difficulty != null) return false;
        if (calorieConsumption != null ? !calorieConsumption.equals(that.calorieConsumption) : that.calorieConsumption != null)
            return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(workoutExercises, that.workoutExercises)) return false;
        return creatorId != null ? creatorId.equals(that.creatorId) : that.creatorId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (calorieConsumption != null ? calorieConsumption.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(workoutExercises);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        return result;
    }

    public static final class WorkoutDtoBuilder {
        private Long id;
        private Integer version;
        private String name;
        private String description;
        private Integer difficulty;
        private Double calorieConsumption;
        private Double rating;
        private Boolean isPrivate;
        private WorkoutExerciseDtoIn[] workoutExercises;
        private Long creatorId;

        public WorkoutDtoBuilder() {
        }

        public WorkoutDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public WorkoutDtoBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public WorkoutDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WorkoutDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public WorkoutDtoBuilder difficulty(Integer difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public WorkoutDtoBuilder calorieConsumption(Double calorieConsumption) {
            this.calorieConsumption = calorieConsumption;
            return this;
        }

        public WorkoutDtoBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public WorkoutDtoBuilder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public WorkoutDtoBuilder workoutExercises(WorkoutExerciseDtoIn[] workoutExercises) {
            this.workoutExercises = workoutExercises;
            return this;
        }

        public WorkoutDtoBuilder creatorId(Long creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public WorkoutDto build() {
            WorkoutDto workoutDto = new WorkoutDto();
            workoutDto.setId(id);
            workoutDto.setVersion(version);
            workoutDto.setName(name);
            workoutDto.setDescription(description);
            workoutDto.setDifficulty(difficulty);
            workoutDto.setCalorieConsumption(calorieConsumption);
            workoutDto.setRating(rating);
            workoutDto.setIsPrivate(isPrivate);
            workoutDto.setWorkoutExercises(workoutExercises);
            workoutDto.setCreatorId(creatorId);
            return workoutDto;
        }
    }

}
