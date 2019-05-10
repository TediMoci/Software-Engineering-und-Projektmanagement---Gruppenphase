package at.ac.tuwien.sepm.groupphase.backend.repository.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDudeRepository extends JpaRepository<Dude, Long> {

    /**
     *
     * @param dude
     * @return
     */
     Dude save(Dude dude);

    /**
     *
     * @param name
     * @return
     */
    Dude findByName(@Param("name")String name);

    /**
     *
     * @param name
     * @param password
     * @return
     */
    @Query("SELECT u FROM Dude u WHERE u.name = ?1 and u.password = ?2")
    Dude findByNameAndPassword(String name, String password);

    List<Dude> findAll();

}
