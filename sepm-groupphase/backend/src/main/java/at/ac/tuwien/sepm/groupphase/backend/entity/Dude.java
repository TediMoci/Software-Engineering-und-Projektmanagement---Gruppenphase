package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.FinishedTrainingScheduleStats;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dude {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_dude_id")
    @SequenceGenerator(name = "seq_dude_id", sequenceName = "seq_dude_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 1000)
    private String description = "No description given.";

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private Sex sex;

    @Column(nullable = false, name = "self_assessment")
    private Integer selfAssessment;
    // 1 = Beginner; 2 = Advanced; 3 = Pro

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private Double height;
    // in centimeters

    @Column(nullable = false)
    private Double weight;
    // in kilograms

    @Column(nullable = false)
    private String imagePath = "http://localhost:8080/downloadImage/kugelfisch.jpg";

    @ElementCollection
    private List<String> roles = new ArrayList<String>() {
        {
            add("DUDE");
        }
    };

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "dude_fitness_provider_follows",
        joinColumns = @JoinColumn(name = "dude_id"),
        inverseJoinColumns = @JoinColumn(name = "fitness_provider_id")
    )
    private List<FitnessProvider> fitnessProviders;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "dude_course_bookmarks",
        joinColumns = @JoinColumn(name = "dude_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Exercise> exercises;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Workout> workouts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    private List<TrainingSchedule> trainingSchedules;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "dude")
    private ActiveTrainingSchedule activeTrainingSchedule;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dude")
    private List<FinishedTrainingScheduleStats> finishedTrainingScheduleStats;

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

    public String getPassword(){ return password; }

    public void setPassword(String password){ this.password = password; }

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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<FitnessProvider> getFitnessProviders() {
        return fitnessProviders;
    }

    public void setFitnessProviders(List<FitnessProvider> fitnessProviders) {
        this.fitnessProviders = fitnessProviders;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public List<TrainingSchedule> getTrainingSchedules() {
        return trainingSchedules;
    }

    public void setTrainingSchedules(List<TrainingSchedule> trainingSchedules) {
        this.trainingSchedules = trainingSchedules;
    }

    public ActiveTrainingSchedule getActiveTrainingSchedule() {
        return activeTrainingSchedule;
    }

    public void setActiveTrainingSchedule(ActiveTrainingSchedule activeTrainingSchedule) {
        this.activeTrainingSchedule = activeTrainingSchedule;
    }

    public List<FinishedTrainingScheduleStats> getFinishedTrainingScheduleStats() {
        return finishedTrainingScheduleStats;
    }

    public void setFinishedTrainingScheduleStats(List<FinishedTrainingScheduleStats> finishedTrainingScheduleStats) {
        this.finishedTrainingScheduleStats = finishedTrainingScheduleStats;
    }

    public static DudeBuilder builder() {
        return new DudeBuilder();
    }

    @Override
    public String toString() {
        return "Dude{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", description='" + description + '\'' +
            ", email='" + email + '\'' +
            ", sex=" + sex +
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
        if (password != null ? !password.equals(dude.password) : dude.password != null) return false;
        if (description != null ? !description.equals(dude.description) : dude.description != null) return false;
        if (email != null ? !email.equals(dude.email) : dude.email != null) return false;
        if (sex != dude.sex) return false;
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
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (selfAssessment != null ? selfAssessment.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    public static final class DudeBuilder {
        private Long id;
        private String name;
        private String password;
        private String description;
        private String email;
        private Sex sex;
        private Integer selfAssessment;
        private LocalDate birthday;
        private Double height;
        private Double weight;
        private String imagePath;
        private List<String> roles;
        private List<FitnessProvider> fitnessProviders;
        private List<Course> courses;
        private List<Exercise> exercises;
        private List<Workout> workouts;
        private List<TrainingSchedule> trainingSchedules;
        private ActiveTrainingSchedule activeTrainingSchedule;
        private List<FinishedTrainingScheduleStats> finishedTrainingScheduleStats;

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

        public DudeBuilder password(String password){
            this.password = password;
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

        public DudeBuilder sex(Sex sex) {
            this.sex = sex;
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

        public DudeBuilder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public DudeBuilder roles(List<String> roles){
            this.roles = roles;
            return this;
        }

        public DudeBuilder fitnessProviders(List<FitnessProvider> fitnessProviders) {
            this.fitnessProviders = fitnessProviders;
            return this;
        }

        public DudeBuilder courses(List<Course> courses) {
            this.courses = courses;
            return this;
        }

        public DudeBuilder exercises(List<Exercise> exercises) {
            this.exercises = exercises;
            return this;
        }

        public DudeBuilder workouts(List<Workout> workouts) {
            this.workouts = workouts;
            return this;
        }

        public DudeBuilder trainingSchedules(List<TrainingSchedule> trainingSchedules) {
            this.trainingSchedules = trainingSchedules;
            return this;
        }

        public DudeBuilder activeTrainingSchedule(ActiveTrainingSchedule activeTrainingSchedule) {
            this.activeTrainingSchedule = activeTrainingSchedule;
            return this;
        }

        public DudeBuilder finishedTrainingScheduleStats(List<FinishedTrainingScheduleStats> finishedTrainingScheduleStats) {
            this.finishedTrainingScheduleStats = finishedTrainingScheduleStats;
            return this;
        }

        public Dude build() {
            Dude dude = new Dude();
            dude.setId(id);
            dude.setName(name);
            dude.setPassword(password);
            dude.setDescription(description);
            dude.setEmail(email);
            dude.setSex(sex);
            dude.setSelfAssessment(selfAssessment);
            dude.setBirthday(birthday);
            dude.setHeight(height);
            dude.setWeight(weight);
            dude.setImagePath(imagePath);
            dude.setRoles(roles);
            dude.setFitnessProviders(fitnessProviders);
            dude.setCourses(courses);
            dude.setExercises(exercises);
            dude.setWorkouts(workouts);
            dude.setTrainingSchedules(trainingSchedules);
            dude.setActiveTrainingSchedule(activeTrainingSchedule);
            dude.setFinishedTrainingScheduleStats(finishedTrainingScheduleStats);
            return dude;
        }
    }

}
