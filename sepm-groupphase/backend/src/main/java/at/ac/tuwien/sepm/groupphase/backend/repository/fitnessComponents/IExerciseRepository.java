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
     * @param exercise
     * @return
     */
    Exercise save(Exercise exercise) throws DataAccessException;

    /**
     * @param name
     * @return
     * @throws DataAccessException
     */
    @Query("SELECT e FROM Exercise e WHERE e.name LIKE ?1%")
    List<Exercise> findByName(String name) throws DataAccessException;

    /**
     * @return
     * @throws DataAccessException
     */
    List<Exercise> findAll() throws DataAccessException;

}
