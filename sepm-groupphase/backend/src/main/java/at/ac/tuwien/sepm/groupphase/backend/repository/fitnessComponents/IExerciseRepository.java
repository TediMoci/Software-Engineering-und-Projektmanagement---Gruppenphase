package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.parameterObjects.ExercisePo;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseKey;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
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
    @Query("SELECT e FROM Exercise e WHERE e.name LIKE ?1% AND e.isHistory=false")
    List<Exercise> findByName(String name) throws DataAccessException;

    /**
     * @return all Exercises in the database
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE e.isHistory=false ORDER BY e.id")
    List<Exercise> findAll() throws DataAccessException;

    /**
     * filtering according to the given parameters
     * @param name
     * @param description
     * @param equipment
     * @param muscleGroup
     * @param rating
     * @param category
     * @return all Exercises in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE e.name LIKE ?1% AND e.description LIKE %?2% AND e.equipment LIKE %?3%" +
        " AND e.muscleGroup LIKE %?4% AND e.rating>=?5 AND e.category=?6 AND e.isHistory=false")
    List<Exercise> findByFiltersWithCategory(String name, String description, String equipment, String muscleGroup, Double rating, Category category) throws DataAccessException;

    /**
     * filtering according to the given parameters
     * @param name
     * @param description
     * @param equipment
     * @param muscleGroup
     * @param rating
     * @return all Exercises in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT e FROM Exercise e WHERE e.name LIKE ?1% AND e.description LIKE %?2% AND e.equipment LIKE %?3%" +
        " AND e.muscleGroup LIKE %?4% AND e.rating>=?5 AND e.isHistory=false")
    List<Exercise> findByFiltersWithoutCategory(String name, String description, String equipment, String muscleGroup, Double rating) throws DataAccessException;

    @Query("SELECT e FROM Exercise e WHERE e.id=?1 AND e.isHistory=false")
    Exercise findById(long id);

    @Modifying
    @Transactional
    @Query("UPDATE Exercise e SET e.id=:myID WHERE e.id=:dbID AND e.isHistory=false")
    void updateNew(@Param("myID")long myId,@Param("dbID")long dbId);

    @Modifying
    @Transactional
    @Query("UPDATE Exercise e SET e.isHistory=true WHERE e.id=:id AND e.isHistory=false")
    void delete(@Param("id")long id);

}
