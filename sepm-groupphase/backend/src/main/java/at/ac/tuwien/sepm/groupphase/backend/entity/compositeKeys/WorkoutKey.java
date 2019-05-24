package at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys;

import java.io.Serializable;

public class WorkoutKey implements Serializable {

    private Long id;
    private Integer version;

    public WorkoutKey() {
    }

    public WorkoutKey(Long id, Integer version) {
        this.id = id;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "WorkoutKey{" +
            "id=" + id +
            ", version=" + version +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutKey that = (WorkoutKey) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
