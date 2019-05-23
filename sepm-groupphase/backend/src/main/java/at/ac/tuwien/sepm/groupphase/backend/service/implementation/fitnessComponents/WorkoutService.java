package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutExerciseKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.WorkoutExerciseInsertRepository;
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
    private final IWorkoutExerciseRepository iWorkoutExerciseRepository;
    private final WorkoutExerciseInsertRepository workoutExerciseInsertRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutService.class);

    public WorkoutService(IWorkoutRepository iWorkoutRepository, IWorkoutExerciseRepository iWorkoutExerciseRepository, WorkoutExerciseInsertRepository workoutExerciseInsertRepository) {
        this.iWorkoutRepository = iWorkoutRepository;
        this.iWorkoutExerciseRepository = iWorkoutExerciseRepository;
        this.workoutExerciseInsertRepository = workoutExerciseInsertRepository;
    }

    @Override
    public Workout save(Workout workout) throws ServiceException {
        LOGGER.info("Entering save for: " + workout);
        List<WorkoutExercise> workoutExercises = workout.getExercises();
        workout.setExercises(null);
        Workout savedWorkout;
        try {
            savedWorkout = iWorkoutRepository.save(workout);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        for (int i = 0; i < workoutExercises.size(); i++) {
            //workoutExercises.get(i).getId().getWorkoutId().setId(savedWorkout.getId());
            workoutExercises.get(i).setWorkoutId(savedWorkout.getId());

            //workoutExercises.get(i).getWorkout().setId(savedWorkout.getId());

            LOGGER.debug(workoutExercises.get(i).toString());
            try {
                iWorkoutExerciseRepository.save(workoutExercises.get(i));
                //workoutExerciseInsertRepository.insertWithQuery(workoutExercises.get(i));
            } catch (DataAccessException e) {
                throw new ServiceException(e.getMessage());
            }
        }
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
}
