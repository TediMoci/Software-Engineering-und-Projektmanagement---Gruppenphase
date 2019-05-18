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
     * @param course
     * @return
     * @throws DataAccessException
     */
    Course save(Course course) throws DataAccessException;

    /**
     * @param name
     * @return
     * @throws DataAccessException
     */
    @Query("SELECT c FROM Course c WHERE c.name LIKE ?1%")
    List<Course> findByName(String name) throws DataAccessException;

    /**
     * @return
     * @throws DataAccessException
     */
    List<Course> findAll() throws DataAccessException;

}
