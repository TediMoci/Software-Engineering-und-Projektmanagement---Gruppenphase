package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseRatingKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseRating;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IExerciseRatingRepository extends JpaRepository<ExerciseRating, ExerciseRatingKey> {

    /**
     * @param exerciseRating to be saved in the database
     * @return the saved ExerciseRating
     * @throws DataAccessException if an error occurred while trying to save the exerciseRating in the database
     */
    ExerciseRating save(ExerciseRating exerciseRating) throws DataAccessException;

    /**
     * @param exerciseRating to be deleted from the database
     * @throws DataAccessException if an error occurred while trying to save the exerciseRating in the database
     */
    void delete(ExerciseRating exerciseRating) throws DataAccessException;

    /**
     * @param dudeId of dude
     * @param exerciseId of exercise
     * @return ExerciseRating found with the given dude and exercise IDs
     */
    @Query("SELECT e FROM ExerciseRating e WHERE e.dudeId=?1 AND e.exerciseId=?2")
    ExerciseRating findRating(Long dudeId, Long exerciseId);
}

