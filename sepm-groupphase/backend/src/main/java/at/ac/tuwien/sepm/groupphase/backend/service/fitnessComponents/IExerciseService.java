package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.util.List;

public interface IExerciseService {

    /**
     * @param exercise
     * @return
     * @throws ServiceException
     */
    Exercise save(Exercise exercise) throws ServiceException;

    /**
     * @param name
     * @return
     * @throws ServiceException
     */
    List<Exercise> findByName(String name) throws ServiceException;

    /**
     * @return
     * @throws ServiceException
     */
    List<Exercise> findAll() throws ServiceException;

}
