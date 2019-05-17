package at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

public interface IExerciseService {

    /**
     * @param exercise
     * @return
     * @throws ServiceException
     */
    Exercise save(Exercise exercise) throws ServiceException;

}
