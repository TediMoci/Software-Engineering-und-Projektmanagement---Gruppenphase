package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors;

import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.ElementCollection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApiModel(value = "DudeDto", description = "A dto for dude entries via rest")
public class DudeDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(required = true, name = "Name of Dude")
    private String name;

    @ApiModelProperty(required = true, name = "Password of Dude")
    private String password;

    @ApiModelProperty(name = "Self description of Dude")
    private String description = "No description given.";

    @ApiModelProperty(required = true, name = "Email adress of Dude")
    private String email;

    @ApiModelProperty(required = true, name = "Sex of Dude")
    private Sex sex;

    @ApiModelProperty(required = true, name = "System status of Dude: 1-3")
    private Integer status = 1;

    @ApiModelProperty(required = true, name = "Self assessment status of Dude")
    private Integer selfAssessment;

    @ApiModelProperty(required = true, name = "Birthday of Dude")
    private LocalDate birthday;

    @ApiModelProperty(required = true, name = "Height of Dude")
    private Double height;

    @ApiModelProperty(required = true, name = "Weight of Dude")
    private Double weight;

    @ElementCollection
    private List<String> roles = new ArrayList<String>() {
        {
            add("DUDE");
        }
    };

    @ApiModelProperty(name = "FitnessProviders the Dude follows")
    private Set<FitnessProvider> fitnessProviders;

    @ApiModelProperty(name = "Courses the Dude takes")
    private Set<Course> courses;

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

    public String getPassword(){ return password; }

    public void setPassword(String password){ this.password = password; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSelfAssessment() {
        return selfAssessment;
    }

    public void setSelfAssessment(Integer selfAssessment) {
        this.selfAssessment = selfAssessment;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Set<FitnessProvider> getFitnessProviders() {
        return fitnessProviders;
    }

    public void setFitnessProviders(Set<FitnessProvider> fitnessProviders) {
        this.fitnessProviders = fitnessProviders;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public static DudeDtoBuilder builder() {
        return new DudeDtoBuilder();
    }

    @Override
    public String toString() {
        return "DudeDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", description='" + description + '\'' +
            ", email='" + email + '\'' +
            ", sex=" + sex +
            ", status=" + status +
            ", selfAssessment=" + selfAssessment +
            ", birthday=" + birthday +
            ", height=" + height +
            ", weight=" + weight +
            ", roles=" + roles +
            ", fitnessProviders=" + fitnessProviders +
            ", courses=" + courses +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DudeDto dudeDto = (DudeDto) o;

        if (id != null ? !id.equals(dudeDto.id) : dudeDto.id != null) return false;
        if (name != null ? !name.equals(dudeDto.name) : dudeDto.name != null) return false;
        if (password != null ? !password.equals(dudeDto.password) : dudeDto.password != null) return false;
        if (description != null ? !description.equals(dudeDto.description) : dudeDto.description != null) return false;
        if (email != null ? !email.equals(dudeDto.email) : dudeDto.email != null) return false;
        if (sex != dudeDto.sex) return false;
        if (status != null ? !status.equals(dudeDto.status) : dudeDto.status != null) return false;
        if (selfAssessment != null ? !selfAssessment.equals(dudeDto.selfAssessment) : dudeDto.selfAssessment != null)
            return false;
        if (birthday != null ? !birthday.equals(dudeDto.birthday) : dudeDto.birthday != null) return false;
        if (height != null ? !height.equals(dudeDto.height) : dudeDto.height != null) return false;
        if (weight != null ? !weight.equals(dudeDto.weight) : dudeDto.weight != null) return false;
        if (roles != null ? !roles.equals(dudeDto.roles) : dudeDto.roles != null) return false;
        if (fitnessProviders != null ? !fitnessProviders.equals(dudeDto.fitnessProviders) : dudeDto.fitnessProviders != null)
            return false;
        return courses != null ? courses.equals(dudeDto.courses) : dudeDto.courses == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (selfAssessment != null ? selfAssessment.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (fitnessProviders != null ? fitnessProviders.hashCode() : 0);
        result = 31 * result + (courses != null ? courses.hashCode() : 0);
        return result;
    }

    public static final class DudeDtoBuilder {
        private Long id;
        private String name;
        private String password;
        private String description;
        private String email;
        private Sex sex;
        private Integer status;
        private Integer selfAssessment;
        private LocalDate birthday;
        private Double height;
        private Double weight;
        private List<String> roles;
        private Set<FitnessProvider> fitnessProviders;
        private Set<Course> courses;

        public DudeDtoBuilder() {
        }

        public DudeDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DudeDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DudeDtoBuilder password(String password){
            this.password = password;
            return this;
        }

        public DudeDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public DudeDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public DudeDtoBuilder sex(Sex sex) {
            this.sex = sex;
            return this;
        }

        public DudeDtoBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public DudeDtoBuilder selfAssessment(Integer selfAssessment) {
            this.selfAssessment = selfAssessment;
            return this;
        }

        public DudeDtoBuilder birthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public DudeDtoBuilder height(Double height) {
            this.height = height;
            return this;
        }

        public DudeDtoBuilder weight(Double weight) {
            this.weight = weight;
            return this;
        }

        public DudeDtoBuilder roles(List<String> roles){
            this.roles = roles;
            return this;
        }

        public DudeDtoBuilder fitnessProviders(Set<FitnessProvider> fitnessProviders) {
            this.fitnessProviders = fitnessProviders;
            return this;
        }

        public DudeDtoBuilder courses(Set<Course> courses) {
            this.courses = courses;
            return this;
        }

        public DudeDto build() {
            DudeDto dude = new DudeDto();
            dude.setId(id);
            dude.setName(name);
            dude.setPassword(password);
            dude.setDescription(description);
            dude.setEmail(email);
            dude.setSex(sex);
            dude.setStatus(status);
            dude.setSelfAssessment(selfAssessment);
            dude.setBirthday(birthday);
            dude.setHeight(height);
            dude.setWeight(weight);
            dude.setRoles(roles);
            dude.setFitnessProviders(fitnessProviders);
            dude.setCourses(courses);
            return dude;
        }
    }
}
