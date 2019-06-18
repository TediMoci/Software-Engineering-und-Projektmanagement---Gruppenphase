package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.FinishedTrainingScheduleStats;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFinishedTrainingScheduleRepository extends JpaRepository<FinishedTrainingScheduleStats, Long> {

    /**
     *
     * @param stats to be saved in the database
     * @return the saved Stats
     * @throws DataAccessException if an error occured while trying to save the Stats in the database
     */
    FinishedTrainingScheduleStats save(FinishedTrainingScheduleStats stats) throws DataAccessException;
}
