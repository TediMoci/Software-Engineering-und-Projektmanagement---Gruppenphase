package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_exercise_id")
    @SequenceGenerator(name = "seq_exercise_id", sequenceName = "seq_exercise_id")
    private Long id;

}
