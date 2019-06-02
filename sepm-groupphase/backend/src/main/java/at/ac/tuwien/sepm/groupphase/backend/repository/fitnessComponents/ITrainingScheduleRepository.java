package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITrainingScheduleRepository extends JpaRepository<TrainingSchedule, Long> {

    /**
     *
     * @param trainingSchedule to be saved in the database
     * @return the saved TrainingSchedule
     * @throws DataAccessException if an error occured while trying to save the TrainingSchedule in the database
     */
    TrainingSchedule save(TrainingSchedule trainingSchedule) throws DataAccessException;

}
