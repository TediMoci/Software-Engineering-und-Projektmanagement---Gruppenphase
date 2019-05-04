package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "training_schedule")
public class TrainingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_training_schedule_id")
    @SequenceGenerator(name = "seq_training_schedule_id", sequenceName = "seq_training_schedule_id")
    private Long id;

}
