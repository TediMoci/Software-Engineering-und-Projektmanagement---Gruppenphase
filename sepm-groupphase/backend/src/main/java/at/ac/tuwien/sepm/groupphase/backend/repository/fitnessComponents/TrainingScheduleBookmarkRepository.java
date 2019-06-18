package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TrainingScheduleBookmarkRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int checkTrainingScheduleBookmark(Long dudeId, Long trainingScheduleId, Integer trainingScheduleVersion) {
        return entityManager.createNativeQuery("SELECT * FROM dude_training_schedule_bookmarks WHERE dude_id=? AND training_schedule_id=? AND training_schedule_version=?")
            .setParameter(1, dudeId)
            .setParameter(2, trainingScheduleId)
            .setParameter(3, trainingScheduleVersion)
            .getResultList()
            .size();
    }

    @Transactional
    public void saveTrainingScheduleBookmark(Long dudeId, Long trainingScheduleId, Integer trainingScheduleVersion) {
        entityManager.createNativeQuery("INSERT INTO dude_training_schedule_bookmarks (dude_id, training_schedule_id, training_schedule_version) VALUES (?, ?, ?)")
            .setParameter(1, dudeId)
            .setParameter(2, trainingScheduleId)
            .setParameter(3, trainingScheduleVersion)
            .executeUpdate();
    }

    @Transactional
    public void deleteTrainingScheduleBookmark(Long dudeId, Long trainingScheduleId, Integer trainingScheduleVersion) {
        entityManager.createNativeQuery("DELETE FROM dude_training_schedule_bookmarks WHERE dude_id=? AND training_schedule_id=? AND training_schedule_version=?")
            .setParameter(1, dudeId)
            .setParameter(2, trainingScheduleId)
            .setParameter(3, trainingScheduleVersion)
            .executeUpdate();
    }

}
