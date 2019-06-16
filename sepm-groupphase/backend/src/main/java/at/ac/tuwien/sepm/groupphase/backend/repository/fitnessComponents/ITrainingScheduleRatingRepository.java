package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.TrainingScheduleRatingKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleRating;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITrainingScheduleRatingRepository extends JpaRepository<TrainingScheduleRating, TrainingScheduleRatingKey> {

    /**
     * @param trainingScheduleRating to be saved in the database
     * @return the saved TrainingScheduleRating
     * @throws DataAccessException if an error occurred while trying to save the TrainingScheduleRating in the database
     */
    TrainingScheduleRating save(TrainingScheduleRating trainingScheduleRating) throws DataAccessException;

    /**
     * @param trainingScheduleRating to be deleted from the database
     * @throws DataAccessException if an error occurred while trying to save the TrainingScheduleRating in the database
     */
    void delete(TrainingScheduleRating trainingScheduleRating) throws DataAccessException;

    /**
     * @param dudeId of dude
     * @param trainingScheduleId of training schedule
     * @return TrainingScheduleRating found with the given dude and training schedule IDs
     */
    @Query("SELECT t FROM TrainingScheduleRating t WHERE t.dudeId=?1 AND t.trainingScheduleId=?2")
    TrainingScheduleRating findRating(Long dudeId, Long trainingScheduleId);
}
