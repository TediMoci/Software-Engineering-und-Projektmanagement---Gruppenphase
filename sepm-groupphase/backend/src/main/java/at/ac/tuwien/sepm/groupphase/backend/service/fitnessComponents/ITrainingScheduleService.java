package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

public interface ITrainingScheduleService {

    /**
     * @param trainingSchedule to be saved in the system
     * @return the saved TrainingSchedule
     * @throws ServiceException if an error occurred while trying to save the TrainingSchedule in the system
     */
    TrainingSchedule save(TrainingSchedule trainingSchedule) throws ServiceException;

    /**
     * @param activeTrainingSchedule to be saved in the system
     * @return the saved ActiveTrainingSchedule
     * @throws ServiceException if an error occurred while trying to save the ActiveTrainingSchedule in the system
     */
    ActiveTrainingSchedule saveActive(ActiveTrainingSchedule activeTrainingSchedule) throws ServiceException;

    /**
     * @param exerciseDones to be saved in the system
     * @throws ServiceException if an error occurred while trying to save the ExerciseDones in the system
     */
    void markExercisesAsDone(ExerciseDone[] exerciseDones) throws ServiceException;

    /**
     * @param days             available to do workout
     * @param trainingSchedule to be saved
     * @return the saved TrainingSchedule
     * @throws ServiceException if an error occurred while trying to save the TrainingSchedule in the system
     */
    TrainingSchedule saveRandom(int days, double minTarget, double maxTarget, TrainingSchedule trainingSchedule) throws ServiceException;

    /**
     * @param id of TrainingSchedule to be found
     * @return TrainingSchedule found with the given id
     * @throws ServiceException if an error occurred while trying to find the Workout in the system
     */
    TrainingSchedule findById(long id) throws ServiceException;

    /**
     * @param id of TrainingSchedule to be deleted
     * @throws ServiceException if an error occurred while trying to find the Workout in the system
     */
    void delete(long id) throws ServiceException;

    /**
     * @param dudeId of the ActiveTrainingSchedule to be deleted from the system
     * @throws ServiceException if an error occurred while trying to delete the ActiveTrainingSchedule from the system
     */
    void deleteActive(Long dudeId) throws ServiceException;

    /**
     * @param id          of TrainingSchedule to be found
     * @param newTraining that will be updated
     * @return updated TrainingSchedule
     * @throws ServiceException if an error occurred while trying to find the Workout in the system
     */
    TrainingSchedule update(long id, TrainingSchedule newTraining) throws ServiceException;

    /**
     *
     * @param activeSchedule
     * @param dude
     * @return
     * @throws ServiceException
     */
    ActiveTrainingSchedule calculatePercentageOfChangeForInterval(ActiveTrainingSchedule activeSchedule, Dude dude) throws ServiceException;

    /**
     *
     * @param activeTs
     * @param dudeId
     * @param oldTs
     * @return
     * @throws ServiceException
     */
    TrainingSchedule copyOldTrainingSchedule(ActiveTrainingSchedule activeTs, Long dudeId, TrainingSchedule oldTs) throws ServiceException;
}
