package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExerciseRepository extends JpaRepository<Exercise, Long> {

    /**
     * @param exercise to be saved in the database
     * @return the saved Exercise
     * @throws DataAccessException if an error occured while trying to save the Exercise in the databse
     */
    Exercise save(Exercise exercise) throws DataAccessException;

    /**
     * @param name of the Exercises to find
     * @return Exercises with name beginning with the given name-string
     * @throws DataAccessException if an error occured while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE e.name LIKE ?1% AND e.isHistory=false")
    List<Exercise> findByName(String name) throws DataAccessException;

    /**
     * @return all Exercises in the database
     * @throws DataAccessException if an error occured while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE e.isHistory=false")
    List<Exercise> findAll() throws DataAccessException;

}
