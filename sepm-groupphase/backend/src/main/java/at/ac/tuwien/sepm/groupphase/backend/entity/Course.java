package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_course_id")
    @SequenceGenerator(name = "seq_course_id", sequenceName = "seq_course_id")
    private Long id;

}
