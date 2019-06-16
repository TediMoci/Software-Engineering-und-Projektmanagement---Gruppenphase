package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import java.io.Serializable;
import java.util.Objects;

public class CourseRatingKey implements Serializable {

    private Long dudeId;
    private Long courseId;

    public CourseRatingKey() {
    }

    public CourseRatingKey(Long dudeId, Long courseId) {
        this.dudeId = dudeId;
        this.courseId = courseId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRatingKey that = (CourseRatingKey) o;
        return Objects.equals(getDudeId(), that.getDudeId()) &&
            Objects.equals(getCourseId(), that.getCourseId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDudeId(), getCourseId());
    }

    @Override
    public String toString() {
        return "CourseRatingKey{" +
            "dudeId=" + dudeId +
            ", courseId=" + courseId +
            '}';
    }
}
