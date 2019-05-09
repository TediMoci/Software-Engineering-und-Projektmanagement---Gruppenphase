package at.ac.tuwien.sepm.groupphase.backend.repository.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
    @Query("SELECT u FROM Dude u WHERE u.name = name and u.password = password")
    Dude findByNameAndPassword(@Param("name") String name, @Param("password") String password);

}
