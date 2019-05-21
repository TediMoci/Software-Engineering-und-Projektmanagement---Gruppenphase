package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.util.Set;

@ApiModel(value = "ExerciseDto", description = "A dto for exercise entries via rest")
public class ExerciseDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(name = "Version of Exercise")
    private Integer version = 1;

    @ApiModelProperty(required = true, name = "Name of Exercise")
    @NotBlank(message = "Name must not be empty")
    @Size(min = 1, max = 50, message = "Name length must be between 1 and 50")
    private String name;

    @ApiModelProperty(name = "Description of Exercise")
    @Size(max = 1000, message = "Max description length is 1000")
    private String description = "No description given.";

    @ApiModelProperty(name = "Equipment needed for Exercise")
    @Size(max = 300, message = "Max equipment length is 300")
    private String equipment = "No needed equipment given.";

    @ApiModelProperty(name = "Muscle group trained by Exercise")
    @Size(max = 100, message = "Max muscleGroup length is 100")
    private String muscleGroup = "No muscle group given";

    @ApiModelProperty(name = "Rating of Exercise")
    @Min(1) @Max(5)
    private Double rating = 1.0;

    @ApiModelProperty(required = true, name = "Category of Exercise")
    @NotNull(message = "Category must not be null")
    private Category category;

    @ApiModelProperty(required = true, name = "Difficulty_level of Exercise")
    @NotNull(message = "Difficulty_level must not be null")
    private String difficulty_level;

    @ApiModelProperty(name = "Workout-Exercise relationships that the Exercise is part of")
    private Set<WorkoutExercise> workouts;

    @ApiModelProperty(required = true, name = "ID of creator of Exercise")
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(String difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public Set<WorkoutExercise> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Set<WorkoutExercise> workouts) {
        this.workouts = workouts;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public static ExerciseDtoBuilder builder() {
        return new ExerciseDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExerciseDto{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", equipment='" + equipment + '\'' +
            ", muscleGroup='" + muscleGroup + '\'' +
            ", rating=" + rating +
            ", category=" + category +
            ", difficulty_level='" + difficulty_level + '\'' +
            ", workouts=" + workouts +
            ", creatorId=" + creatorId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseDto that = (ExerciseDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (equipment != null ? !equipment.equals(that.equipment) : that.equipment != null) return false;
        if (muscleGroup != null ? !muscleGroup.equals(that.muscleGroup) : that.muscleGroup != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (category != that.category) return false;
        if (difficulty_level != null ? !difficulty_level.equals(that.difficulty_level) : that.difficulty_level != null)
            return false;
        if (workouts != null ? !workouts.equals(that.workouts) : that.workouts != null) return false;
        return creatorId != null ? creatorId.equals(that.creatorId) : that.creatorId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (equipment != null ? equipment.hashCode() : 0);
        result = 31 * result + (muscleGroup != null ? muscleGroup.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (difficulty_level != null ? difficulty_level.hashCode() : 0);
        result = 31 * result + (workouts != null ? workouts.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        return result;
    }

    public static final class ExerciseDtoBuilder {
        private Long id;
        private Integer version;
        private String name;
        private String description;
        private String equipment;
        private String muscleGroup;
        private Double rating;
        private Category category;
        private String difficulty_level;
        private Set<WorkoutExercise> workouts;
        private Long creatorId;

        public ExerciseDtoBuilder() {
        }

        public ExerciseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ExerciseDtoBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public ExerciseDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ExerciseDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ExerciseDtoBuilder equipment(String equipment) {
            this.equipment = equipment;
            return this;
        }

        public ExerciseDtoBuilder muscleGroup(String muscleGroup) {
            this.muscleGroup = muscleGroup;
            return this;
        }

        public ExerciseDtoBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public ExerciseDtoBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ExerciseDtoBuilder difficulty_level(String difficulty_level){
            this.difficulty_level = difficulty_level;
            return this;
        }

        public ExerciseDtoBuilder workouts(Set<WorkoutExercise> workouts) {
            this.workouts = workouts;
            return this;
        }

        public ExerciseDtoBuilder creatorId(Long creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public ExerciseDto build() {
            ExerciseDto exerciseDto = new ExerciseDto();
            exerciseDto.setId(id);
            exerciseDto.setVersion(version);
            exerciseDto.setName(name);
            exerciseDto.setDescription(description);
            exerciseDto.setEquipment(equipment);
            exerciseDto.setMuscleGroup(muscleGroup);
            exerciseDto.setRating(rating);
            exerciseDto.setCategory(category);
            exerciseDto.setDifficulty_level(difficulty_level);
            exerciseDto.setWorkouts(workouts);
            exerciseDto.setCreatorId(creatorId);
            return exerciseDto;
        }
    }
}
