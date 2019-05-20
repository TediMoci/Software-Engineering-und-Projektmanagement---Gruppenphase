package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.util.List;

public interface IExerciseService {

    /**
     * @param exercise to be saved in the system
     * @return the saved Exercise
     * @throws ServiceException if an error occured while trying to save the Exercise in the system
     */
    Exercise save(Exercise exercise) throws ServiceException;

    /**
     * @param id of the Exercise to find
     * @return the Exercise with the given id
     * @throws ServiceException if an error occured while trying to find the Exercise in the system
     */
    Exercise findById(Long id) throws ServiceException;

    /**
     * @param name of the Exercises to find
     * @return Exercises with name beginning with the given name-string
     * @throws ServiceException if an error occured while trying to find the Exercises in the system
     */
    List<Exercise> findByName(String name) throws ServiceException;

    /**
     * @return all Exercises in the system
     * @throws ServiceException if an error occured while trying to find the Exercises in the system
     */
    List<Exercise> findAll() throws ServiceException;

    /**
     *
     * @param creator
     * @return
     * @throws ServiceException
     */
    List<Exercise> findAllByCreator(Dude creator) throws ServiceException;

}
