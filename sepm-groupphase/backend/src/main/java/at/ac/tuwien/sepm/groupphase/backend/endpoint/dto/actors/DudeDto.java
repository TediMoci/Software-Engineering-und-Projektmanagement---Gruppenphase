package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
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

    @ApiModelProperty(required = true, name = "Sex of Dude: female (F), male (M) or other (O)")
    private String sex;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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
        return "Dude{" +
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
            ", fitnessProviders=" + fitnessProviders +
            ", courses=" + courses +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DudeDto dude = (DudeDto) o;

        if (id != null ? !id.equals(dude.id) : dude.id != null) return false;
        if (name != null ? !name.equals(dude.name) : dude.name != null) return false;
        if (password != null ? !password.equals(dude.password) : dude.password != null) return false;
        if (description != null ? !description.equals(dude.description) : dude.description != null) return false;
        if (email != null ? !email.equals(dude.email) : dude.email != null) return false;
        if (sex != null ? !sex.equals(dude.sex) : dude.sex != null) return false;
        if (status != null ? !status.equals(dude.status) : dude.status != null) return false;
        if (selfAssessment != null ? !selfAssessment.equals(dude.selfAssessment) : dude.selfAssessment != null)
            return false;
        if (birthday != null ? !birthday.equals(dude.birthday) : dude.birthday != null) return false;
        if (height != null ? !height.equals(dude.height) : dude.height != null) return false;
        if (weight != null ? !weight.equals(dude.weight) : dude.weight != null) return false;
        if (fitnessProviders != null ? !fitnessProviders.equals(dude.fitnessProviders) : dude.fitnessProviders != null)
            return false;
        return courses != null ? courses.equals(dude.courses) : dude.courses == null;

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
        private String sex;
        private Integer status;
        private Integer selfAssessment;
        private LocalDate birthday;
        private Double height;
        private Double weight;
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

        public DudeDtoBuilder sex(String sex) {
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
            dude.setFitnessProviders(fitnessProviders);
            dude.setCourses(courses);
            return dude;
        }
    }
}
