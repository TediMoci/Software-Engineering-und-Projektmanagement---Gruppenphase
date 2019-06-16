package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.CourseRatingKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.CourseRating;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRatingRepository extends JpaRepository<CourseRating, CourseRatingKey> {

    /**
     * @param courseRating to be saved in the database
     * @return the saved CourseRating
     * @throws DataAccessException if an error occurred while trying to save the courseRating in the database
     */
    CourseRating save(CourseRating courseRating) throws DataAccessException;

    /**
     * @param courseRating to be deleted from the database
     * @throws DataAccessException if an error occurred while trying to save the courseRating in the database
     */
    void delete(CourseRating courseRating) throws DataAccessException;

    /**
     * @param dudeId of dude
     * @param courseId of course
     * @return CourseRating found with the given dude and course IDs
     */
    @Query("SELECT c FROM CourseRating c WHERE c.dudeId=?1 AND c.courseId=?2")
    CourseRating findRating(Long dudeId, Long courseId);
}
