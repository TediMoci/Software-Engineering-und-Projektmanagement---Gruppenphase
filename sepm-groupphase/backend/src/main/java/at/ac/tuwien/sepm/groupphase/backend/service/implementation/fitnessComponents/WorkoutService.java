package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.WorkoutBookmarkRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IWorkoutService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.WorkoutExerciseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WorkoutService implements IWorkoutService {

    private final IWorkoutRepository iWorkoutRepository;
    private final WorkoutBookmarkRepository workoutBookmarkRepository;
    private final IExerciseRepository iExerciseRepository;
    private final IWorkoutExerciseRepository iWorkoutExerciseRepository;
    private final IDudeRepository iDudeRepository;
    private final WorkoutExerciseValidator workoutExerciseValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutService.class);

    public WorkoutService(IWorkoutRepository iWorkoutRepository, WorkoutBookmarkRepository workoutBookmarkRepository, IExerciseRepository iExerciseRepository, IWorkoutExerciseRepository iWorkoutExerciseRepository, IDudeRepository iDudeRepository, WorkoutExerciseValidator workoutExerciseValidator) {
        this.iWorkoutRepository = iWorkoutRepository;
        this.workoutBookmarkRepository = workoutBookmarkRepository;
        this.iExerciseRepository = iExerciseRepository;
        this.iWorkoutExerciseRepository = iWorkoutExerciseRepository;
        this.iDudeRepository = iDudeRepository;
        this.workoutExerciseValidator = workoutExerciseValidator;
    }

    @Override
    public Workout save(Workout workout) throws ServiceException {
        LOGGER.info("Entering save for: " + workout);
        List<WorkoutExercise> workoutExercises = workout.getExercises();
        workout.setExercises(null);
        validateWorkoutExercises(workoutExercises);

        Workout savedWorkout;
        try {
            savedWorkout = iWorkoutRepository.save(workout);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        saveWorkoutExercises(workoutExercises, savedWorkout);
        return savedWorkout;
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
    public List<Workout> findByName(String name, Long dudeId) throws ServiceException {
        LOGGER.info("Entering findByName with name: " + name + "; dudeId: " + dudeId);
        Dude dude = new Dude();
        dude.setId(dudeId);
        List<Workout> workouts;
        try {
            workouts = iWorkoutRepository.findByName(name);
            workouts.addAll(iWorkoutRepository.findOwnPrivateByName(name, dude));
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        return workouts;
    }

    @Override
    public List<Workout> findAll(Long dudeId) throws ServiceException {
        LOGGER.info("Entering findAll with dudeId: " + dudeId);
        Dude dude = new Dude();
        dude.setId(dudeId);
        List<Workout> workouts;
        try {
            workouts = iWorkoutRepository.findAll();
            workouts.addAll(iWorkoutRepository.findOwnPrivate(dude));
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        return workouts;
    }

    @Override
    public List<Workout> findByFilter(String filter, Integer difficulty, Double calorieLower, Double calorieUpper, Long dudeId) throws ServiceException {
        LOGGER.info("Entering findByFilter with filter: " + filter + "; and difficulty: " + difficulty + "; calorieLower: " + calorieLower + "; calorieUpper: " + calorieUpper + "; dudeId: " + dudeId);
        Dude dude = new Dude();
        dude.setId(dudeId);
        List<Workout> workouts;
        try {
            if (difficulty != null) {
                workouts = iWorkoutRepository.findByFilterWithDifficulty(filter, difficulty, calorieLower, calorieUpper);
                workouts.addAll(iWorkoutRepository.findOwnPrivateByFilterWithDifficulty(filter, difficulty, calorieLower, calorieUpper, dude));
            } else {
                workouts = iWorkoutRepository.findByFilterWithoutDifficulty(filter, calorieLower, calorieUpper);
                workouts.addAll(iWorkoutRepository.findOwnPrivateByFilterWithoutDifficulty(filter, calorieLower, calorieUpper, dude));
            }

        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        return workouts;
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
            newWorkout.setId(id);
            newWorkout.setVersion(1+oldWorkout.getVersion());

            List<WorkoutExercise> workoutExercises = newWorkout.getExercises();
            newWorkout.setExercises(null);
            validateWorkoutExercises(workoutExercises);

            iWorkoutRepository.delete(id);
            Long dbId = iWorkoutRepository.save(newWorkout).getId();
            iWorkoutRepository.updateNew(newWorkout.getId(), dbId);
            saveWorkoutExercises(workoutExercises, newWorkout);

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

    private void validateWorkoutExercises(List<WorkoutExercise> workoutExercises) throws ServiceException {
        for (WorkoutExercise workoutExercise : workoutExercises) {
            try {
                workoutExerciseValidator.validateWorkoutExercise(workoutExercise);
                if (iExerciseRepository.findByIdAndVersion(workoutExercise.getExerciseId(), workoutExercise.getExerciseVersion()).isEmpty()) {
                    throw new NoSuchElementException();
                }
            } catch (NoSuchElementException e) {
                throw new ServiceException("Exercise with id: " + workoutExercise.getExerciseId() + " and version: " + workoutExercise.getExerciseVersion() + " does not exist");
            } catch (ValidationException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

    private void saveWorkoutExercises(List<WorkoutExercise> workoutExercises, Workout savedWorkout) throws ServiceException {
        for (int i = 0; i < workoutExercises.size(); i++) {
            workoutExercises.get(i).setWorkoutId(savedWorkout.getId());
            workoutExercises.get(i).setWorkoutVersion(savedWorkout.getVersion());
            try {
                iWorkoutExerciseRepository.save(workoutExercises.get(i));
            } catch (DataAccessException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

    @Override
    public void saveWorkoutBookmark(Long dudeId, Long workoutId, Integer workoutVersion) throws ServiceException {
        LOGGER.info("Entering saveWorkoutBookmark with dudeId: " + dudeId + "; workoutId: " + workoutId + "; workoutVersion: " + workoutVersion);
        try {
            if (iDudeRepository.findById(dudeId).isEmpty()) {
                throw new NoSuchElementException("Could not find Dude with id: " + dudeId);
            } else if (iWorkoutRepository.findByIdAndVersion(workoutId, workoutVersion).isEmpty()) {
                throw new NoSuchElementException("Could not find Workout with id: " + workoutId);
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            if (workoutBookmarkRepository.checkWorkoutBookmark(dudeId, workoutId, workoutVersion) != 0) {
                throw new ServiceException("You already have this workout bookmarked!");
            }
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            workoutBookmarkRepository.saveWorkoutBookmark(dudeId, workoutId, workoutVersion);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteWorkoutBookmark(Long dudeId, Long workoutId, Integer workoutVersion) throws ServiceException {
        LOGGER.info("Entering deleteWorkoutBookmark with dudeId: " + dudeId + "; workoutId: " + workoutId + "; workoutVersion: " + workoutVersion);
        try {
            workoutBookmarkRepository.deleteWorkoutBookmark(dudeId, workoutId, workoutVersion);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
