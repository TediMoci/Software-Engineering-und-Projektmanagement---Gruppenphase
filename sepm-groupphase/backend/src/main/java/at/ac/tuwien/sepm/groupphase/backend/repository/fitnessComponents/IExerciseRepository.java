package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseKey;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.MuscleGroup;
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
public interface IExerciseRepository extends JpaRepository<Exercise, ExerciseKey> {

    /**
     * @param exercise to be saved in the database
     * @return the saved Exercise
     * @throws DataAccessException if an error occurred while trying to save the Exercise in the database
     */
    Exercise save(Exercise exercise) throws DataAccessException;

    /**
     * @param id of the Exercise to find
     * @param version of the Exercise to find
     * @return the Exercise with the given id and version
     * @throws NoSuchElementException if the Exercise could not be found in the database
     */
    Optional<Exercise> findByIdAndVersion(Long id, Integer version) throws NoSuchElementException;

    /**
     * @param name of the Exercises to find
     * @return Exercises with name beginning with the given name-string
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE e.name LIKE ?1% AND e.isHistory=false AND e.isPrivate=false")
    List<Exercise> findByName(String name) throws DataAccessException;

    /**
     * @param name of the Exercises to find
     * @param dude that called the method
     * @return Exercises with name beginning with the given name-string
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE e.name LIKE ?1% AND e.isHistory=false AND e.isPrivate=true AND e.creator=?2")
    List<Exercise> findOwnPrivateByName(String name, Dude dude) throws DataAccessException;

    /**
     * @return all Exercises in the database
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE e.isHistory=false AND e.isPrivate=false ORDER BY e.id")
    List<Exercise> findAll() throws DataAccessException;

    /**
     * @param dude that called the method
     * @return all Exercises in the database
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE e.isHistory=false AND e.isPrivate=true AND e.creator=?1 ORDER BY e.id")
    List<Exercise> findOwnPrivate(Dude dude) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param category to be filtered for
     * @param muscleGroup to be filtered for
     * @return all Exercises in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE (e.name LIKE %?1% OR e.description LIKE %?1% OR e.equipment LIKE %?1%)" +
        " AND e.muscleGroup=?2 AND e.category=?3 AND e.isHistory=false AND e.isPrivate=false")
    List<Exercise> findByFilterWithMuscleGroupAndWithCategory(String filter, MuscleGroup muscleGroup, Category category) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param muscleGroup to be filtered for
     * @return all Exercises in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE (e.name LIKE %?1% OR e.description LIKE %?1% OR e.equipment LIKE %?1%)" +
        " AND e.muscleGroup=?2 AND e.isHistory=false AND e.isPrivate=false")
    List<Exercise> findByFilterWithMuscleGroupAndWithoutCategory(String filter, MuscleGroup muscleGroup) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param category to be filtered for
     * @return all Exercises in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE (e.name LIKE %?1% OR e.description LIKE %?1% OR e.equipment LIKE %?1%)" +
        " AND e.category=?2 AND e.isHistory=false AND e.isPrivate=false")
    List<Exercise> findByFilterWithoutMuscleGroupAndWithCategory(String filter, Category category) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @return all Exercises in the database according to the given filter
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE (e.name LIKE %?1% OR e.description LIKE %?1% OR e.equipment LIKE %?1%)" +
        " AND e.isHistory=false AND e.isPrivate=false")
    List<Exercise> findByFilterWithoutMuscleGroupAndWithoutCategory(String filter) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param category to be filtered for
     * @param muscleGroup to be filtered for
     * @param dude that called the method
     * @return all Exercises in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE (e.name LIKE %?1% OR e.description LIKE %?1% OR e.equipment LIKE %?1%)" +
        " AND e.muscleGroup=?2 AND e.category=?3 AND e.isHistory=false AND e.isPrivate=true AND e.creator=?4")
    List<Exercise> findOwnPrivateByFilterWithMuscleGroupAndWithCategory(String filter, MuscleGroup muscleGroup, Category category, Dude dude) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param muscleGroup to be filtered for
     * @param dude that called the method
     * @return all Exercises in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE (e.name LIKE %?1% OR e.description LIKE %?1% OR e.equipment LIKE %?1%)" +
        " AND e.muscleGroup=?2 AND e.isHistory=false AND e.isPrivate=true AND e.creator=?3")
    List<Exercise> findOwnPrivateByFilterWithMuscleGroupAndWithoutCategory(String filter, MuscleGroup muscleGroup, Dude dude) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param category to be filtered for
     * @param dude that called the method
     * @return all Exercises in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE (e.name LIKE %?1% OR e.description LIKE %?1% OR e.equipment LIKE %?1%)" +
        " AND e.category=?2 AND e.isHistory=false AND e.isPrivate=true AND e.creator=?3")
    List<Exercise> findOwnPrivateByFilterWithoutMuscleGroupAndWithCategory(String filter, Category category, Dude dude) throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param dude that called the method
     * @return all Exercises in the database according to the given filter
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE (e.name LIKE %?1% OR e.description LIKE %?1% OR e.equipment LIKE %?1%)" +
        " AND e.isHistory=false AND e.isPrivate=true AND e.creator=?2")
    List<Exercise> findOwnPrivateByFilterWithoutMuscleGroupAndWithoutCategory(String filter, Dude dude) throws DataAccessException;

    /**
     * @param id of Exercise to be found
     * @return Exercise found with the given id
     */
    @Query("SELECT e FROM Exercise e WHERE e.id=?1 AND e.isHistory=false")
    Exercise findById(long id);

    /**
     * @param myId -> ID of the exercise that is updated
     * @param dbId -> ID of the exercise generated by the database automatically
     */
    @Modifying
    @Transactional
    @Query("UPDATE Exercise e SET e.id=:myID WHERE e.id=:dbID AND e.isHistory=false")
    void updateNew(@Param("myID")long myId,@Param("dbID")long dbId);

    /**
     * @param id of Exercise to be deleted
     */
    @Modifying
    @Transactional
    @Query("UPDATE Exercise e SET e.isHistory=true WHERE e.id=:id AND e.isHistory=false")
    void delete(@Param("id")long id);

}
