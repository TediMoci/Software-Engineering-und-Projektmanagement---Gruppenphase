package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class WorkoutBookmarkRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int checkWorkoutBookmark(Long dudeId, Long workoutId, Integer workoutVersion) {
        return entityManager.createNativeQuery("SELECT * FROM dude_workout_bookmarks WHERE dude_id=? AND workout_id=? AND workout_version=?")
            .setParameter(1, dudeId)
            .setParameter(2, workoutId)
            .setParameter(3, workoutVersion)
            .getResultList()
            .size();
    }

    @Transactional
    public void saveWorkoutBookmark(Long dudeId, Long workoutId, Integer workoutVersion) {
        entityManager.createNativeQuery("INSERT INTO dude_workout_bookmarks (dude_id, workout_id, workout_version) VALUES (?, ?, ?)")
            .setParameter(1, dudeId)
            .setParameter(2, workoutId)
            .setParameter(3, workoutVersion)
            .executeUpdate();
    }

    @Transactional
    public void deleteWorkoutBookmark(Long dudeId, Long workoutId, Integer workoutVersion) {
        entityManager.createNativeQuery("DELETE FROM dude_workout_bookmarks WHERE dude_id=? AND workout_id=? AND workout_version=?")
            .setParameter(1, dudeId)
            .setParameter(2, workoutId)
            .setParameter(3, workoutVersion)
            .executeUpdate();
    }

}
