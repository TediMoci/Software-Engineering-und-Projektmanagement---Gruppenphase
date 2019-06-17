package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TrainingScheduleWorkoutDtoOut", description = "A dto for TrainingSchedule-Workout-relationship outputs via rest")
public class TrainingScheduleWorkoutDtoOut {

    @ApiModelProperty(name = "Id of Workout")
    private Long id;

    @ApiModelProperty(name = "Version of Workout")
    private Integer version = 1;

    @ApiModelProperty(name = "Name of Workout")
    private String name;

    @ApiModelProperty(name = "Description of Workout")
    private String description;

    @ApiModelProperty(name = "Difficulty of Workout")
    private Integer difficulty;

    @ApiModelProperty(name = "Calorie consumption of Workout")
    private Double calorieConsumption;

    @ApiModelProperty(name = "Rating of Workout")
    private Double rating;

    @ApiModelProperty(name = "Name of creator of Workout")
    private String creatorName;

    @ApiModelProperty(name = "Day of Workout")
    private Integer day;

    @ApiModelProperty(name = "Visibility of Workout")
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public static TrainingScheduleWorkoutDtoOutBuilder builder() {
        return new TrainingScheduleWorkoutDtoOutBuilder();
    }

    @Override
    public String toString() {
        return "TrainingScheduleWorkoutDtoOut{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", difficulty=" + difficulty +
            ", calorieConsumption=" + calorieConsumption +
            ", rating=" + rating +
            ", creatorName='" + creatorName + '\'' +
            ", day=" + day +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingScheduleWorkoutDtoOut that = (TrainingScheduleWorkoutDtoOut) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (difficulty != null ? !difficulty.equals(that.difficulty) : that.difficulty != null) return false;
        if (calorieConsumption != null ? !calorieConsumption.equals(that.calorieConsumption) : that.calorieConsumption != null)
            return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (creatorName != null ? !creatorName.equals(that.creatorName) : that.creatorName != null) return false;
        return day != null ? day.equals(that.day) : that.day == null;

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
        result = 31 * result + (creatorName != null ? creatorName.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }

    public static final class TrainingScheduleWorkoutDtoOutBuilder {
        private Long id;
        private Integer version;
        private String name;
        private String description;
        private Integer difficulty;
        private Double calorieConsumption;
        private Double rating;
        private String creatorName;
        private Integer day;
        private Boolean isPrivate;

        public TrainingScheduleWorkoutDtoOutBuilder() {
        }

        public TrainingScheduleWorkoutDtoOutBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TrainingScheduleWorkoutDtoOutBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public TrainingScheduleWorkoutDtoOutBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TrainingScheduleWorkoutDtoOutBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TrainingScheduleWorkoutDtoOutBuilder difficulty(Integer difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public TrainingScheduleWorkoutDtoOutBuilder calorieConsumption(Double calorieConsumption) {
            this.calorieConsumption = calorieConsumption;
            return this;
        }

        public TrainingScheduleWorkoutDtoOutBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public TrainingScheduleWorkoutDtoOutBuilder creatorName(String creatorName) {
            this.creatorName = creatorName;
            return this;
        }

        public TrainingScheduleWorkoutDtoOutBuilder day(Integer day) {
            this.day = day;
            return this;
        }

        public TrainingScheduleWorkoutDtoOutBuilder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public TrainingScheduleWorkoutDtoOut build() {
            TrainingScheduleWorkoutDtoOut trainingScheduleWorkoutDtoOut = new TrainingScheduleWorkoutDtoOut();
            trainingScheduleWorkoutDtoOut.setId(id);
            trainingScheduleWorkoutDtoOut.setVersion(version);
            trainingScheduleWorkoutDtoOut.setName(name);
            trainingScheduleWorkoutDtoOut.setDescription(description);
            trainingScheduleWorkoutDtoOut.setDifficulty(difficulty);
            trainingScheduleWorkoutDtoOut.setCalorieConsumption(calorieConsumption);
            trainingScheduleWorkoutDtoOut.setRating(rating);
            trainingScheduleWorkoutDtoOut.setCreatorName(creatorName);
            trainingScheduleWorkoutDtoOut.setDay(day);
            trainingScheduleWorkoutDtoOut.setIsPrivate(isPrivate);
            return trainingScheduleWorkoutDtoOut;
        }
    }
}
