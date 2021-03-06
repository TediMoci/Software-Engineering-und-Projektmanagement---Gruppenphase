package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
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
     * @param dudeId of the Dude that called the method
     * @return Workouts with name beginning with the given name-string
     * @throws ServiceException if an error occurred while trying to find the Workouts in the system
     */
    List<Workout> findByName(String name, Long dudeId) throws ServiceException;

    /**
     * @param dudeId of the Dude that called the method
     * @return all Workouts in the system
     * @throws ServiceException if an error occurred while trying to find the Workouts in the system
     */
    List<Workout> findAll(Long dudeId) throws ServiceException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param difficulty to be filtered for
     * @param calorieLower lower bound for calorieConsumption
     * @param calorieUpper upper bound for calorieConsumption
     * @param dudeId of the Dude that called the method
     * @return all Workouts in the system according to the given filters
     * @throws ServiceException if an error occurred while trying to find the Workouts in the system
     */
    List<Workout> findByFilter(String filter, Integer difficulty, Double calorieLower, Double calorieUpper, Long dudeId) throws ServiceException;

    /**
     * @param id of Workout to be found
     * @param newWorkout that will be updated
     * @return updated Workout
     * @throws ServiceException if an error occurred while trying to find the Workout in the system
     */
    Workout update(long id, Workout newWorkout) throws ServiceException;

    /**
     * @param id of Workout to be found
     * @return Workout found with the given id
     * @throws ServiceException if an error occurred while trying to find the Workout in the system
     */
    Workout findById(long id) throws ServiceException;

    /**
     * @param id of Workout to be deleted
     * @throws ServiceException if an error occurred while trying to find the Workout in the system
     */
    void delete(long id) throws ServiceException;

    /**
     * @param dudeId of the Dude
     * @param workoutId of the Workout that the Dude wants to bookmark
     * @param workoutVersion of the Workout that the Dude wants to bookmark
     * @throws ServiceException if an error occurred while trying to bookmark the Workout
     */
    void saveWorkoutBookmark(Long dudeId, Long workoutId, Integer workoutVersion) throws ServiceException;

    /**
     * @param dudeId of the Dude
     * @param workoutId of the Workout that the Dude wants to delete the bookmark for
     * @param workoutVersion of the Workout that the Dude wants to delete the bookmark for
     * @throws ServiceException if an error occurred while trying to delete the bookmark for the Workout
     */
    void deleteWorkoutBookmark(Long dudeId, Long workoutId, Integer workoutVersion) throws ServiceException;
    /**
     * @param dudeId of the Dude
     * @param workoutId of the Workout that the Dude wants to rate
     * @param rating given by Dude to Workout
     * @throws ServiceException if an error occurred while trying to rate the Workout
     */
    void saveWorkoutRating(Long dudeId, Long workoutId, Integer rating) throws ServiceException;

    /**
     * @param dudeId of the Dude
     * @param workoutId of the Workout that the Dude wants to rate
     * @throws ServiceException if an error occurred while trying to rate the Workout
     */
    void deleteWorkoutRating(Long dudeId, Long workoutId) throws ServiceException;
}
