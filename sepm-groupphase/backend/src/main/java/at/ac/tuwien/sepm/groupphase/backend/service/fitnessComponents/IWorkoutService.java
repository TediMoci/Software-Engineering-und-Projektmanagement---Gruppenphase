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

}
