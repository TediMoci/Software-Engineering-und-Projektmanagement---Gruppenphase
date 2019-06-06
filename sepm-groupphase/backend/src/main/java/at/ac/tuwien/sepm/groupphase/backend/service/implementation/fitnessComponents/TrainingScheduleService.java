package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ActiveTrainingScheduleKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.TrainingScheduleWorkoutValidator;
import at.ac.tuwien.sepm.groupphase.backend.validators.TrainingScheduleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TrainingScheduleService implements ITrainingScheduleService {

    private final ITrainingScheduleRepository iTrainingScheduleRepository;
    private final ITrainingScheduleWorkoutRepository iTrainingScheduleWorkoutRepository;
    private final IWorkoutRepository iWorkoutRepository;
    private final IActiveTrainingScheduleRepository iActiveTrainingScheduleRepository;
    private final IExerciseDoneRepository iExerciseDoneRepository;
    private final TrainingScheduleValidator trainingScheduleValidator;
    private final TrainingScheduleWorkoutValidator trainingScheduleWorkoutValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingScheduleService.class);
    private static Map<Integer, List<Workout>> finalList = new HashMap<Integer, List<Workout>>();
    private static Integer listPosition = 0;

    public TrainingScheduleService(ITrainingScheduleRepository iTrainingScheduleRepository, ITrainingScheduleWorkoutRepository iTrainingScheduleWorkoutRepository, IWorkoutRepository iWorkoutRepository, IActiveTrainingScheduleRepository iActiveTrainingScheduleRepository, IExerciseDoneRepository iExerciseDoneRepository, TrainingScheduleValidator trainingScheduleValidator, TrainingScheduleWorkoutValidator trainingScheduleWorkoutValidator) {
        this.iTrainingScheduleRepository = iTrainingScheduleRepository;
        this.iTrainingScheduleWorkoutRepository = iTrainingScheduleWorkoutRepository;
        this.iWorkoutRepository = iWorkoutRepository;
        this.iActiveTrainingScheduleRepository = iActiveTrainingScheduleRepository;
        this.iExerciseDoneRepository = iExerciseDoneRepository;
        this.trainingScheduleValidator = trainingScheduleValidator;
        this.trainingScheduleWorkoutValidator = trainingScheduleWorkoutValidator;
    }

    @Override
    public TrainingSchedule save(TrainingSchedule trainingSchedule) throws ServiceException {
        LOGGER.info("Entering save for: " + trainingSchedule);
        List<TrainingScheduleWorkout> trainingScheduleWorkouts = trainingSchedule.getWorkouts();
        trainingSchedule.setWorkouts(null);

        TrainingSchedule savedTrainingSchedule;
        try {
            this.trainingScheduleValidator.validateTrainingSchedule(trainingSchedule);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            savedTrainingSchedule = iTrainingScheduleRepository.save(trainingSchedule);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        saveTrainingScheduleWorkouts(trainingScheduleWorkouts, savedTrainingSchedule);
        return savedTrainingSchedule;
    }

    @Override
    public ActiveTrainingSchedule saveActive(ActiveTrainingSchedule activeTrainingSchedule) throws ServiceException {
        LOGGER.info("Entering saveActive for: " + activeTrainingSchedule);
        try {
            if (iActiveTrainingScheduleRepository.findByDudeId(activeTrainingSchedule.getDudeId()).isPresent()) {
                throw new ServiceException("Dude already has an ActiveTrainingSchedule.");
            }
        } catch (NoSuchElementException e) {
            LOGGER.debug("Dude has no ActiveTrainingSchedule");
        }

        activeTrainingSchedule.setStartDate(LocalDate.now());
        ActiveTrainingSchedule savedActiveTrainingSchedule;
        try {
            savedActiveTrainingSchedule = iActiveTrainingScheduleRepository.save(activeTrainingSchedule);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }

        TrainingSchedule trainingSchedule;
        try {
            trainingSchedule = iTrainingScheduleRepository.findByIdAndVersion(savedActiveTrainingSchedule.getTrainingScheduleId(), savedActiveTrainingSchedule.getTrainingScheduleVersion()).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }

        List<TrainingScheduleWorkout> trainingScheduleWorkouts = trainingSchedule.getWorkouts();
        ExerciseDone.ExerciseDoneBuilder exerciseDoneBuilder;
        for (int i = 0; i < activeTrainingSchedule.getIntervalRepetitions(); i++) {
            for (TrainingScheduleWorkout trainingScheduleWorkout : trainingScheduleWorkouts) {
                for (WorkoutExercise workoutExercise : trainingScheduleWorkout.getWorkout().getExercises()) {
                    exerciseDoneBuilder = new ExerciseDone.ExerciseDoneBuilder();

                    exerciseDoneBuilder.dudeId(activeTrainingSchedule.getDudeId());
                    exerciseDoneBuilder.trainingScheduleId(activeTrainingSchedule.getTrainingScheduleId());
                    exerciseDoneBuilder.trainingScheduleVersion(activeTrainingSchedule.getTrainingScheduleVersion());
                    exerciseDoneBuilder.exerciseId(workoutExercise.getExerciseId());
                    exerciseDoneBuilder.exerciseVersion(workoutExercise.getExerciseVersion());
                    exerciseDoneBuilder.workoutId(workoutExercise.getWorkoutId());
                    exerciseDoneBuilder.workoutVersion(workoutExercise.getWorkoutVersion());
                    exerciseDoneBuilder.day(trainingScheduleWorkout.getDay() + i*trainingSchedule.getIntervalLength());
                    exerciseDoneBuilder.done(false);

                    try {
                        iExerciseDoneRepository.save(exerciseDoneBuilder.build());
                    } catch (DataAccessException e) {
                        throw new ServiceException(e.getMessage());
                    }
                }
            }
        }
        return savedActiveTrainingSchedule;
    }

    @Override
    public void markExercisesAsDone(ExerciseDone[] exerciseDones) throws ServiceException {
        LOGGER.info("Entering saveDone for: " + exerciseDones);
        try {
            for (int i = 0; i < exerciseDones.length; i++) {
                iExerciseDoneRepository.save(exerciseDones[i]);
            }
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void saveTrainingScheduleWorkouts(List<TrainingScheduleWorkout> trainingScheduleWorkouts, TrainingSchedule savedTrainingSchedule) throws ServiceException {
        for (int i = 0; i < trainingScheduleWorkouts.size(); i++) {
            trainingScheduleWorkouts.get(i).setTrainingScheduleId(savedTrainingSchedule.getId());
            trainingScheduleWorkouts.get(i).setTrainingScheduleVersion(savedTrainingSchedule.getVersion());
            try {
                iTrainingScheduleWorkoutRepository.save(trainingScheduleWorkouts.get(i));
            } catch (DataAccessException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

    @Override
    public TrainingSchedule saveRandom(int days, double minTarget, double maxTarget, TrainingSchedule trainingSchedule) throws ServiceException {
        LOGGER.info("Entering save for: " + trainingSchedule);
        trainingSchedule.setWorkouts(null);

        List<Workout> workouts = iWorkoutRepository.findAll();
        sum_up(workouts,minTarget,maxTarget,days);

        TrainingSchedule savedTrainingSchedule;
        try {
            savedTrainingSchedule = iTrainingScheduleRepository.save(trainingSchedule);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        saveRandomTrainingScheduleWorkout(days, finalList, savedTrainingSchedule);
        finalList.clear();
        listPosition = 0;
        return savedTrainingSchedule;
    }

    @Override
    public TrainingSchedule findById(long id) throws ServiceException {
        LOGGER.info("Entering findById with id: " + id);
        try {
            TrainingSchedule trainingSchedule = iTrainingScheduleRepository.findById(id);
            if (trainingSchedule == null) throw new ServiceException("Could not find Training Schedule with id: " + id);
            return trainingSchedule;
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        LOGGER.info("Deleting training schedule with id: " + id);
        try {
            TrainingSchedule trainingSchedule = iTrainingScheduleRepository.findById(id);
            if (trainingSchedule == null) throw new ServiceException("Could not find Training Schedule with id: " + id);
            iTrainingScheduleRepository.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteActive(Long dudeId) throws ServiceException {
        LOGGER.info("Entering deleteActive with dudeId: " + dudeId);
        ActiveTrainingSchedule activeTrainingSchedule;
        try {
            activeTrainingSchedule = iActiveTrainingScheduleRepository.findByDudeId(dudeId).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }

        // TODO (Amir): stat-calculations and -saving for activeTrainingSchedule

        try {
            iActiveTrainingScheduleRepository.deleteByDudeId(dudeId);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public TrainingSchedule update(long id, TrainingSchedule newTraining) throws ServiceException {
        LOGGER.info("Updating training schedule with id: " + id);
        try {
            TrainingSchedule oldTraining = iTrainingScheduleRepository.findById(id);
            if (oldTraining == null) throw new ServiceException("Could not find training schedule with id: " + id);
            newTraining.setId(id);
            newTraining.setVersion(1+oldTraining.getVersion());
            iTrainingScheduleRepository.delete(id);

            List<TrainingScheduleWorkout> trainingWorkouts = newTraining.getWorkouts();
            newTraining.setWorkouts(null);
            validateTrainingScheduleWorkouts(trainingWorkouts);

            Long dbId = iTrainingScheduleRepository.save(newTraining).getId();
            iTrainingScheduleRepository.updateNew(newTraining.getId(), dbId);
            saveTrainingScheduleWorkouts(trainingWorkouts, newTraining);

            return newTraining;
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void validateTrainingScheduleWorkouts(List<TrainingScheduleWorkout> trainingScheduleWorkouts) throws ServiceException {
        for (TrainingScheduleWorkout trainingScheduleWorkout : trainingScheduleWorkouts) {
            try {
                trainingScheduleWorkoutValidator.validateTrainingScheduleWorkout(trainingScheduleWorkout);
                if (iWorkoutRepository.findByIdAndVersion(trainingScheduleWorkout.getWorkoutId(), trainingScheduleWorkout.getWorkoutVersion()).isEmpty()) {
                    throw new NoSuchElementException();
                }
            } catch (NoSuchElementException e) {
                throw new ServiceException("Workout with id: " + trainingScheduleWorkout.getWorkout() + " and version: " + trainingScheduleWorkout.getWorkoutVersion() + " does not exist");
            } catch (ValidationException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

    private void saveRandomTrainingScheduleWorkout(int days,  Map<Integer, List<Workout>> list, TrainingSchedule savedTrainingSchedule) throws ServiceException {
        if (list.isEmpty()) throw new ServiceException("List is empty");

        for (int a=1; a<=days; a++) {
            int x = (a-1)%list.size();
            for (int b=0; b<list.get(x).size();b++){
                Workout w = list.get(x).get(b);
                TrainingScheduleWorkout tsW = new TrainingScheduleWorkout();
                tsW.setTrainingScheduleId(savedTrainingSchedule.getId());
                tsW.setTrainingScheduleVersion(savedTrainingSchedule.getVersion());
                tsW.setWorkoutId(w.getId());
                tsW.setWorkoutVersion(w.getVersion());
                tsW.setDay(a);

                try{
                    iTrainingScheduleWorkoutRepository.save(tsW);
                } catch (DataAccessException e) {
                    throw new ServiceException(e.getMessage());
                }
            }
        }
    }

    private static void sum_up(List<Workout> numbers, double minTarget, double maxTarget, int days) throws ServiceException {
        if (days<1 || days>7){
            throw new ServiceException("Please give a valid number of days per week");
        }
        if (minTarget>maxTarget){
            throw new ServiceException("Please give the correct minimum and maximum values");
        }
        sum_up_recursive(numbers,minTarget,maxTarget,new ArrayList<Workout>(),days);
        if (finalList.isEmpty()) throw new ServiceException("Training schedule could not be formed with existing workouts");
    }

    private static void sum_up_recursive(List<Workout> numbers, double minTarget, double maxTarget, List<Workout> partial, int days) {
        if (listPosition == days) return;
        double s = 0.0;
        for (Workout x : partial) s += x.getCalorieConsumption();

        //if sum equals target, put it in list
        if (s <= maxTarget && s >= minTarget) {
            finalList.put(listPosition, partial);
            listPosition++;
        }

        //if sum larger than target, return
        if (s >= maxTarget) return;

        for (int i = 0; i < numbers.size(); i++) {
            //iterate over the next remaining numbers in list
            List<Workout> remaining = new ArrayList<Workout>();
            Workout n = numbers.get(i);
            for (int j = i + 1; j < numbers.size(); j++) {
                remaining.add(numbers.get(j));
            }
            //put elements of n in partial list one by one
            List<Workout> partial_rec = new ArrayList<Workout>(partial);
            partial_rec.add(n);
            sum_up_recursive(remaining, minTarget, maxTarget, partial_rec, days);
        }
    }
}
