package at.ac.tuwien.sepm.groupphase.backend.repository.Actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFitnessProviderRepository extends JpaRepository<FitnessProvider, Long> {
}
