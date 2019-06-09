package at.ac.tuwien.sepm.groupphase.backend.repository.actors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FollowFitnessProviderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int checkFollowedFitnessProvider(Long dudeId, Long fitnessProviderId) {
        return entityManager.createNativeQuery("SELECT * FROM dude_fitness_provider_follows WHERE dude_id=? AND fitness_provider_id=?")
            .setParameter(1, dudeId)
            .setParameter(2, fitnessProviderId)
            .getResultList()
            .size();
    }

    @Transactional
    public void followFitnessProvider(Long dudeId, Long fitnessProviderId) {
        entityManager.createNativeQuery("INSERT INTO dude_fitness_provider_follows (dude_id, fitness_provider_id) VALUES (?, ?)")
            .setParameter(1, dudeId)
            .setParameter(2, fitnessProviderId)
            .executeUpdate();
    }

    @Transactional
    public void unfollowFitnessProvider(Long dudeId, Long fitnessProviderId) {
        entityManager.createNativeQuery("DELETE FROM dude_fitness_provider_follows WHERE dude_id=? AND fitness_provider_id=?")
            .setParameter(1, dudeId)
            .setParameter(2, fitnessProviderId)
            .executeUpdate();
    }

}
