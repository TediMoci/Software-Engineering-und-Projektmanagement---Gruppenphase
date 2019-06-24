package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.TrainingScheduleWorkoutKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ITrainingScheduleWorkoutRepository extends JpaRepository<TrainingScheduleWorkout, TrainingScheduleWorkoutKey> {
    /**
     * @param trainingScheduleWorkout to be saved in the database
     * @return the saved TrainingScheduleWorkout
     * @throws DataAccessException if an error occurred while trying to save the workoutExercise in the database
     */
    TrainingScheduleWorkout save(TrainingScheduleWorkout trainingScheduleWorkout) throws DataAccessException;

    /**
     * @param trainingSchedule which uses sought for trainingScheduleWorkouts
     * @return List of TrainingScheduleWorkouts used in the given TrainingSchedule
     * @throws DataAccessException if an error occurred while trying to find all TrainingScheduleWorkouts in the given trainingScheduleWorkout
     */
    List<TrainingScheduleWorkout> findByTrainingSchedule(TrainingSchedule trainingSchedule) throws DataAccessException;

    /**
     * @param workout to delete
     * @throws DataAccessException if an error occurred while trying to delete the given TrainingScheduleWorkout
     */
    @Modifying
    @Transactional
    void delete(TrainingScheduleWorkout workout) throws DataAccessException;

    /**
     * @param id of training Schedule
     * @param version of training Schedule
     * @param day of workouts
     * @return List of TrainingScheduleWorkouts used in the given TrainingSchedule in the given day
     * @throws DataAccessException if an error occurred while trying to find all TrainingScheduleWorkouts in the given trainingScheduleWorkout
     */
    @Query("SELECT t FROM TrainingScheduleWorkout t WHERE t.trainingScheduleId=?1 AND t.trainingScheduleVersion=?2 AND t.day=?3")
    List<TrainingScheduleWorkout> findByTrainingScheduleIdVersionAndDay(Long id, Integer version, Integer day) throws DataAccessException;
}
