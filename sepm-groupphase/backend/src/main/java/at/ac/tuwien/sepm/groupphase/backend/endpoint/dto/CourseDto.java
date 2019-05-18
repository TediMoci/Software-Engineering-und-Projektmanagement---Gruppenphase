package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(value = "CourseDto", description = "A dto for course entries via rest")
public class CourseDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(required = true, name = "Name of Course")
    @NotBlank(message = "Name must not be empty")
    @Size(min = 1, max = 50, message = "Name length must be between 1 and 50")
    private String name;

    @ApiModelProperty(name = "Description of Course")
    @Size(max = 1000, message = "Max description length is 1000")
    private String description = "No description given.";

    @ApiModelProperty(name = "FitnessProvider offering the Course")
    private FitnessProviderDto fitnessProviderDto;

    @ApiModelProperty(name = "Dudes having the Course bookmarked")
    private Set<DudeDto> dudeDtos;

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

    public FitnessProviderDto getFitnessProviderDto() {
        return fitnessProviderDto;
    }

    public void setFitnessProviderDto(FitnessProviderDto fitnessProviderDto) {
        this.fitnessProviderDto = fitnessProviderDto;
    }

    public Set<DudeDto> getDudeDtos() {
        return dudeDtos;
    }

    public void setDudeDtos(Set<DudeDto> dudeDtos) {
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
            ", fitnessProviderDto=" + fitnessProviderDto +
            ", dudeDtos=" + dudeDtos +
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
        if (fitnessProviderDto != null ? !fitnessProviderDto.equals(courseDto.fitnessProviderDto) : courseDto.fitnessProviderDto != null)
            return false;
        return dudeDtos != null ? dudeDtos.equals(courseDto.dudeDtos) : courseDto.dudeDtos == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (fitnessProviderDto != null ? fitnessProviderDto.hashCode() : 0);
        result = 31 * result + (dudeDtos != null ? dudeDtos.hashCode() : 0);
        return result;
    }

    public static final class CourseDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private FitnessProviderDto fitnessProviderDto;
        private Set<DudeDto> dudeDtos;

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

        public CourseDtoBuilder fitnessProviderDto(FitnessProviderDto fitnessProviderDto) {
            this.fitnessProviderDto = fitnessProviderDto;
            return this;
        }

        public CourseDtoBuilder dudeDtos(Set<DudeDto> dudeDtos) {
            this.dudeDtos = dudeDtos;
            return this;
        }

        public CourseDto build() {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(id);
            courseDto.setName(name);
            courseDto.setDescription(description);
            courseDto.setFitnessProviderDto(fitnessProviderDto);
            courseDto.setDudeDtos(dudeDtos);
            return courseDto;
        }
    }

}
