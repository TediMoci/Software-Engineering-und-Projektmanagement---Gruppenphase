package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;

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
    private String imagePath = "exercise.png";

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
            ", creatorId=" + creatorId +
            ", dudeDtos=" + Arrays.toString(dudeDtos) +
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
        if (creatorId != null ? !creatorId.equals(courseDto.creatorId) : courseDto.creatorId != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(dudeDtos, courseDto.dudeDtos);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(dudeDtos);
        return result;
    }

    public static final class CourseDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private String imagePath;
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

        public CourseDtoBuilder imagePath(String imagePath) {
            this.imagePath = imagePath;
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
            courseDto.setImagePath(imagePath);
            courseDto.setCreatorId(creatorId);
            courseDto.setDudeDtos(dudeDtos);
            return courseDto;
        }
    }

}
