package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Objects;

@ApiModel(value = "CourseDto", description = "A dto for course entries via rest")
public class CourseDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(required = true, name = "Name of Course")
    @NotBlank(message = "Name must not be empty")
    @Size(min = 1, max = 100, message = "Name length must be between 1 and 100")
    private String name;

    @ApiModelProperty(name = "Description of Course")
    @Size(max = 3000, message = "Max description length is 3000")
    private String description = "No description given.";

    @ApiModelProperty(name = "Path of picture of Course")
    private String imagePath = "http://localhost:8080/downloadImage/exercise.png";

    @ApiModelProperty(name = "Rating of Course")
    @Min(0) @Max(5)
    private Double rating = 0.0;

    @ApiModelProperty(name = "FitnessProvider offering the Course")
    private Long creatorId;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public static CourseDtoBuilder builder() {
        return new CourseDtoBuilder();
    }

    @Override
    public String toString() {
        return "CourseDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", imagePath='" + imagePath + '\'' +
            ", rating=" + rating +
            ", creatorId=" + creatorId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;

        if (id != null ? !id.equals(courseDto.id) : courseDto.id != null) return false;
        if (name != null ? !name.equals(courseDto.name) : courseDto.name != null) return false;
        if (description != null ? !description.equals(courseDto.description) : courseDto.description != null)
            return false;
        if (imagePath != null ? !imagePath.equals(courseDto.imagePath) : courseDto.imagePath != null) return false;
        if (rating != null ? !rating.equals(courseDto.rating) : courseDto.rating != null) return false;
        return creatorId != null ? creatorId.equals(courseDto.creatorId) : courseDto.creatorId == null;

    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getImagePath(), getRating(), getCreatorId());
    }

    public static final class CourseDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private Double rating;
        private String imagePath;
        private Long creatorId;

        public CourseDtoBuilder() {
        }

        public CourseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CourseDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseDtoBuilder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public CourseDtoBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public CourseDtoBuilder creatorId(Long creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public CourseDto build() {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(id);
            courseDto.setName(name);
            courseDto.setDescription(description);
            courseDto.setImagePath(imagePath);
            courseDto.setRating(rating);
            courseDto.setCreatorId(creatorId);
            return courseDto;
        }
    }

}
