package at.ac.tuwien.sepm.groupphase.backend.repository.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFitnessProviderRepository extends JpaRepository<FitnessProvider, Long> {

    /**
     *
     * @param fitnessProvider
     * @return
     */
    FitnessProvider save(FitnessProvider fitnessProvider);

    /**
     *
     * @param name
     * @return
     */
    FitnessProvider findByName(@Param("name")String name);

    /**
     *
     * @return
     */
    List<FitnessProvider> findAll();
}
