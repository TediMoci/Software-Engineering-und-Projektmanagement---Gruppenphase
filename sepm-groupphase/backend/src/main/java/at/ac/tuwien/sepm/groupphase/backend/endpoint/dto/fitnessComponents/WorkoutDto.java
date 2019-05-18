package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

@ApiModel(value = "WorkoutDto", description = "A dto for workout entries via rest")
public class WorkoutDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(required = true, name = "Name of Workout")
    private String name;

    @ApiModelProperty(name = "Description of Workout")
    private String description = "No description given.";

    @ApiModelProperty(required = true, name = "Difficulty of Workout")
    private Integer difficulty;
    // TODO: selfAssessment enum

    @ApiModelProperty(name = "Calorie consumption of Workout")
    private Double calorieConsumption = 0.0;

    @ApiModelProperty(name = "Rating of Workout")
    private Double rating = 1.0;

    @ApiModelProperty(name = "Version of Workout")
    private Integer version = 1;

    @ApiModelProperty(name = "Workout-Exercise relationships that the Workout is part of")
    private Set<WorkoutExercise> exercises;

    @ApiModelProperty(required = true, name = "Creator of Workout")
    private Dude creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<WorkoutExercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }

    public Dude getCreator() {
        return creator;
    }

    public void setCreator(Dude creator) {
        this.creator = creator;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public static WorkoutDtoBuilder builder() {
        return new WorkoutDtoBuilder();
    }

    @Override
    public String toString() {
        return "WorkoutDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", difficulty=" + difficulty +
            ", calorieConsumption=" + calorieConsumption +
            ", rating=" + rating +
            ", version=" + version +
            ", exercises=" + exercises +
            ", creator=" + creator +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutDto that = (WorkoutDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (difficulty != null ? !difficulty.equals(that.difficulty) : that.difficulty != null) return false;
        if (calorieConsumption != null ? !calorieConsumption.equals(that.calorieConsumption) : that.calorieConsumption != null)
            return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (exercises != null ? !exercises.equals(that.exercises) : that.exercises != null) return false;
        return creator != null ? creator.equals(that.creator) : that.creator == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (calorieConsumption != null ? calorieConsumption.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (exercises != null ? exercises.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        return result;
    }

    public static final class WorkoutDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private Integer difficulty;
        private Double calorieConsumption;
        private Double rating;
        private Integer version;
        private Set<WorkoutExercise> exercises;
        private Dude creator;

        public WorkoutDtoBuilder() {
        }

        public WorkoutDtoBuilder id(Long id) {
            this.id = id;
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

        public WorkoutDtoBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public WorkoutDtoBuilder exercises(Set<WorkoutExercise> exercises) {
            this.exercises = exercises;
            return this;
        }

        public WorkoutDtoBuilder creator(Dude creator) {
            this.creator = creator;
            return this;
        }

        public WorkoutDto build() {
            WorkoutDto workoutDto = new WorkoutDto();
            workoutDto.setId(id);
            workoutDto.setName(name);
            workoutDto.setDescription(description);
            workoutDto.setDifficulty(difficulty);
            workoutDto.setCalorieConsumption(calorieConsumption);
            workoutDto.setRating(rating);
            workoutDto.setVersion(version);
            workoutDto.setExercises(exercises);
            workoutDto.setCreator(creator);
            return workoutDto;
        }
    }

}
