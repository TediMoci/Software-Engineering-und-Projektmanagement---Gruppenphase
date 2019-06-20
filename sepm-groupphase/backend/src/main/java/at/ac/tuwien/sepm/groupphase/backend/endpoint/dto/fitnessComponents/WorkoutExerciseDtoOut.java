package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.MuscleGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "WorkoutExerciseDtoOut", description = "A dto for workout-exercise-relationship outputs via rest")
public class WorkoutExerciseDtoOut {

    @ApiModelProperty(name = "Id of Exercise")
    private Long id;

    @ApiModelProperty(name = "Version of Exercise")
    private Integer version;

    @ApiModelProperty(name = "Name of Exercise")
    private String name;

    @ApiModelProperty(name = "Description of Exercise")
    private String description;

    @ApiModelProperty(name = "Equipment needed for Exercise")
    private String equipment;

    @ApiModelProperty(name = "Muscle group trained by Exercise")
    private MuscleGroup muscleGroup;

    @ApiModelProperty(name = "Rating of Exercise")
    private Double rating;

    @ApiModelProperty(name = "Category of Exercise")
    private Category category;

    @ApiModelProperty(name = "Name of creator of Exercise")
    private String creatorName;

    @ApiModelProperty(name = "Duration of Exercise")
    private Integer exDuration;

    @ApiModelProperty(name = "Repetitions of Exercise")
    private Integer repetitions;

    @ApiModelProperty(name = "Sets of Exercise")
    private Integer sets;

    @ApiModelProperty(name = "Path of image of Exercise")
    private String imagePath;

    @ApiModelProperty(name = "Visibility of Exercise")
    private Boolean isPrivate;

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

    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(MuscleGroup muscleGroup) {
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public static WorkoutExerciseDtoOutBuilder builder() {
        return new WorkoutExerciseDtoOutBuilder();
    }

    @Override
    public String toString() {
        return "WorkoutExerciseDtoOut{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", equipment='" + equipment + '\'' +
            ", muscleGroup=" + muscleGroup +
            ", rating=" + rating +
            ", category=" + category +
            ", creatorName='" + creatorName + '\'' +
            ", exDuration=" + exDuration +
            ", repetitions=" + repetitions +
            ", sets=" + sets +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExerciseDtoOut that = (WorkoutExerciseDtoOut) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (equipment != null ? !equipment.equals(that.equipment) : that.equipment != null) return false;
        if (muscleGroup != that.muscleGroup) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (category != that.category) return false;
        if (creatorName != null ? !creatorName.equals(that.creatorName) : that.creatorName != null) return false;
        if (exDuration != null ? !exDuration.equals(that.exDuration) : that.exDuration != null) return false;
        if (repetitions != null ? !repetitions.equals(that.repetitions) : that.repetitions != null) return false;
        return sets != null ? sets.equals(that.sets) : that.sets == null;

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
        result = 31 * result + (creatorName != null ? creatorName.hashCode() : 0);
        result = 31 * result + (exDuration != null ? exDuration.hashCode() : 0);
        result = 31 * result + (repetitions != null ? repetitions.hashCode() : 0);
        result = 31 * result + (sets != null ? sets.hashCode() : 0);
        return result;
    }

    public static final class WorkoutExerciseDtoOutBuilder {
        private Long id;
        private Integer version;
        private String name;
        private String description;
        private String equipment;
        private MuscleGroup muscleGroup;
        private Double rating;
        private Category category;
        private String creatorName;
        private Integer exDuration;
        private Integer repetitions;
        private Integer sets;
        private String imagePath;
        private Boolean isPrivate;

        public WorkoutExerciseDtoOutBuilder() {
        }

        public WorkoutExerciseDtoOutBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder description(String description) {
            this.description = description;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder equipment(String equipment) {
            this.equipment = equipment;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder muscleGroup(MuscleGroup muscleGroup) {
            this.muscleGroup = muscleGroup;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder creatorName(String creatorName) {
            this.creatorName = creatorName;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder exDuration(Integer exDuration) {
            this.exDuration = exDuration;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder repetitions(Integer repetitions) {
            this.repetitions = repetitions;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder sets(Integer sets) {
            this.sets = sets;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public WorkoutExerciseDtoOutBuilder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public WorkoutExerciseDtoOut build() {
            WorkoutExerciseDtoOut workoutExerciseDtoOut = new WorkoutExerciseDtoOut();
            workoutExerciseDtoOut.setId(id);
            workoutExerciseDtoOut.setVersion(version);
            workoutExerciseDtoOut.setName(name);
            workoutExerciseDtoOut.setDescription(description);
            workoutExerciseDtoOut.setEquipment(equipment);
            workoutExerciseDtoOut.setMuscleGroup(muscleGroup);
            workoutExerciseDtoOut.setRating(rating);
            workoutExerciseDtoOut.setCategory(category);
            workoutExerciseDtoOut.setCreatorName(creatorName);
            workoutExerciseDtoOut.setExDuration(exDuration);
            workoutExerciseDtoOut.setRepetitions(repetitions);
            workoutExerciseDtoOut.setSets(sets);
            workoutExerciseDtoOut.setImagePath(imagePath);
            workoutExerciseDtoOut.setIsPrivate(isPrivate);
            return workoutExerciseDtoOut;
        }
    }
}
