package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutRating;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutRatingRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IWorkoutService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.WorkoutExerciseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WorkoutService implements IWorkoutService {

    private final IWorkoutRepository iWorkoutRepository;
    private final IExerciseRepository iExerciseRepository;
    private final IWorkoutExerciseRepository iWorkoutExerciseRepository;
    private final IDudeRepository iDudeRepository;
    private final IWorkoutRatingRepository iWorkoutRatingRepository;
    private final WorkoutExerciseValidator workoutExerciseValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutService.class);

    public WorkoutService(IWorkoutRepository iWorkoutRepository, IExerciseRepository iExerciseRepository, IWorkoutExerciseRepository iWorkoutExerciseRepository, IWorkoutRatingRepository iWorkoutRatingRepository, IDudeRepository iDudeRepository, WorkoutExerciseValidator workoutExerciseValidator) {
        this.iWorkoutRepository = iWorkoutRepository;
        this.iExerciseRepository = iExerciseRepository;
        this.iWorkoutExerciseRepository = iWorkoutExerciseRepository;
        this.iWorkoutRatingRepository = iWorkoutRatingRepository;
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
    public List<Workout> findByFilter(String filter, Integer difficulty, Double calorieLower, Double calorieUpper) throws ServiceException {
        LOGGER.info("Entering findByFilter with filter: " + filter + "; and difficulty: " + difficulty + "; calorieLower: " + calorieLower + "; calorieUpper: " + calorieUpper);
        try {
            if (difficulty != null) {
                return iWorkoutRepository.findByFilterWithDifficulty(filter, difficulty, calorieLower, calorieUpper);
            } else {
                return iWorkoutRepository.findByFilterWithoutDifficulty(filter, calorieLower, calorieUpper);
            }

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
            newWorkout.setId(id);
            newWorkout.setVersion(1+oldWorkout.getVersion());
            newWorkout.setRatingNum(oldWorkout.getRatingNum());
            newWorkout.setRatingSum(oldWorkout.getRatingSum());

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
    public void saveWorkoutRating(Long dudeId, Long workoutId, Integer rating) throws ServiceException {
        LOGGER.info("Entering saveWorkoutRating with dudeId: " + dudeId + "; workoutId: " + workoutId + "; rating: " + rating);
        // checks that rating is between 1 and 5
        try {
            if (rating < 1 || rating > 5) throw new ValidationException("Rating must be between 1 and 5");
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
        checkDudeWorkoutExist(dudeId, workoutId);

        WorkoutRating workoutRating = new WorkoutRating();
        workoutRating.setDudeId(dudeId);
        workoutRating.setWorkoutId(workoutId);
        workoutRating.setRating(rating);

        Workout workout = iWorkoutRepository.findById(workoutId);

        // checks that dude is not rating own content
        if (workout.getCreator().getId().equals(dudeId)){
            throw new ServiceException("You cannot rate your own content");
        }

        // checks if dude has rated this content before or not
        if (iWorkoutRatingRepository.findRating(dudeId,workoutId)==null){
            workout.setRatingSum(rating+workout.getRatingSum());
            workout.setRatingNum(1+workout.getRatingNum());

            iWorkoutRatingRepository.save(workoutRating);
            iWorkoutRepository.save(workout);
            System.out.println("Dude with id: " + dudeId + " rated Workout with id: " + workoutId + " with new rating: " + rating);
        } else{
            WorkoutRating foundRating = iWorkoutRatingRepository.findRating(dudeId, workoutId);
            workout.setRatingSum(rating + workout.getRatingSum() - foundRating.getRating());

            iWorkoutRatingRepository.save(workoutRating);
            iWorkoutRepository.save(workout);
            System.out.println("Dude with id: " + dudeId + " rated Workout with id: " + workoutId + " with updated rating: " + rating);
        }
    }

    @Override
    public void deleteWorkoutRating(Long dudeId, Long workoutId) throws ServiceException {
        LOGGER.info("Entering deleteWorkoutRating with dudeId: " + dudeId + "; workoutId: " + workoutId);
        WorkoutRating workoutRating = iWorkoutRatingRepository.findRating(dudeId,workoutId);

        if (workoutRating==null){
            throw new ServiceException("Could not find rating to delete with dude id: " + dudeId + " and workoutId " + workoutId);
        } else{
            checkDudeWorkoutExist(dudeId, workoutId);
            Workout workout = iWorkoutRepository.findById(workoutId);

            workout.setRatingSum(workout.getRatingSum() - workoutRating.getRating());
            workout.setRatingNum(workout.getRatingNum() - 1);

            try {
                iWorkoutRatingRepository.delete(workoutRating);
                iWorkoutRepository.save(workout);
            } catch (DataAccessException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

    private void checkDudeWorkoutExist(Long dudeId, Long workoutId) throws ServiceException {
        try {
            if (iDudeRepository.findById(dudeId).isEmpty()) {
                throw new NoSuchElementException("Could not find Dude with id: " + dudeId);
            } else if (iWorkoutRepository.findById(workoutId)==null) {
                throw new NoSuchElementException("Could not find Workout with id: " + workoutId);
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
