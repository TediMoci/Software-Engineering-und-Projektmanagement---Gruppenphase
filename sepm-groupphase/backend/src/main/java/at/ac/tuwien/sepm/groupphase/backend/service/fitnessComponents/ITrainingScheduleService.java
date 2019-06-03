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
     * @param trainingSchedule to be saved
     * @return the saved TrainingSchedule
     * @throws ServiceException if an error occurred while trying to save the TrainingSchedule in the system
     */
    TrainingSchedule saveRandom(int days, double minTarget, double maxTarget, TrainingSchedule trainingSchedule) throws ServiceException;
}
