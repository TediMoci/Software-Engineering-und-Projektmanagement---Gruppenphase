package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;

import javax.persistence.*;
import java.util.List;

@Entity
@IdClass(WorkoutKey.class)
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_workout_id")
    @SequenceGenerator(name = "seq_workout_id", sequenceName = "seq_workout_id")
    private Long id;

    @Id
    private Integer version = 1;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 3000)
    private String description = "No description given.";

    @Column(nullable = false)
    private Integer difficulty;
    // TODO: selfAssessment enum

    @Column(nullable = false, name = "calorie_consumption")
    private Double calorieConsumption = 0.0;

    @Column(nullable = false)
    private Double rating = 1.0;

    @Column(nullable = false, name = "is_history")
    private Boolean isHistory = false;

    @Column(nullable = false, name = "is_private")
    private Boolean isPrivate = false;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "workout")
    private List<WorkoutExercise> exercises;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "workout")
    private List<TrainingScheduleWorkout> trainingSchedules;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dude_id")
    private Dude creator;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "workoutBookmarks")
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

    public Double getCalorieConsumption() {
        return calorieConsumption;
    }

    public void setCalorieConsumption(Double calorieConsumption) {
        this.calorieConsumption = calorieConsumption;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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

    public List<WorkoutExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }

    public List<TrainingScheduleWorkout> getTrainingSchedules() {
        return trainingSchedules;
    }

    public void setTrainingSchedules(List<TrainingScheduleWorkout> trainingSchedules) {
        this.trainingSchedules = trainingSchedules;
    }

    public Dude getCreator() {
        return creator;
    }

    public void setCreator(Dude creator) {
        this.creator = creator;
    }

    public List<Dude> getBookmarkDudes() {
        return bookmarkDudes;
    }

    public void setBookmarkDudes(List<Dude> bookmarkDudes) {
        this.bookmarkDudes = bookmarkDudes;
    }

    public static WorkoutBuilder builder() {
        return new WorkoutBuilder();
    }

    @Override
    public String toString() {
        return "Workout{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", difficulty=" + difficulty +
            ", calorieConsumption=" + calorieConsumption +
            ", rating=" + rating +
            ", isHistory=" + isHistory +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Workout workout = (Workout) o;

        if (id != null ? !id.equals(workout.id) : workout.id != null) return false;
        if (version != null ? !version.equals(workout.version) : workout.version != null) return false;
        if (name != null ? !name.equals(workout.name) : workout.name != null) return false;
        if (description != null ? !description.equals(workout.description) : workout.description != null) return false;
        if (difficulty != null ? !difficulty.equals(workout.difficulty) : workout.difficulty != null) return false;
        if (calorieConsumption != null ? !calorieConsumption.equals(workout.calorieConsumption) : workout.calorieConsumption != null)
            return false;
        if (rating != null ? !rating.equals(workout.rating) : workout.rating != null) return false;
        return isHistory != null ? isHistory.equals(workout.isHistory) : workout.isHistory == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (calorieConsumption != null ? calorieConsumption.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (isHistory != null ? isHistory.hashCode() : 0);
        return result;
    }

    public static final class WorkoutBuilder {
        private Long id;
        private Integer version;
        private String name;
        private String description;
        private Integer difficulty;
        private Double calorieConsumption;
        private Double rating;
        private Boolean isHistory;
        private Boolean isPrivate;
        private List<WorkoutExercise> exercises;
        private List<TrainingScheduleWorkout> trainingSchedules;
        private Dude creator;
        private List<Dude> bookmarkDudes;

        public WorkoutBuilder() {
        }

        public WorkoutBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public WorkoutBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public WorkoutBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WorkoutBuilder description(String description) {
            this.description = description;
            return this;
        }

        public WorkoutBuilder difficulty(Integer difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public WorkoutBuilder calorieConsumption(Double calorieConsumption) {
            this.calorieConsumption = calorieConsumption;
            return this;
        }

        public WorkoutBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public WorkoutBuilder isHistory(Boolean isHistory) {
            this.isHistory = isHistory;
            return this;
        }

        public WorkoutBuilder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public WorkoutBuilder exercises(List<WorkoutExercise> exercises) {
            this.exercises = exercises;
            return this;
        }

        public WorkoutBuilder trainingSchedules(List<TrainingScheduleWorkout> trainingSchedules) {
            this.trainingSchedules = trainingSchedules;
            return this;
        }

        public WorkoutBuilder creator(Dude creator) {
            this.creator = creator;
            return this;
        }

        public WorkoutBuilder bookmarkDudes(List<Dude> bookmarkDudes) {
            this.bookmarkDudes = bookmarkDudes;
            return this;
        }

        public Workout build() {
            Workout workout = new Workout();
            workout.setId(id);
            workout.setVersion(version);
            workout.setName(name);
            workout.setDescription(description);
            workout.setDifficulty(difficulty);
            workout.setCalorieConsumption(calorieConsumption);
            workout.setRating(rating);
            workout.setHistory(isHistory);
            workout.setIsPrivate(isPrivate);
            workout.setExercises(exercises);
            workout.setTrainingSchedules(trainingSchedules);
            workout.setCreator(creator);
            workout.setBookmarkDudes(bookmarkDudes);
            return workout;
        }
    }
}
