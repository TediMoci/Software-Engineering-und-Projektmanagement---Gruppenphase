package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
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

    // TODO : write log
    Exercise update(long id, Exercise newExercise) throws ServiceException;

    //TODO: write log
    Exercise findById(long id) throws ServiceException;

    //TODO: write log
    void delete(long id) throws ServiceException;
}
