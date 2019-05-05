package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;

@ApiModel(value = "DudeDto", description = "A dto for dude entries via rest")
public class DudeDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(required = true, name = "Name of Dude")
    private String name;

    @ApiModelProperty(name = "Self description of Dude")
    private String description = "No description given.";

    @ApiModelProperty(required = true, name = "Email adress of Dude")
    private String email;

    @ApiModelProperty(required = true, name = "Sex of Dude: female (F), male (M) or other (O)")
    private Character sex;

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

    // TODO: save fitness providers and courses

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
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


    public static DudeDtoBuilder builder() {
        return new DudeDtoBuilder();
    }

    @Override
    public String toString() {
        return "Dude{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", email='" + email + '\'' +
            ", sex=" + sex +
            ", status=" + status +
            ", selfAssessment=" + selfAssessment +
            ", birthday=" + birthday +
            ", height=" + height +
            ", weight=" + weight +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dude dude = (Dude) o;

        if (id != null ? !id.equals(dude.getId()) : dude.getId() != null) return false;
        if (name != null ? !name.equals(dude.getName()) : dude.getName() != null) return false;
        if (description != null ? !description.equals(dude.getDescription()) : dude.getDescription() != null) return false;
        if (email != null ? !email.equals(dude.getEmail()) : dude.getEmail() != null) return false;
        if (sex != null ? !sex.equals(dude.getSex()) : dude.getSex() != null) return false;
        if (status != null ? !status.equals(dude.getStatus()) : dude.getStatus() != null) return false;
        if (selfAssessment != null ? !selfAssessment.equals(dude.getSelfAssessment()) : dude.getSelfAssessment() != null)
            return false;
        if (birthday != null ? !birthday.equals(dude.getBirthday()) : dude.getBirthday() != null) return false;
        if (height != null ? !height.equals(dude.getHeight()) : dude.getHeight() != null) return false;
        return (weight != null ? !weight.equals(dude.getWeight()) : dude.getWeight() != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (selfAssessment != null ? selfAssessment.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    public static final class DudeDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private String email;
        private Character sex;
        private Integer status;
        private Integer selfAssessment;
        private LocalDate birthday;
        private Double height;
        private Double weight;

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

        public DudeDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public DudeDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public DudeDtoBuilder sex(Character sex) {
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

        public DudeDto build() {
            DudeDto dude = new DudeDto();
            dude.setId(id);
            dude.setName(name);
            dude.setDescription(description);
            dude.setEmail(email);
            dude.setSex(sex);
            dude.setStatus(status);
            dude.setSelfAssessment(selfAssessment);
            dude.setBirthday(birthday);
            dude.setHeight(height);
            dude.setWeight(weight);
            return dude;
        }
    }
}
