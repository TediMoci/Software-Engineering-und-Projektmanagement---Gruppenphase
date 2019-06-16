package at.ac.tuwien.sepm.groupphase.backend.entity.relationships;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.CourseRatingKey;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "course_rating")
@IdClass(CourseRatingKey.class)
public class CourseRating {

    @Id
    @Column(name = "dude_id")
    private Long dudeId;

    @Id
    @Column(name = "course_id")
    private Long courseId;

    @Column(nullable = false, name = "rating")
    @Min(1)
    private Integer rating;

    public Long getDudeId() {
        return dudeId;
    }

    public void setDudeId(Long dudeId) {
        this.dudeId = dudeId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRating that = (CourseRating) o;
        return Objects.equals(getDudeId(), that.getDudeId()) &&
            Objects.equals(getCourseId(), that.getCourseId()) &&
            Objects.equals(getRating(), that.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDudeId(), getCourseId(), getRating());
    }

    @Override
    public String toString() {
        return "CourseRating{" +
            "dudeId=" + dudeId +
            ", courseId=" + courseId +
            ", rating=" + rating +
            '}';
    }
}
