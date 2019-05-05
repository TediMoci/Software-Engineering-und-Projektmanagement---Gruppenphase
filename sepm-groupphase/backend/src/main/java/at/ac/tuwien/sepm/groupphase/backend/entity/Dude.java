package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Dude {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_dude_id")
    @SequenceGenerator(name = "seq_dude_id", sequenceName = "seq_dude_id")
    private Long id;

    @Column(nullable = false, length = 20)
    @Size(min = 8, max = 20)
    private String name;

    @Column(nullable = false, length = 500)
    @Size(max = 500)
    private String description = "No description given.";

    @Column(nullable = false, length = 50)
    @Size(max = 50)
    private String email = "No e-mail given.";

    @Column(nullable = false)
    private Character sex;
    // M = Male; F = Female

    @Column(nullable = false)
    @Min(1) @Max(3)
    private Integer status = 1;
    // 1 = New Dude; 2 = Experienced Dude; 3 = Ancient Dude

    @Column(nullable = false, name = "self_assessment")
    @Min(1) @Max(3)
    private Integer selfAssessment;
    // 1 = Beginner; 2 = Advanced; 3 = Pro

    @Column(nullable = false)
    @Past
    private LocalDate birthday;

    @Column(nullable = false)
    @Min(1)
    private Double height;
    // in centimeters

    @Column(nullable = false)
    @Min(1)
    private Double weight;
    // in kilograms

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "dude_fitness_provider_follows",
        joinColumns = @JoinColumn(name = "dude_id"),
        inverseJoinColumns = @JoinColumn(name = "fitness_provider_id")
    )
    private Set<FitnessProvider> fitnessProviders;

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

    public static DudeBuilder builder() {
        return new DudeBuilder();
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

        if (id != null ? !id.equals(dude.id) : dude.id != null) return false;
        if (name != null ? !name.equals(dude.name) : dude.name != null) return false;
        if (description != null ? !description.equals(dude.description) : dude.description != null) return false;
        if (email != null ? !email.equals(dude.email) : dude.email != null) return false;
        if (sex != null ? !sex.equals(dude.sex) : dude.sex != null) return false;
        if (status != null ? !status.equals(dude.status) : dude.status != null) return false;
        if (selfAssessment != null ? !selfAssessment.equals(dude.selfAssessment) : dude.selfAssessment != null)
            return false;
        if (birthday != null ? !birthday.equals(dude.birthday) : dude.birthday != null) return false;
        if (height != null ? !height.equals(dude.height) : dude.height != null) return false;
        return weight != null ? weight.equals(dude.weight) : dude.weight == null;

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

    public static final class DudeBuilder {
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

        public DudeBuilder() {
        }

        public DudeBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DudeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DudeBuilder description(String description) {
            this.description = description;
            return this;
        }

        public DudeBuilder email(String email) {
            this.email = email;
            return this;
        }

        public DudeBuilder sex(Character sex) {
            this.sex = sex;
            return this;
        }

        public DudeBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public DudeBuilder selfAssessment(Integer selfAssessment) {
            this.selfAssessment = selfAssessment;
            return this;
        }

        public DudeBuilder birthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public DudeBuilder height(Double height) {
            this.height = height;
            return this;
        }

        public DudeBuilder weight(Double weight) {
            this.weight = weight;
            return this;
        }

        public Dude build() {
            Dude dude = new Dude();
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
