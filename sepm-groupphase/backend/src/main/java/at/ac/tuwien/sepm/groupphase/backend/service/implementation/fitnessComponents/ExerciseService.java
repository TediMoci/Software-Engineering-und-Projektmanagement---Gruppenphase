package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
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

    @Override
    public List<Exercise> findByFilter(String filter, Category category) throws ServiceException {
        LOGGER.info("Entering findByFilter with filter: " + filter + "; and category: " + category);
        try {
            if (category != null) {
                return iExerciseRepository.findByFilterWithCategory(filter, category);
            } else {
                return iExerciseRepository.findByFilterWithoutCategory(filter);
            }
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Exercise findById(long id) throws ServiceException {
        LOGGER.info("Entering findById with id: " + id);
        try {
            return iExerciseRepository.findById(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Exercise update(long id, Exercise newExercise) throws ServiceException {
        LOGGER.info("Updating exercise with id: " + id);
        try {
            Exercise oldExercise = iExerciseRepository.findById(id);
            if (oldExercise == null) throw new ServiceException("Could not find exercise with id: " + id);

            oldExercise.setHistory(true);
            newExercise.setId(id);
            newExercise.setVersion(oldExercise.getVersion()+1);
            iExerciseRepository.save(oldExercise);
            Long dbID = iExerciseRepository.save(newExercise).getId();
            iExerciseRepository.updateNew(newExercise.getId(), dbID);
            return newExercise;
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        LOGGER.info("Deleting exercise with id: " + id);
        try {
            Exercise exercise = iExerciseRepository.findById(id);
            if (exercise == null) throw new ServiceException("Could not find exercise with id: " + id);
            iExerciseRepository.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String updateImagePath(Long id, Integer version, String fileName) throws ServiceException {
        LOGGER.info("Entering updateImagePath with id: " + id + "; version: " + version + "; fileName: " + fileName);
        Exercise exercise;
        try {
            exercise = iExerciseRepository.findByIdAndVersion(id, version).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        String imagePath = "/assets/img/" + fileName;
        exercise.setImagePath(imagePath);
        iExerciseRepository.save(exercise);
        return imagePath;
    }
}
