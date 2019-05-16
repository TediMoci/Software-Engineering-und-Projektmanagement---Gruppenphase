package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

@ApiModel(value = "ExerciseDto", description = "A dto for exercise entries via rest")
public class ExerciseDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(required = true, name = "Name of Exercise")
    private String name;

    @ApiModelProperty(name = "Description of Exercise")
    private String description = "No description given.";

    @ApiModelProperty(name = "Equipment needed for Exercise")
    private String equipment = "No needed equipment given.";

    @ApiModelProperty(name = "Muscle group trained by Exercise")
    private String muscleGroup = "No muscle group given";

    @ApiModelProperty(name = "Rating of Exercise")
    private Double rating = 1.0;

    @ApiModelProperty(required = true, name = "Category of Exercise")
    private Category category;

    @ApiModelProperty(name = "Workout-Exercise relationships that the Exercise is part of")
    private Set<WorkoutExercise> workouts;

    @ApiModelProperty(required = true, name = "Creator of Exercise")
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

    public Set<WorkoutExercise> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Set<WorkoutExercise> workouts) {
        this.workouts = workouts;
    }

    public Dude getCreator() {
        return creator;
    }

    public void setCreator(Dude creator) {
        this.creator = creator;
    }

    public static ExerciseDtoBuilder builder() {
        return new ExerciseDtoBuilder();
    }

    @Override
    public String toString() {
        return "ExerciseDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", equipment='" + equipment + '\'' +
            ", muscleGroup='" + muscleGroup + '\'' +
            ", rating=" + rating +
            ", category=" + category +
            ", workouts=" + workouts +
            ", creator=" + creator +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseDto exerciseDto = (ExerciseDto) o;

        if (id != null ? !id.equals(exerciseDto.id) : exerciseDto.id != null) return false;
        if (name != null ? !name.equals(exerciseDto.name) : exerciseDto.name != null) return false;
        if (description != null ? !description.equals(exerciseDto.description) : exerciseDto.description != null)
            return false;
        if (equipment != null ? !equipment.equals(exerciseDto.equipment) : exerciseDto.equipment != null) return false;
        if (muscleGroup != null ? !muscleGroup.equals(exerciseDto.muscleGroup) : exerciseDto.muscleGroup != null)
            return false;
        if (rating != null ? !rating.equals(exerciseDto.rating) : exerciseDto.rating != null) return false;
        if (category != exerciseDto.category) return false;
        if (workouts != null ? !workouts.equals(exerciseDto.workouts) : exerciseDto.workouts != null) return false;
        return creator != null ? creator.equals(exerciseDto.creator) : exerciseDto.creator == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (equipment != null ? equipment.hashCode() : 0);
        result = 31 * result + (muscleGroup != null ? muscleGroup.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (workouts != null ? workouts.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        return result;
    }

    public static final class ExerciseDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private String equipment;
        private String muscleGroup;
        private Double rating;
        private Category category;
        private Set<WorkoutExercise> workouts;
        private Dude creator;

        public ExerciseDtoBuilder() {
        }

        public ExerciseDtoBuilder id(Long id) {
            this.id = id;
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

        public ExerciseDtoBuilder workouts(Set<WorkoutExercise> workouts) {
            this.workouts = workouts;
            return this;
        }

        public ExerciseDtoBuilder creator(Dude creator) {
            this.creator = creator;
            return this;
        }

        public ExerciseDto build() {
            ExerciseDto exerciseDto = new ExerciseDto();
            exerciseDto.setId(id);
            exerciseDto.setName(name);
            exerciseDto.setDescription(description);
            exerciseDto.setEquipment(equipment);
            exerciseDto.setMuscleGroup(muscleGroup);
            exerciseDto.setRating(rating);
            exerciseDto.setCategory(category);
            exerciseDto.setWorkouts(workouts);
            exerciseDto.setCreator(creator);
            return exerciseDto;
        }
    }
}
