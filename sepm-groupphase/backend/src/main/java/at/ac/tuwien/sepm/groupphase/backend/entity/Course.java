package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import java.util.List;

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

    public static CourseBuilder builder() {
        return new CourseBuilder();
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", creator=" + creator +
            ", dudes=" + dudes +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != null ? !id.equals(course.id) : course.id != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (creator != null ? !creator.equals(course.creator) : course.creator != null) return false;
        return dudes != null ? dudes.equals(course.dudes) : course.dudes == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (dudes != null ? dudes.hashCode() : 0);
        return result;
    }

    public static final class CourseBuilder {
        private Long id;
        private String name;
        private String description;
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

        public CourseBuilder dudes(List<Dude> dudes) {
            this.dudes = dudes;
            return this;
        }

        public Course build() {
            Course course = new Course();
            course.setId(id);
            course.setName(name);
            course.setDescription(description);
            course.setCreator(creator);
            course.setDudes(dudes);
            return course;
        }
    }
}
