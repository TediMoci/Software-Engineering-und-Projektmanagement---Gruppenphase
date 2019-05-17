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
    Dude findByName(String name);

    /**
     *
     * @return
     */
    List<Dude> findAll();

}
