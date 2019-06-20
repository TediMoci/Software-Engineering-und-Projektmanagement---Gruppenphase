package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.TrainingScheduleKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "training_schedule")
@IdClass(TrainingScheduleKey.class)
public class TrainingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_training_schedule_id")
    @SequenceGenerator(name = "seq_training_schedule_id", sequenceName = "seq_training_schedule_id")
    private Long id;

    @Id
    private Integer version = 1;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 3000)
    private String description = "No description given.";

    @Column(nullable = false)
    private Integer difficulty;

    @Column(nullable = false, name = "interval_length")
    private Integer intervalLength;

    @Column(nullable = false)
    @Min(0)
    private Integer ratingSum = 0;

    @Column(nullable = false)
    @Min(0)
    private Integer ratingNum = 0;

    @Column(nullable = false, name = "is_history")
    private Boolean isHistory = false;

    @Column(nullable = false, name = "is_private")
    private Boolean isPrivate = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "trainingSchedule")
    private List<TrainingScheduleWorkout> workouts;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dude_id")
    private Dude creator;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "trainingSchedule")
    private List<ActiveTrainingSchedule> activeUsages;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "trainingScheduleBookmarks")
    private List<Dude> bookmarkDudes;

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

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getIntervalLength() {
        return intervalLength;
    }

    public void setIntervalLength(Integer intervalLength) {
        this.intervalLength = intervalLength;
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

    public Boolean getHistory() {
        return isHistory;
    }

    public void setHistory(Boolean history) {
        isHistory = history;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public List<TrainingScheduleWorkout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<TrainingScheduleWorkout> workouts) {
        this.workouts = workouts;
    }

    public Dude getCreator() {
        return creator;
    }

    public void setCreator(Dude creator) {
        this.creator = creator;
    }

    public List<ActiveTrainingSchedule> getActiveUsages() {
        return activeUsages;
    }

    public void setActiveUsages(List<ActiveTrainingSchedule> activeUsages) {
        this.activeUsages = activeUsages;
    }

    public List<Dude> getBookmarkDudes() {
        return bookmarkDudes;
    }

    public void setBookmarkDudes(List<Dude> bookmarkDudes) {
        this.bookmarkDudes = bookmarkDudes;
    }

    public static TrainingScheduleBuilder builder() {
        return new TrainingScheduleBuilder();
    }

    @Override
    public String toString() {
        return "TrainingSchedule{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", difficulty=" + difficulty +
            ", intervalLength=" + intervalLength +
            ", ratingNum=" + ratingNum +
            ", ratingSum=" + ratingSum +
            ", isHistory=" + isHistory +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingSchedule that = (TrainingSchedule) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (difficulty != null ? !difficulty.equals(that.difficulty) : that.difficulty != null) return false;
        if (intervalLength != null ? !intervalLength.equals(that.intervalLength) : that.intervalLength != null)
            return false;
        if (ratingNum != null ? !ratingNum.equals(that.ratingNum) : that.ratingNum != null) return false;
        if (ratingSum != null ? !ratingSum.equals(that.ratingSum) : that.ratingSum != null) return false;
        return isHistory != null ? isHistory.equals(that.isHistory) : that.isHistory == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (intervalLength != null ? intervalLength.hashCode() : 0);
        result = 31 * result + (ratingNum != null ? ratingNum.hashCode() : 0);
        result = 31 * result + (ratingSum != null ? ratingSum.hashCode() : 0);
        result = 31 * result + (isHistory != null ? isHistory.hashCode() : 0);
        return result;
    }

    public static final class TrainingScheduleBuilder {
        private Long id;
        private Integer version;
        private String name;
        private String description;
        private Integer difficulty;
        private Integer intervalLength;
        private Integer ratingNum;
        private Integer ratingSum;
        private Boolean isHistory;
        private Boolean isPrivate;
        private List<TrainingScheduleWorkout> workouts;
        private Dude creator;
        private List<ActiveTrainingSchedule> activeUsages;
        private List<Dude> bookmarkDudes;

        public TrainingScheduleBuilder() {
        }

        public TrainingScheduleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TrainingScheduleBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public TrainingScheduleBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TrainingScheduleBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TrainingScheduleBuilder difficulty(Integer difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public TrainingScheduleBuilder intervalLength(Integer intervalLength) {
            this.intervalLength = intervalLength;
            return this;
        }

        public TrainingScheduleBuilder ratingNum(Integer ratingNum) {
            this.ratingNum = ratingNum;
            return this;
        }

        public TrainingScheduleBuilder ratingSum(Integer ratingSum) {
            this.ratingSum = ratingSum;
            return this;
        }

        public TrainingScheduleBuilder isHistory(Boolean isHistory) {
            this.isHistory = isHistory;
            return this;
        }

        public TrainingScheduleBuilder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public TrainingScheduleBuilder workouts(List<TrainingScheduleWorkout> workouts) {
            this.workouts = workouts;
            return this;
        }

        public TrainingScheduleBuilder creator(Dude creator) {
            this.creator = creator;
            return this;
        }

        public TrainingScheduleBuilder activeUsages(List<ActiveTrainingSchedule> activeUsages) {
            this.activeUsages = activeUsages;
            return this;
        }

        public TrainingScheduleBuilder bookmarkDudes(List<Dude> bookmarkDudes) {
            this.bookmarkDudes = bookmarkDudes;
            return this;
        }

        public TrainingSchedule build() {
            TrainingSchedule trainingSchedule = new TrainingSchedule();
            trainingSchedule.setId(id);
            trainingSchedule.setName(name);
            trainingSchedule.setDescription(description);
            trainingSchedule.setDifficulty(difficulty);
            trainingSchedule.setIntervalLength(intervalLength);
            trainingSchedule.setRatingNum(ratingNum);
            trainingSchedule.setRatingNum(ratingSum);
            trainingSchedule.setHistory(isHistory);
            trainingSchedule.setIsPrivate(isPrivate);
            trainingSchedule.setWorkouts(workouts);
            trainingSchedule.setCreator(creator);
            trainingSchedule.setActiveUsages(activeUsages);
            trainingSchedule.setBookmarkDudes(bookmarkDudes);
            return trainingSchedule;
        }
    }
}
