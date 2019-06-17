package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
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

    @ApiModelProperty(name = "Rating of Exercise")
    @Min(0) @Max(5)
    private Double rating = 0.0;

    @ApiModelProperty(name = "FitnessProvider offering the Course")
    private Long creatorId;

    @ApiModelProperty(name = "Dudes having the Course bookmarked")
    private DudeDto[] dudeDtos;

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

    public DudeDto[] getDudeDtos() {
        return dudeDtos;
    }

    public void setDudeDtos(DudeDto[] dudeDtos) {
        this.dudeDtos = dudeDtos;
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
            ", rating=" + rating +
            ", creatorId=" + creatorId +
            ", dudeDtos=" + Arrays.toString(dudeDtos) +
            '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;
        return Objects.equals(getId(), courseDto.getId()) &&
            Objects.equals(getName(), courseDto.getName()) &&
            Objects.equals(getDescription(), courseDto.getDescription()) &&
            Objects.equals(getRating(), courseDto.getRating()) &&
            Objects.equals(getCreatorId(), courseDto.getCreatorId()) &&
            Arrays.equals(getDudeDtos(), courseDto.getDudeDtos());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getName(), getDescription(), getRating(), getCreatorId());
        result = 31 * result + Arrays.hashCode(getDudeDtos());
        return result;
    }

    public static final class CourseDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private Double rating;
        private Long creatorId;
        private DudeDto[] dudeDtos;

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

        public CourseDtoBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public CourseDtoBuilder creatorId(Long creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public CourseDtoBuilder dudeDtos(DudeDto[] dudeDtos) {
            this.dudeDtos = dudeDtos;
            return this;
        }

        public CourseDto build() {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(id);
            courseDto.setName(name);
            courseDto.setDescription(description);
            courseDto.setRating(rating);
            courseDto.setCreatorId(creatorId);
            courseDto.setDudeDtos(dudeDtos);
            return courseDto;
        }
    }

}
