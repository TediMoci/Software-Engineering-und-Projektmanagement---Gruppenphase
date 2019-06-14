package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.util.Objects;

@ApiModel(value = "TrainingScheduleRandomDto", description = "A dto for random crated training-schedule entries")
public class TrainingScheduleRandomDto {

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

    @ApiModelProperty(required = true, name = "Duration per day of TrainingSchedule")
    @NotNull(message = "duration must be given")
    @Min(value = 1, message = "Min duration value is 1") @Max(value = 1440, message = "Max duration value is 7")
    private Integer duration;

    @ApiModelProperty(required = true, name = "Minimum amount of calories per day of TrainingSchedule")
    @NotNull(message = "minTarget must be given")
    @Min(value = 1, message = "Min minTarget value is 1")
    private double minTarget;

    @ApiModelProperty(required = true, name = "Maximum amount of calories per day of TrainingSchedule")
    @NotNull(message = "maxTarget must be given")
    @Min(value = 1, message = "Min maxTarget value is 1")
    private double maxTarget;

    @ApiModelProperty(required = true, name = "Allow workouts of lower difficulty")
    @NotNull(message = "lowerDifficulty must be given")
    private boolean lowerDifficulty;

    @ApiModelProperty(name = "Rating of TrainingSchedule")
    @Min(1) @Max(5)
    private Double rating = 1.0;

    @ApiModelProperty(required = true, name = "ID of creator of TrainingSchedule")
    private Long creatorId;

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Integer getIntervalLength() {
        return intervalLength;
    }

    public Double getRating() {
        return rating;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public void setIntervalLength(Integer intervalLength) {
        this.intervalLength = intervalLength;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public double getMinTarget() {
        return minTarget;
    }

    public void setMinTarget(double minTarget) {
        this.minTarget = minTarget;
    }

    public double getMaxTarget() {
        return maxTarget;
    }

    public void setMaxTarget(double maxTarget) {
        this.maxTarget = maxTarget;
    }

    public boolean isLowerDifficulty() {
        return lowerDifficulty;
    }

    public void setLowerDifficulty(boolean lowerDifficulty) {
        this.lowerDifficulty = lowerDifficulty;
    }

    @Override
    public String toString() {
        return "TrainingScheduleRandomDto{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", difficulty=" + difficulty +
            ", intervalLength=" + intervalLength +
            ", duration=" + duration +
            ", minTarget=" + minTarget +
            ", maxTarget=" + maxTarget +
            ", lowerDifficulty=" + lowerDifficulty +
            ", rating=" + rating +
            ", creatorId=" + creatorId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingScheduleRandomDto that = (TrainingScheduleRandomDto) o;
        return Double.compare(that.getMinTarget(), getMinTarget()) == 0 &&
            Double.compare(that.getMaxTarget(), getMaxTarget()) == 0 &&
            isLowerDifficulty() == that.isLowerDifficulty() &&
            getId().equals(that.getId()) &&
            getVersion().equals(that.getVersion()) &&
            getName().equals(that.getName()) &&
            getDescription().equals(that.getDescription()) &&
            getDifficulty().equals(that.getDifficulty()) &&
            getIntervalLength().equals(that.getIntervalLength()) &&
            getDuration().equals(that.getDuration()) &&
            getRating().equals(that.getRating()) &&
            getCreatorId().equals(that.getCreatorId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVersion(), getName(), getDescription(), getDifficulty(), getIntervalLength(), getDuration(), getMinTarget(), getMaxTarget(), isLowerDifficulty(), getRating(), getCreatorId());
    }

    public static TrainingScheduleRandomDtoBuilder builder() {
        return new TrainingScheduleRandomDtoBuilder();
    }

    public static final class TrainingScheduleRandomDtoBuilder {
        private Long id;
        private Integer version;
        private String name;
        private String description;
        private Integer difficulty;
        private Integer intervalLength;
        private Double rating;
        private Long creatorId;

        public TrainingScheduleRandomDtoBuilder() {
        }

        public TrainingScheduleRandomDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TrainingScheduleRandomDtoBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public TrainingScheduleRandomDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TrainingScheduleRandomDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TrainingScheduleRandomDtoBuilder difficulty(Integer difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public TrainingScheduleRandomDtoBuilder intervalLength(Integer intervalLength) {
            this.intervalLength = intervalLength;
            return this;
        }

        public TrainingScheduleRandomDtoBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public TrainingScheduleRandomDtoBuilder creatorId(Long creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public TrainingScheduleRandomDto build() {
            TrainingScheduleRandomDto trainingScheduleRandomDto = new TrainingScheduleRandomDto();
            trainingScheduleRandomDto.setId(id);
            trainingScheduleRandomDto.setVersion(version);
            trainingScheduleRandomDto.setName(name);
            trainingScheduleRandomDto.setDescription(description);
            trainingScheduleRandomDto.setDifficulty(difficulty);
            trainingScheduleRandomDto.setIntervalLength(intervalLength);
            trainingScheduleRandomDto.setRating(rating);
            trainingScheduleRandomDto.setCreatorId(creatorId);
            return trainingScheduleRandomDto;
        }
    }
}
