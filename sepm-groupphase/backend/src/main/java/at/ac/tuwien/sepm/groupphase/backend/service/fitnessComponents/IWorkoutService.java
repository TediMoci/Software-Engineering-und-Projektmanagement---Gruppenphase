package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.util.List;

public interface IWorkoutService {

    /**
     * @param workout to be saved in the system
     * @return the saved Workout
     * @throws ServiceException if an error occurred while trying to save the Workout in the system
     */
    Workout save(Workout workout) throws ServiceException;

    /**
     * @param id of the Workout to find
     * @param version of the Workout to find
     * @return the Workout with the given id and version
     * @throws ServiceException if an error occurred while trying to find the Workout in the system
     */
    Workout findByIdAndVersion(Long id, Integer version) throws ServiceException;

    /**
     * @param name of the Workouts to find
     * @return Workouts with name beginning with the given name-string
     * @throws ServiceException if an error occurred while trying to find the Workouts in the system
     */
    List<Workout> findByName(String name) throws ServiceException;

    /**
     * @return all Workouts in the system
     * @throws ServiceException if an error occurred while trying to find the Workouts in the system
     */
    List<Workout> findAll() throws ServiceException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param difficulty to be filtered for
     * @param calorieLower lower bound for calorieConsumption
     * @param calorieUpper upper bound for calorieConsumption
     * @return all Workouts in the system according to the given filters
     * @throws ServiceException if an error occurred while trying to find the Workouts in the system
     */
    List<Workout> findByFilter(String filter, Integer difficulty, Double calorieLower, Double calorieUpper) throws ServiceException;

    // TODO : write log
    Workout update(long id, Workout newWorkout) throws ServiceException;

    //TODO: write log
    Workout findById(long id) throws ServiceException;

    //TODO: write log
    void delete(long id) throws ServiceException;

}
