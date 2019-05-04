package at.ac.tuwien.sepm.groupphase.backend.repository.Actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDudeRepository extends JpaRepository<Dude, Long> {
}
