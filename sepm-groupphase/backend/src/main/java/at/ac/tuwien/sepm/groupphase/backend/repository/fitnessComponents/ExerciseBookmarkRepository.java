package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ExerciseBookmarkRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int checkExerciseBookmark(Long dudeId, Long exerciseId, Integer exerciseVersion) {
        return entityManager.createNativeQuery("SELECT * FROM dude_exercise_bookmarks WHERE dude_id=? AND exercise_id=? AND exercise_version=?")
            .setParameter(1, dudeId)
            .setParameter(2, exerciseId)
            .setParameter(3, exerciseVersion)
            .getResultList()
            .size();
    }

    @Transactional
    public void saveExerciseBookmark(Long dudeId, Long exerciseId, Integer exerciseVersion) {
        entityManager.createNativeQuery("INSERT INTO dude_exercise_bookmarks (dude_id, exercise_id, exercise_version) VALUES (?, ?, ?)")
            .setParameter(1, dudeId)
            .setParameter(2, exerciseId)
            .setParameter(3, exerciseVersion)
            .executeUpdate();
    }

    @Transactional
    public void deleteExerciseBookmark(Long dudeId, Long exerciseId, Integer exerciseVersion) {
        entityManager.createNativeQuery("DELETE FROM dude_exercise_bookmarks WHERE dude_id=? AND exercise_id=? AND exercise_version=?")
            .setParameter(1, dudeId)
            .setParameter(2, exerciseId)
            .setParameter(3, exerciseVersion)
            .executeUpdate();
    }

}
