package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

public interface ITrainingScheduleService {

    /**
     *
     * @param trainingSchedule to be saved in the system
     * @return the saved trainingSchedule
     * @throws ServiceException if an error occurred while trying to save the TrainingSchedule in the system
     */
    TrainingSchedule save(TrainingSchedule trainingSchedule) throws ServiceException;

    /**
     * @param days available to do workout
     * @param duration to do workouts per day
     * @param minTarget minimum amount of calories to be spent per day
     * @param maxTarget maximum amount of calories to be spent per day
     * @param trainingSchedule to be saved
     * @return the saved TrainingSchedule
     * @throws ServiceException if an error occurred while trying to save the TrainingSchedule in the system
     */
    TrainingSchedule saveRandom(int days, int duration, double minTarget, double maxTarget, TrainingSchedule trainingSchedule) throws ServiceException;

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
     * @param id of TrainingSchedule to be found
     * @param newTraining that will be updated
     * @return updated TrainingSchedule
     * @throws ServiceException if an error occurred while trying to find the Workout in the system
     */
    TrainingSchedule update(long id, TrainingSchedule newTraining) throws ServiceException;
}
