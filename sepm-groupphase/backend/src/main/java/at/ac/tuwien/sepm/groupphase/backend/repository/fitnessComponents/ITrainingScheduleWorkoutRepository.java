package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.TrainingScheduleWorkoutKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainingScheduleWorkoutRepository extends JpaRepository<TrainingScheduleWorkout, TrainingScheduleWorkoutKey> {
    //TODO: log
    TrainingScheduleWorkout save(TrainingScheduleWorkout trainingScheduleWorkout) throws DataAccessException;

}
