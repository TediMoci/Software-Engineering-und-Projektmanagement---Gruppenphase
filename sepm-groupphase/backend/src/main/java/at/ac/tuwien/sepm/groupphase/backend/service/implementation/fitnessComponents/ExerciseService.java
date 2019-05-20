package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExerciseService implements IExerciseService {

    private final IExerciseRepository iExerciseRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseService.class);

    public ExerciseService(IExerciseRepository iExerciseRepository) {
        this.iExerciseRepository = iExerciseRepository;
    }

    @Override
    public Exercise save(Exercise exercise) throws ServiceException {
        LOGGER.info("Entering save for: " + exercise);
        try {
            return iExerciseRepository.save(exercise);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Exercise findByIdAndVersion(Long id, Integer version) throws ServiceException {
        LOGGER.info("Entering findByIdAndVersion with id: " + id + "; and version: " + version);
        try {
            return iExerciseRepository.findByIdAndVersion(id, version).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Exercise> findByName(String name) throws ServiceException {
        LOGGER.info("Entering findByName with name: " + name);
        try {
            return iExerciseRepository.findByName(name);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Exercise> findAll() throws ServiceException {
        LOGGER.info("Entering findAll");
        try {
            return iExerciseRepository.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
