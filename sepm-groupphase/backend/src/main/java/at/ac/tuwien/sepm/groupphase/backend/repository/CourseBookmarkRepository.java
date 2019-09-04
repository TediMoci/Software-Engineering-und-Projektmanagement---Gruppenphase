package at.ac.tuwien.sepm.groupphase.backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CourseBookmarkRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int checkCourseBookamrk(Long dudeId, Long courseId) {
        return entityManager.createNativeQuery("SELECT * FROM dude_course_bookmarks WHERE dude_id=? AND course_id=?")
            .setParameter(1, dudeId)
            .setParameter(2, courseId)
            .getResultList()
            .size();
    }

    @Transactional
    public void saveCourseBookmark(Long dudeId, Long courseId) {
        entityManager.createNativeQuery("INSERT INTO dude_course_bookmarks (dude_id, course_id) VALUES (?, ?)")
            .setParameter(1, dudeId)
            .setParameter(2, courseId)
            .executeUpdate();
    }

    @Transactional
    public void deleteCourseBookmark(Long dudeId, Long courseId) {
        entityManager.createNativeQuery("DELETE FROM dude_course_bookmarks WHERE dude_id=? AND course_id=?")
            .setParameter(1, dudeId)
            .setParameter(2, courseId)
            .executeUpdate();
    }

    @Transactional
    public void deleteCourseBookmarkByCourseId(Long courseId) {
        entityManager.createNativeQuery("DELETE FROM dude_course_bookmarks WHERE course_id=?")
            .setParameter(1, courseId)
            .executeUpdate();
    }

}
