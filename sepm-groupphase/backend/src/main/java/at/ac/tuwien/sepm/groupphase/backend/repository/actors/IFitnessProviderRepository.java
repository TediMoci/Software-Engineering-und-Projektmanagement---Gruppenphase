package at.ac.tuwien.sepm.groupphase.backend.repository.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
     * @param password
     * @return
     */
    //todo expand database with password
    @Query("select u FROM FitnessProvider u where u.name = name and u.password = password")
    FitnessProvider findByNameAndPassword(@Param("name")String name,@Param("password") String password);
}
