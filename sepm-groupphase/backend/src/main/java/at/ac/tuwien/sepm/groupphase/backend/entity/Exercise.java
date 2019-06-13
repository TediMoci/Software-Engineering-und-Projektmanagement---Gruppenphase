package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@IdClass(ExerciseKey.class)
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_exercise_id")
    @SequenceGenerator(name = "seq_exercise_id", sequenceName = "seq_exercise_id")
    private Long id;

    @Id
    private Integer version = 1;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 3000)
    private String description = "No description given.";

    @Column(nullable = false, length = 300)
    private String equipment = "No needed equipment given.";

    @Column(nullable = false, length = 100, name = "muscle_group")
    private String muscleGroup = "No muscle group given";

    @Column(nullable = false)
    private Double rating = 1.0;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, name = "is_history")
    private Boolean isHistory = false;

    @Column(nullable = false)
    private String imagePath = "/assets/img/exercise.png";

    @Column(nullable = false, name = "is_private")
    private Boolean isPrivate = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "exercise")
    private Set<WorkoutExercise> workouts;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dude_id")
    private Dude creator;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "exerciseBookmarks")
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Set<WorkoutExercise> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Set<WorkoutExercise> workouts) {
        this.workouts = workouts;
    }

    public Dude getCreator() {
        return creator;
    }

    public void setCreator(Dude creator) {
        this.creator = creator;
    }

    public Boolean getHistory() {
        return isHistory;
    }

    public void setHistory(Boolean history) {
        isHistory = history;
    }

    public List<Dude> getBookmarkDudes() {
        return bookmarkDudes;
    }

    public void setBookmarkDudes(List<Dude> bookmarkDudes) {
        this.bookmarkDudes = bookmarkDudes;
    }

    public static ExerciseBuilder builder() {
        return new ExerciseBuilder();
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", equipment='" + equipment + '\'' +
            ", muscleGroup='" + muscleGroup + '\'' +
            ", rating=" + rating +
            ", category=" + category +
            ", isHistory=" + isHistory +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (id != null ? !id.equals(exercise.id) : exercise.id != null) return false;
        if (version != null ? !version.equals(exercise.version) : exercise.version != null) return false;
        if (name != null ? !name.equals(exercise.name) : exercise.name != null) return false;
        if (description != null ? !description.equals(exercise.description) : exercise.description != null)
            return false;
        if (equipment != null ? !equipment.equals(exercise.equipment) : exercise.equipment != null) return false;
        if (muscleGroup != null ? !muscleGroup.equals(exercise.muscleGroup) : exercise.muscleGroup != null)
            return false;
        if (rating != null ? !rating.equals(exercise.rating) : exercise.rating != null) return false;
        if (category != exercise.category) return false;
        return isHistory != null ? isHistory.equals(exercise.isHistory) : exercise.isHistory == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (equipment != null ? equipment.hashCode() : 0);
        result = 31 * result + (muscleGroup != null ? muscleGroup.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (isHistory != null ? isHistory.hashCode() : 0);
        return result;
    }

    public static final class ExerciseBuilder {
        private Long id;
        private Integer version;
        private String name;
        private String description;
        private String equipment;
        private String muscleGroup;
        private Double rating;
        private Category category;
        private Boolean isHistory;
        private String imagePath;
        private Boolean isPrivate;
        private Set<WorkoutExercise> workouts;
        private Dude creator;
        private List<Dude> bookmarkDudes;

        public ExerciseBuilder() {
        }

        public ExerciseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ExerciseBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public ExerciseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ExerciseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ExerciseBuilder equipment(String equipment) {
            this.equipment = equipment;
            return this;
        }

        public ExerciseBuilder muscleGroup(String muscleGroup) {
            this.muscleGroup = muscleGroup;
            return this;
        }

        public ExerciseBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public ExerciseBuilder category(Category category) {
            this.category = category;
            return this;
        }


        public ExerciseBuilder isHistory(Boolean isHistory) {
            this.isHistory = isHistory;
            return this;
        }

        public ExerciseBuilder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public ExerciseBuilder isPrivate(Boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public ExerciseBuilder workouts(Set<WorkoutExercise> workouts) {
            this.workouts = workouts;
            return this;
        }

        public ExerciseBuilder creator(Dude creator) {
            this.creator = creator;
            return this;
        }

        public ExerciseBuilder bookmarkDudes(List<Dude> bookmarkDudes) {
            this.bookmarkDudes = bookmarkDudes;
            return this;
        }

        public Exercise build() {
            Exercise exercise = new Exercise();
            exercise.setId(id);
            exercise.setVersion(version);
            exercise.setName(name);
            exercise.setDescription(description);
            exercise.setEquipment(equipment);
            exercise.setMuscleGroup(muscleGroup);
            exercise.setRating(rating);
            exercise.setCategory(category);
            exercise.setHistory(isHistory);
            exercise.setImagePath(imagePath);
            exercise.setIsPrivate(isPrivate);
            exercise.setWorkouts(workouts);
            exercise.setCreator(creator);
            exercise.setBookmarkDudes(bookmarkDudes);
            return exercise;
        }
    }

}
