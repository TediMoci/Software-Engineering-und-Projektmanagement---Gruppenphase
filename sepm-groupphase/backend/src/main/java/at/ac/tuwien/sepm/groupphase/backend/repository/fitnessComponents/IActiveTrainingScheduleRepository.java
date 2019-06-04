package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ActiveTrainingScheduleKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActiveTrainingScheduleRepository extends JpaRepository<ActiveTrainingSchedule, ActiveTrainingScheduleKey> {

    /**
     * @param activeTrainingSchedule to be saved in the database
     * @return the saved ActiveTrainingSchedule
     * @throws DataAccessException if an error occurred while trying to save the ActiveTrainingSchedule in the database
     */
    ActiveTrainingSchedule save(ActiveTrainingSchedule activeTrainingSchedule) throws DataAccessException;

}
