package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ActiveTrainingScheduleKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface IActiveTrainingScheduleRepository extends JpaRepository<ActiveTrainingSchedule, ActiveTrainingScheduleKey> {

    /**
     * @param activeTrainingSchedule to be saved in the database
     * @return the saved ActiveTrainingSchedule
     * @throws DataAccessException if an error occurred while trying to save the ActiveTrainingSchedule in the database
     */
    ActiveTrainingSchedule save(ActiveTrainingSchedule activeTrainingSchedule) throws DataAccessException;

    /**
     * @param dudeId of the ActiveTrainingSchedule to be found in the database
     * @return the found ActiveTrainingSchedule
     * @throws NoSuchElementException if no ActiveTrainingSchedule for given dudeId was found in the database
     */
    Optional<ActiveTrainingSchedule> findByDudeId(Long dudeId) throws NoSuchElementException;

    /**
     * @param dudeId of the ActiveTrainingSchedule to be deleted from the database
     * @throws DataAccessException if an error occurred while trying to delete the ActiveTrainingSchedule from the database
     */
    @Transactional
    void deleteByDudeId(Long dudeId) throws DataAccessException;

}
