package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {

    /**
     * @param course to be saved in the database
     * @return the saved Course
     * @throws DataAccessException if an error occurred while trying to save the Course in the database
     */
    Course save(Course course) throws DataAccessException;

    /**
     * @param name of the Courses to find
     * @return Courses with name beginning with the given name-string
     * @throws DataAccessException if an error occurred while trying to find the Courses in the database
     */
    @Query("SELECT c FROM Course c WHERE c.name LIKE ?1%")
    List<Course> findByName(String name) throws DataAccessException;

    /**
     * @return all Courses in the database
     * @throws DataAccessException if an error occurred while trying to find the Courses in the database
     */
    List<Course> findAll() throws DataAccessException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @return all Courses in the database according to the given filters
     * @throws DataAccessException if an error occurred while trying to find the Exercises in the database
     */
    @Query("SELECT c FROM Course c WHERE c.name LIKE %?1% OR c.description LIKE %?1%")
    List<Course> findByFilter(String filter) throws DataAccessException;
}
