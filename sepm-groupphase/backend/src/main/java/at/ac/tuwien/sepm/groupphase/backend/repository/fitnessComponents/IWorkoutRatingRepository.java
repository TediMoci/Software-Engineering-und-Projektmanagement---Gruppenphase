package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutRatingKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutRating;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkoutRatingRepository extends JpaRepository<WorkoutRating, WorkoutRatingKey> {

    /**
     * @param workoutRating to be saved in the database
     * @return the saved WorkoutRating
     * @throws DataAccessException if an error occurred while trying to save the WorkoutRating in the database
     */
    WorkoutRating save(WorkoutRating workoutRating) throws DataAccessException;

    /**
     * @param workoutRating to be deleted from the database
     * @throws DataAccessException if an error occurred while trying to save the workoutRating in the database
     */
    void delete(WorkoutRating workoutRating) throws DataAccessException;

    /**
     * @param dudeId of dude
     * @param workoutId of workout
     * @return WorkoutRating found with the given dude and workout IDs
     */
    @Query("SELECT w FROM WorkoutRating w WHERE w.dudeId=?1 AND w.workoutId=?2")
    WorkoutRating findRating(Long dudeId, Long workoutId);
}
