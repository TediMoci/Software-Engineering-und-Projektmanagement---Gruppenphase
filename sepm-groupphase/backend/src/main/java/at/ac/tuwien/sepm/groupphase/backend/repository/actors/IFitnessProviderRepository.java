package at.ac.tuwien.sepm.groupphase.backend.repository.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import org.springframework.dao.DataAccessException;
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

    /*

     */
    @Query("SELECT f FROM FitnessProvider  f WHERE f.name LIKE %?1% OR f.description LIKE %?1% OR f.address LIKE %?1%")
    List<FitnessProvider> findByFilter(String filter) throws DataAccessException;
}
