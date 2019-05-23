package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IWorkoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WorkoutService implements IWorkoutService {

    private final IWorkoutRepository iWorkoutRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutService.class);

    public WorkoutService(IWorkoutRepository iWorkoutRepository) {
        this.iWorkoutRepository = iWorkoutRepository;
    }

    @Override
    public Workout save(Workout workout) throws ServiceException {
        LOGGER.info("Entering save for: " + workout);
        try {
            return iWorkoutRepository.save(workout);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Workout findByIdAndVersion(Long id, Integer version) throws ServiceException {
        LOGGER.info("Entering findByIdAndVersion with id: " + id + "; and version: " + version);
        try {
            return iWorkoutRepository.findByIdAndVersion(id, version).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Workout> findByName(String name) throws ServiceException {
        LOGGER.info("Entering findByName with name: " + name);
        try {
            return iWorkoutRepository.findByName(name);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Workout> findAll() throws ServiceException {
        LOGGER.info("Entering findAll");
        try {
            return iWorkoutRepository.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Workout findById(long id) throws ServiceException {
        LOGGER.info("Entering findById with id: " + id);
        try {
            return iWorkoutRepository.findById(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Workout update(long id, Workout newWorkout) throws ServiceException {
        LOGGER.info("Updating workout with id: " + id);
        try {
            Workout oldWorkout = iWorkoutRepository.findById(id);
            if (oldWorkout == null) throw new ServiceException("Could not find workout with id: " + id);
            oldWorkout.setHistory(true);
            newWorkout.setId(id);
            newWorkout.setVersion(newWorkout.getVersion()+1);
            iWorkoutRepository.save(oldWorkout);
            Long dbID = iWorkoutRepository.save(newWorkout).getId();
            iWorkoutRepository.updateNew(newWorkout.getId(), dbID);
            return newWorkout;
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        LOGGER.info("Deleting workout with id: " + id);
        try {
            Workout workout = iWorkoutRepository.findById(id);
            if (workout == null) throw new ServiceException("Could not find workout with id: " + id);
            iWorkoutRepository.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
