package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.util.List;

public interface IExerciseService {

    /**
     * @param exercise to be saved in the system
     * @return the saved Exercise
     * @throws ServiceException if an error occurred while trying to save the Exercise in the system
     */
    Exercise save(Exercise exercise) throws ServiceException;

    /**
     * @param id of the Exercise to find
     * @param version of the Exercise to find
     * @return the Exercise with the given id and version
     * @throws ServiceException if an error occurred while trying to find the Exercise in the system
     */
    Exercise findByIdAndVersion(Long id, Integer version) throws ServiceException;

    /**
     * @param name of the Exercises to find
     * @return Exercises with name beginning with the given name-string
     * @throws ServiceException if an error occurred while trying to find the Exercises in the system
     */
    List<Exercise> findByName(String name) throws ServiceException;

    /**
     * @return all Exercises in the system
     * @throws ServiceException if an error occurred while trying to find the Exercises in the system
     */
    List<Exercise> findAll() throws ServiceException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @param category to be filtered for
     * @return all Exercises in the system according to the given filters
     * @throws ServiceException if an error occurred while trying to find the Exercises in the system
     */
    List<Exercise> findByFilter(String filter, Category category) throws ServiceException;

    /**
     * @param id of Exercise to be found
     * @param newExercise that will be updated
     * @return updated Exercise
     * @throws ServiceException if an error occurred while trying to find the Exercise in the system
     */
    Exercise update(long id, Exercise newExercise) throws ServiceException;

    /**
     * @param id of Exercise to be found
     * @return Exercise found with the given id
     * @throws ServiceException if an error occurred while trying to find the Exercise in the system
     */
    Exercise findById(long id) throws ServiceException;

    /**
     * @param id of Exercise to be deleted
     * @throws ServiceException if an error occurred while trying to find the Exercise in the system
     */
    void delete(long id) throws ServiceException;

    /**
     * @param id of the Exercise
     * @param version of the Exercise
     * @param fileName of the image in the path
     * @throws ServiceException if something went wrong while updating the imagePath in the system
     */
    void updateImagePath(Long id, Integer version, String fileName) throws ServiceException;
}
