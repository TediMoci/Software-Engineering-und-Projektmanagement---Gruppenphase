package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_workout_id")
    @SequenceGenerator(name = "seq_workout_id", sequenceName = "seq_workout_id")
    private Long id;

    @Column(nullable = false, length = 50)
    @Size(min = 1, max = 50)
    private String name;

    @Column(nullable = false, length = 1000)
    @Size(max = 1000)
    private String description = "No description given.";

    @Column(nullable = false)
    @Min(1) @Max(3)
    private Integer difficulty;
    // TODO: selfAssessment enum

    @Column(nullable = false)
    @Min(1) @Max(5)
    private Double rating = 1.0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dude_id")
    private Dude creator;

}
