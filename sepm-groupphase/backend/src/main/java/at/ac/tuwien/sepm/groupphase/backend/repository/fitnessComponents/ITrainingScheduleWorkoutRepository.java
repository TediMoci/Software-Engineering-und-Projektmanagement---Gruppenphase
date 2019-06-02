package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.TrainingScheduleWorkoutKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainingScheduleWorkoutRepository extends JpaRepository<TrainingScheduleWorkout, TrainingScheduleWorkoutKey> {
    /**
     * @param trainingScheduleWorkout to be saved in the database
     * @return the saved TrainingScheduleWorkout
     * @throws DataAccessException if an error occurred while trying to save the workoutExercise in the database
     */
    TrainingScheduleWorkout save(TrainingScheduleWorkout trainingScheduleWorkout) throws DataAccessException;

}
