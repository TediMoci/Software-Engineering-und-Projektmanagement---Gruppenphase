package at.ac.tuwien.sepm.groupphase.backend.entity;

import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_exercise_id")
    @SequenceGenerator(name = "seq_exercise_id", sequenceName = "seq_exercise_id")
    private Long id;

    @Column(nullable = false, length = 50)
    @Size(min = 1, max = 50)
    private String name;

    @Column(nullable = false, length = 1000)
    @Size(max = 1000)
    private String description = "No description given.";

    @Column(nullable = false, length = 300)
    @Size(max = 300)
    private String equipment = "No needed equipment given.";

    @Column(nullable = false, length = 100, name = "muscle_group")
    @Size(max = 100)
    private String muscleGroup = "No muscle group given";

    @Column(nullable = false)
    @Min(1) @Max(5)
    private Double rating = 1.0;

    @Column(nullable = false)
    @NotNull
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dude_id")
    private Dude creator;

}
