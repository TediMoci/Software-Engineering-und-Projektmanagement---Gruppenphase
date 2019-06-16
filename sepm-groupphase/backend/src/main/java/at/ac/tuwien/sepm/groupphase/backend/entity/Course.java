package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_course_id")
    @SequenceGenerator(name = "seq_course_id", sequenceName = "seq_course_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 3000)
    private String description = "No description given.";

    @Column(nullable = false)
    @Min(0)
    private Integer ratingSum = 0;

    @Column(nullable = false)
    @Min(0)
    private Integer ratingNum = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fitness_provider_id")
    private FitnessProvider creator;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "courses")
    private List<Dude> dudes;

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

    public FitnessProvider getCreator() {
        return creator;
    }

    public void setCreator(FitnessProvider creator) {
        this.creator = creator;
    }

    public List<Dude> getDudes() {
        return dudes;
    }

    public void setDudes(List<Dude> dudes) {
        this.dudes = dudes;
    }

    public Integer getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(Integer ratingSum) {
        this.ratingSum = ratingSum;
    }

    public Integer getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(Integer ratingNum) {
        this.ratingNum = ratingNum;
    }

    public static CourseBuilder builder() {
        return new CourseBuilder();
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", ratingSum=" + ratingSum +
            ", ratingNum=" + ratingNum +
            ", creator=" + creator +
            ", dudes=" + dudes +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(getId(), course.getId()) &&
            Objects.equals(getName(), course.getName()) &&
            Objects.equals(getDescription(), course.getDescription()) &&
            Objects.equals(getRatingSum(), course.getRatingSum()) &&
            Objects.equals(getRatingNum(), course.getRatingNum()) &&
            Objects.equals(getCreator(), course.getCreator()) &&
            Objects.equals(getDudes(), course.getDudes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getRatingSum(), getRatingNum(), getCreator(), getDudes());
    }

    public static final class CourseBuilder {
        private Long id;
        private String name;
        private String description;
        private Integer ratingNum;
        private Integer ratingSum;
        private FitnessProvider creator;
        private List<Dude> dudes;

        public CourseBuilder() {
        }

        public CourseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseBuilder creator(FitnessProvider creator) {
            this.creator = creator;
            return this;
        }

        public CourseBuilder ratingNum(Integer ratingNum) {
            this.ratingNum = ratingNum;
            return this;
        }

        public CourseBuilder ratingSum(Integer ratingSum) {
            this.ratingSum = ratingSum;
            return this;
        }

        public CourseBuilder dudes(List<Dude> dudes) {
            this.dudes = dudes;
            return this;
        }

        public Course build() {
            Course course = new Course();
            course.setId(id);
            course.setName(name);
            course.setDescription(description);
            course.setRatingNum(ratingNum);
            course.setRatingNum(ratingSum);
            course.setCreator(creator);
            course.setDudes(dudes);
            return course;
        }
    }
}
