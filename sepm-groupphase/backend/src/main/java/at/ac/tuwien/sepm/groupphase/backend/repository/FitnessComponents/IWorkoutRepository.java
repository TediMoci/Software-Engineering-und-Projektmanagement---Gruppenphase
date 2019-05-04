package at.ac.tuwien.sepm.groupphase.backend.repository.FitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkoutRepository extends JpaRepository<Workout, Long> {
}
