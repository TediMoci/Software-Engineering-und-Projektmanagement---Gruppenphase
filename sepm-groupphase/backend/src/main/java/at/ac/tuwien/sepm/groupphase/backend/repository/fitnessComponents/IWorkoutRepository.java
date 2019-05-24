package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutKey;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface IWorkoutRepository extends JpaRepository<Workout, WorkoutKey> {

    /**
     * @param workout to be saved in the database
     * @return the saved Workout
     * @throws DataAccessException if an error occurred while trying to save the Workout in the database
     */
    Workout save(Workout workout) throws DataAccessException;

    /**
     * @param id of the Workout to find
     * @param version of the Workout to find
     * @return the Workout with the given id and version
     * @throws NoSuchElementException if the Workout could not be found in the database
     */
    Optional<Workout> findByIdAndVersion(Long id, Integer version) throws NoSuchElementException;

    /**
     * @param name of the Workouts to find
     * @return Workouts with name beginning with the given name-string
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE w.name LIKE ?1% AND w.isHistory=false")
    List<Workout> findByName(String name) throws DataAccessException;

    /**
     * @return all Workouts in the database
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE w.isHistory=false ORDER BY w.id")
    List<Workout> findAll() throws DataAccessException;

    @Query("SELECT w FROM Workout w WHERE w.id=?1 AND w.isHistory=false")
    Workout findById(long id);

    @Modifying
    @Transactional
    @Query("UPDATE Workout w SET w.id=:myID WHERE w.id=:dbID AND w.isHistory=false")
    void updateNew(@Param("myID")long myId, @Param("dbID")long dbId);

    @Modifying
    @Transactional
    @Query("UPDATE Workout w SET w.isHistory=true WHERE w.id=:id AND w.isHistory=false")
    void delete(@Param("id")long id);

}
