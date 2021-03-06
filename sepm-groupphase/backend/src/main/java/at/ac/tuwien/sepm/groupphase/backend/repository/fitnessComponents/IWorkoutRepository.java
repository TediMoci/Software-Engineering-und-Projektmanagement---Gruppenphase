package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutKey;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
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
    @Query("SELECT w FROM Workout w WHERE w.name LIKE ?1% AND w.isHistory=false AND w.isPrivate=false")
    List<Workout> findByName(String name) throws DataAccessException;

    /**
     * @param name of the Workouts to find
     * @param dude that called the method
     * @return Workouts with name beginning with the given name-string
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE w.name LIKE ?1% AND w.isHistory=false AND w.isPrivate=true AND w.creator=?2")
    List<Workout> findOwnPrivateByName(String name, Dude dude) throws DataAccessException;

    /**
     * @return all Workouts in the database
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE w.isHistory=false AND w.isPrivate=false ORDER BY w.id")
    List<Workout> findAll() throws DataAccessException;

    /**
     * @param dude that called the method
     * @return all Workouts in the database
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE w.isHistory=false AND w.isPrivate=true AND w.creator=?1 ORDER BY w.id")
    List<Workout> findOwnPrivate(Dude dude) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param difficulty to be filtered for
     * @param calorieLower lower bound for calorieConsumption
     * @param calorieUpper upper bound for calorieConsumption
     * @return all Workouts in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE (w.name LIKE %?1% OR w.description LIKE %?1%) AND w.difficulty=?2 " +
        "AND w.calorieConsumption>=?3 AND w.calorieConsumption<=?4 AND w.isHistory=false AND w.isPrivate=false")
    List<Workout> findByFilterWithDifficulty(String filter, Integer difficulty, Double calorieLower, Double calorieUpper) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param calorieLower lower bound for calorieConsumption
     * @param calorieUpper upper bound for calorieConsumption
     * @return all Workouts in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE (w.name LIKE %?1% OR w.description LIKE %?1%) AND w.calorieConsumption>=?2 " +
        "AND w.calorieConsumption<=?3 AND w.isHistory=false AND w.isPrivate=false")
    List<Workout> findByFilterWithoutDifficulty(String filter, Double calorieLower, Double calorieUpper) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param difficulty to be filtered for
     * @param calorieLower lower bound for calorieConsumption
     * @param calorieUpper upper bound for calorieConsumption
     * @param dude that called the method
     * @return all Workouts in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE (w.name LIKE %?1% OR w.description LIKE %?1%) AND w.difficulty=?2 " +
        "AND w.calorieConsumption>=?3 AND w.calorieConsumption<=?4 AND w.isHistory=false AND w.isPrivate=true AND w.creator=?5")
    List<Workout> findOwnPrivateByFilterWithDifficulty(String filter, Integer difficulty, Double calorieLower, Double calorieUpper, Dude dude) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param calorieLower lower bound for calorieConsumption
     * @param calorieUpper upper bound for calorieConsumption
     * @param dude that called the method
     * @return all Workouts in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE (w.name LIKE %?1% OR w.description LIKE %?1%) AND w.calorieConsumption>=?2 " +
        "AND w.calorieConsumption<=?3 AND w.isHistory=false AND w.isPrivate=true AND w.creator=?4")
    List<Workout> findOwnPrivateByFilterWithoutDifficulty(String filter, Double calorieLower, Double calorieUpper, Dude dude) throws DataAccessException;

    /**
     * @param id of Workout to be found
     * @return Workout found with the given id
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE w.id=?1 AND w.isHistory=false")
    Workout findById(long id) throws DataAccessException;

    /**
     * @param difficulty of Workouts to be found
     * @param maxCalories maximum amount of calories for workout
     * @return all non-deleted Workouts with the given difficulty and calories amount lower than maxCalories in the database
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE w.difficulty=?1 AND w.calorieConsumption<=?2 AND w.isHistory=false ORDER BY w.id")
    List<Workout> findByDifficulty(Integer difficulty, double maxCalories) throws DataAccessException;

    /**
     * @param difficulty of Workouts to be found
     * @param maxCalories maximum amount of calories for workout
     * @return all non-deleted Workouts in the database with difficulty and calorie amount equal or lower  to the given values
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Query("SELECT w FROM Workout w WHERE w.difficulty<=?1 AND w.calorieConsumption<=?2 AND w.isHistory=false ORDER BY w.id")
    List<Workout> findByLowerDifficulty(Integer difficulty, double maxCalories) throws DataAccessException;

    /**
     * @param myId -> ID of the workout that is updated
     * @param dbId -> ID of the workout generated by the database automatically
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Modifying
    @Transactional
    @Query("UPDATE Workout w SET w.id=:myID WHERE w.id=:dbID AND w.isHistory=false")
    void updateNew(@Param("myID")long myId, @Param("dbID")long dbId) throws DataAccessException;

    /**
     * @param id of Workout to be deleted
     * @throws DataAccessException if an error occurred while trying to find the Workouts in the database
     */
    @Modifying
    @Transactional
    @Query("UPDATE Workout w SET w.isHistory=true WHERE w.id=:id AND w.isHistory=false")
    void delete(@Param("id")long id) throws DataAccessException;

    /**
     * @param id of Workout to delete
     * @throws DataAccessException if an error occured while trying to delete the Workout with given id
     */
    @Modifying
    @Transactional
    void deleteById(Long id) throws DataAccessException;

}
