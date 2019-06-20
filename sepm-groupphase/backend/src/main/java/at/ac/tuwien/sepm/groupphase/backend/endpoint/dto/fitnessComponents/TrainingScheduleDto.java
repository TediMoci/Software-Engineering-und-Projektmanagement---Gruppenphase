package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.util.Arrays;

@ApiModel(value = "TrainingScheduleDto", description = "A dto for training-schedule entries via rest")
public class TrainingScheduleDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(name = "Version of TrainingSchedule")
    @Min(value = 1, message = "Min version value is 1")
    private Integer version = 1;

    @ApiModelProperty(required = true, name = "Name of TrainingSchedule")
    @NotBlank(message = "Name must not be empty")
    @Size(min = 1, max = 100, message = "Name length must be between 1 and 100")
    private String name;

    @ApiModelProperty(name = "Description of TrainingSchedule")
    @Size(max = 3000, message = "Max description length is 3000")
    private String description = "No description given.";

    @ApiModelProperty(required = true, name = "Difficulty of TrainingSchedule")
    @NotNull(message = "Difficulty must be given")
    @Min(value = 1, message = "Min difficulty value is 1") @Max(value = 3, message = "Max difficulty value is 3")
    private Integer difficulty;

    @ApiModelProperty(required = true, name = "Length of Interval of TrainingSchedule")
    @NotNull(message = "intervalLength must be given")
    @Min(value = 1, message = "Min intervalLength value is 1") @Max(value = 7, message = "Max intervalLength value is 7")
    private Integer intervalLength;

    @ApiModelProperty(name = "Rating of TrainingSchedule")
    @Min(0) @Max(5)
    private Double rating = 0.0;

    @ApiModelProperty(name = "Visibility of TrainingSchedule")
    private Boolean isPrivate = false;

    @ApiModelProperty(name = "TrainingSchedule-Workouts that are part of the TrainingSchedule")
    private TrainingScheduleWorkoutDtoIn[] trainingScheduleWorkouts;

    @ApiModelProperty(required = true, name = "ID of creator of TrainingSchedule")
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

    public Integer getIntervalLength() {
        return intervalLength;
    }

    public void setIntervalLength(Integer intervalLength) {
        this.intervalLength = intervalLength;
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

    public TrainingScheduleWorkoutDtoIn[] getTrainingScheduleWorkouts() {
        return trainingScheduleWorkouts;
    }

    public void setTrainingScheduleWorkouts(TrainingScheduleWorkoutDtoIn[] trainingScheduleWorkouts) {
        this.trainingScheduleWorkouts = trainingScheduleWorkouts;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public static TrainingScheduleDtoBuilder builder() {
        return new TrainingScheduleDtoBuilder();
    }

    @Override
    public String toString() {
        return "TrainingScheduleDto{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", difficulty=" + difficulty +
            ", intervalLength=" + intervalLength +
            ", rating=" + rating +
            ", trainingScheduleWorkouts=" + Arrays.toString(trainingScheduleWorkouts) +
            ", creatorId=" + creatorId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingScheduleDto that = (TrainingScheduleDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (difficulty != null ? !difficulty.equals(that.difficulty) : that.difficulty != null) return false;
        if (intervalLength != null ? !intervalLength.equals(that.intervalLength) : that.intervalLength != null)
            return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(trainingScheduleWorkouts, that.trainingScheduleWorkouts)) return false;
        return creatorId != null ? creatorId.equals(that.creatorId) : that.creatorId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (intervalLength != null ? intervalLength.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(trainingScheduleWorkouts);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        return result;
    }

    public static final class TrainingScheduleDtoBuilder {
        private Long id;
        private Integer version;
        private String name;
        private String description;
        private Integer difficulty;
        private Integer intervalLength;
        private Double rating;
        private Boolean isPrivate;
        private TrainingScheduleWorkoutDtoIn[] trainingScheduleWorkouts;
        private Long creatorId;

        public TrainingScheduleDtoBuilder() {
        }

        public TrainingScheduleDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TrainingScheduleDtoBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public TrainingScheduleDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TrainingScheduleDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TrainingScheduleDtoBuilder difficulty(Integer difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public TrainingScheduleDtoBuilder intervalLength(Integer intervalLength) {
            this.intervalLength = intervalLength;
            return this;
        }

        public TrainingScheduleDtoBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public TrainingScheduleDtoBuilder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public TrainingScheduleDtoBuilder trainingScheduleWorkouts(TrainingScheduleWorkoutDtoIn[] trainingScheduleWorkouts) {
            this.trainingScheduleWorkouts = trainingScheduleWorkouts;
            return this;
        }

        public TrainingScheduleDtoBuilder creatorId(Long creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public TrainingScheduleDto build() {
            TrainingScheduleDto trainingScheduleDto = new TrainingScheduleDto();
            trainingScheduleDto.setId(id);
            trainingScheduleDto.setVersion(version);
            trainingScheduleDto.setName(name);
            trainingScheduleDto.setDescription(description);
            trainingScheduleDto.setDifficulty(difficulty);
            trainingScheduleDto.setIntervalLength(intervalLength);
            trainingScheduleDto.setRating(rating);
            trainingScheduleDto.setIsPrivate(isPrivate);
            trainingScheduleDto.setTrainingScheduleWorkouts(trainingScheduleWorkouts);
            trainingScheduleDto.setCreatorId(creatorId);
            return trainingScheduleDto;
        }
    }
}
