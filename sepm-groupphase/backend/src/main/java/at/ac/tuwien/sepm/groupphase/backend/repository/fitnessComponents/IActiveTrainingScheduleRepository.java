package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ActiveTrainingScheduleKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface IActiveTrainingScheduleRepository extends JpaRepository<ActiveTrainingSchedule, ActiveTrainingScheduleKey> {

    /**
     * @param activeTrainingSchedule to be saved in the database
     * @return the saved ActiveTrainingSchedule
     * @throws DataAccessException if an error occurred while trying to save the ActiveTrainingSchedule in the database
     */
    ActiveTrainingSchedule save(ActiveTrainingSchedule activeTrainingSchedule) throws DataAccessException;

    /**
     * @param id of the sought for activeTrainingSchedule
     * @return activeTrainingSchedule with given id
     */
    ActiveTrainingSchedule findById(Long id);

    /**
     * update startDate of given activeTrainingSchedule
     * @param dudeId of dude the activeTrainingSchedule belongs to
     * @param tsId id of the trainingSchedule the activeTrainingSchedule refers to
     * @param tsVersion version of the trainingSchedule the activeTrainingSchedule refers to
     * @param newDate date which should be set instead of the old startDate
     */
    @Modifying
    @Transactional
    @Query("UPDATE ActiveTrainingSchedule t SET t.startDate=:newDate WHERE t.id=:dudeId AND t.trainingScheduleId=:tsId AND t.trainingScheduleVersion=:tsVersion")
    void updateActiveTrainingSchedule(@Param("dudeId")Long dudeId, @Param("tsId")Long tsId, @Param("tsVersion")Integer tsVersion, @Param("newDate")LocalDate newDate);

    /**
     * @param dudeId of the ActiveTrainingSchedule to be deleted from the database
     * @throws DataAccessException if an error occurred while trying to delete the ActiveTrainingSchedule from the database
     */
    @Transactional
    void deleteByDudeId(Long dudeId) throws DataAccessException;

}
