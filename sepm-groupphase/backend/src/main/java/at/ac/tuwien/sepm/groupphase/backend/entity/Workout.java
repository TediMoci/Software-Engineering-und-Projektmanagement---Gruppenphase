package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_workout_id")
    @SequenceGenerator(name = "seq_workout_id", sequenceName = "seq_workout_id")
    private Long id;

}
