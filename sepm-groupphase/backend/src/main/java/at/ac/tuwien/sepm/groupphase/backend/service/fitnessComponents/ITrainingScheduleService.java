package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import java.util.List;

public interface ITrainingScheduleService {

    /**
     *
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
     * @param days available to do workout
     * @param duration to do workouts per day
     * @param minTarget minimum amount of calories to be spent per day
     * @param maxTarget maximum amount of calories to be spent per day
     * @param trainingSchedule to be saved
     * @return the saved TrainingSchedule
     * @throws ServiceException if an error occurred while trying to save the TrainingSchedule in the system
     */
    TrainingSchedule saveRandom(int days, int duration, double minTarget, double maxTarget, TrainingSchedule trainingSchedule, boolean lowerDifficulty) throws ServiceException;

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
     * @param id of TrainingSchedule to be found
     * @param newTraining that will be updated
     * @return updated TrainingSchedule
     * @throws ServiceException if an error occurred while trying to find the Workout in the system
     */
    TrainingSchedule update(long id, TrainingSchedule newTraining) throws ServiceException;

    /**
     * @param name of the TrainingSchedules to find
     * @return TrainingSchedules with name beginning with the given name-string
     * @throws ServiceException if an error occurred while trying to find the TrainingSchedules in the system
     */
    List<TrainingSchedule> findByName(String name) throws ServiceException;

    /**
     * @param id of the TrainingSchedule to find
     * @param version of the TrainingSchedule to find
     * @return the TrainingSchedule with the given id and version
     * @throws ServiceException if an error occurred while trying to find the TrainingSchedule in the system
     */
    TrainingSchedule findByIdAndVersion(Long id, Integer version) throws ServiceException;

    /**
     * @param filter of the name or description TrainingSchedules to find
     * @param difficulty of TrainingSchedules to find
     * @param intervalLength of TrainingSchedules to find
     * @return TrainingSchedules with given parameters
     * @throws ServiceException if an error occurred while trying to find the TrainingSchedules in the database
     */
    List<TrainingSchedule> findByFilter(String filter, Integer difficulty, Integer intervalLength) throws ServiceException;
}
