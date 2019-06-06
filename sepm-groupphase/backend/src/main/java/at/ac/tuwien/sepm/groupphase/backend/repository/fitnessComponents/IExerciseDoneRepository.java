package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseDoneKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IExerciseDoneRepository extends JpaRepository<ExerciseDone, ExerciseDoneKey> {

    /**
     * @param exerciseDone to be saved in the database
     * @return the saved ExerciseDone
     * @throws DataAccessException if an error occurred while trying to save the ExerciseDone in the database
     */
    ExerciseDone save(ExerciseDone exerciseDone) throws DataAccessException;

}