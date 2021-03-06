package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutExerciseKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.*;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.helperStructure.WorkoutExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.helperStructure.WorkoutExerciseHelp;
import at.ac.tuwien.sepm.groupphase.backend.helperStructure.WorkoutHelp;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IWorkoutService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.TrainingScheduleWorkoutValidator;
import at.ac.tuwien.sepm.groupphase.backend.validators.TrainingScheduleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TrainingScheduleService implements ITrainingScheduleService {

    private final ITrainingScheduleRepository iTrainingScheduleRepository;
    private final ITrainingScheduleWorkoutRepository iTrainingScheduleWorkoutRepository;
    private final TrainingScheduleBookmarkRepository trainingScheduleBookmarkRepository;
    private final IWorkoutRepository iWorkoutRepository;
    private final IActiveTrainingScheduleRepository iActiveTrainingScheduleRepository;
    private final IExerciseDoneRepository iExerciseDoneRepository;
    private final IDudeRepository iDudeRepository;
    private final ITrainingScheduleRatingRepository iTrainingScheduleRatingRepository;
    private final IWorkoutExerciseRepository iWorkoutExerciseRepository;
    private final IExerciseRepository iExerciseRepository;
    private final IFinishedTrainingScheduleRepository iFinishedTrainingScheduleRepository;
    private final TrainingScheduleValidator trainingScheduleValidator;
    private final TrainingScheduleWorkoutValidator trainingScheduleWorkoutValidator;
    private final IWorkoutService workoutService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingScheduleService.class);
    private static Map<Integer, List<Workout>> finalList = new HashMap<Integer, List<Workout>>();
    private static Integer listPosition = 0;

    // ----------------------------------------- start of variables for training schedule adaption -----------------------------------------

    // list of all exercises
    private List<WorkoutExerciseHelp> allExercises = new ArrayList<>();
    //list of all workouts
    private List<WorkoutHelp> allWorkouts = new ArrayList<>();

    private int t; // current intervalrepetition
    private double a; // percentage for the first repetition of the interval, initial change of x%, on average x% change per interval
    private double s; // maximum percentage for training schedule adaption
    private double totalChangePerDayInPercent; // total percent of change per day calculated by logistic growth function
    private double k; // growth constant calculated for logistic growth function
    private double bt; // percent per interval for intervalrepetition t
    private int intervalLength;
    private int intervalRepetitions;
    private int selfAssessment;

    // number of exercises per day of interval
    private int[] numOfExPerDay = new int[]{0, 0, 0, 0, 0, 0, 0};

    // list of workoutExercise per day with information done/not done
    private List<WorkoutExerciseDone> waExDay1 = new ArrayList<>();
    private List<WorkoutExerciseDone> waExDay2 = new ArrayList<>();
    private List<WorkoutExerciseDone> waExDay3 = new ArrayList<>();
    private List<WorkoutExerciseDone> waExDay4 = new ArrayList<>();
    private List<WorkoutExerciseDone> waExDay5 = new ArrayList<>();
    private List<WorkoutExerciseDone> waExDay6 = new ArrayList<>();
    private List<WorkoutExerciseDone> waExDay7 = new ArrayList<>();

    // total number of exercises in trainingScheduleInterval
    private int totalEx = 0;
    // total number of exercises with category strength per interval
    private int categoryStrength = 0;

    // ----------------------------------------- end of variables for training schedule adaption -----------------------------------------

    public TrainingScheduleService(ITrainingScheduleRatingRepository iTrainingScheduleRatingRepository, IWorkoutService workoutService, IExerciseRepository iExerciseRepository, IFinishedTrainingScheduleRepository iFinishedTrainingScheduleRepository, TrainingScheduleBookmarkRepository trainingScheduleBookmarkRepository, IWorkoutExerciseRepository iWorkoutExerciseRepository, IDudeRepository iDudeRepository, ITrainingScheduleRepository iTrainingScheduleRepository, ITrainingScheduleWorkoutRepository iTrainingScheduleWorkoutRepository, IWorkoutRepository iWorkoutRepository, IActiveTrainingScheduleRepository iActiveTrainingScheduleRepository, IExerciseDoneRepository iExerciseDoneRepository, TrainingScheduleValidator trainingScheduleValidator, TrainingScheduleWorkoutValidator trainingScheduleWorkoutValidator) {
        this.workoutService = workoutService;
        this.iTrainingScheduleRepository = iTrainingScheduleRepository;
        this.iTrainingScheduleWorkoutRepository = iTrainingScheduleWorkoutRepository;
        this.trainingScheduleBookmarkRepository = trainingScheduleBookmarkRepository;
        this.iWorkoutExerciseRepository = iWorkoutExerciseRepository;
        this.iWorkoutRepository = iWorkoutRepository;
        this.iExerciseRepository = iExerciseRepository;
        this.iActiveTrainingScheduleRepository = iActiveTrainingScheduleRepository;
        this.iExerciseDoneRepository = iExerciseDoneRepository;
        this.iDudeRepository = iDudeRepository;
        this.iTrainingScheduleRatingRepository = iTrainingScheduleRatingRepository;
        this.iFinishedTrainingScheduleRepository = iFinishedTrainingScheduleRepository;
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
    public ActiveTrainingSchedule updateActive(ActiveTrainingSchedule activeTrainingSchedule) throws ServiceException {
        try {
            iActiveTrainingScheduleRepository.updateActiveTrainingSchedule(activeTrainingSchedule.getDudeId(), activeTrainingSchedule.getTrainingScheduleId(), activeTrainingSchedule.getTrainingScheduleVersion(), activeTrainingSchedule.getStartDate());
            return iActiveTrainingScheduleRepository.findById(activeTrainingSchedule.getId());
        }  catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public ActiveTrainingSchedule saveActive(ActiveTrainingSchedule activeTrainingSchedule) throws ServiceException {
        LOGGER.info("Entering saveActive for: " + activeTrainingSchedule);
        try {
            if (iDudeRepository.findById(activeTrainingSchedule.getDudeId()).get().getActiveTrainingSchedule() != null) {
                throw new ServiceException("You already have an active training schedule!");
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException("No Dude in database with id: " + activeTrainingSchedule.getDudeId());
        }

        if(activeTrainingSchedule.getStartDate() == null) {
            activeTrainingSchedule.setStartDate(LocalDate.now());
        }

        List<Boolean> hasBeenAdapted = new ArrayList<>();
        for (int i = 0; i < activeTrainingSchedule.getIntervalRepetitions() - 1; i++) {
            hasBeenAdapted.add(false);
        }
        activeTrainingSchedule.setHasBeenAdapted(hasBeenAdapted);
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
                    if (activeTrainingSchedule.getAdaptive() && (trainingScheduleWorkout.getDay() + i * trainingSchedule.getIntervalLength()) > trainingSchedule.getIntervalLength()) {
                        continue;
                    }
                    exerciseDoneBuilder = new ExerciseDone.ExerciseDoneBuilder();

                    exerciseDoneBuilder.activeTrainingScheduleId(savedActiveTrainingSchedule.getId());
                    exerciseDoneBuilder.dudeId(activeTrainingSchedule.getDudeId());
                    exerciseDoneBuilder.trainingScheduleId(activeTrainingSchedule.getTrainingScheduleId());
                    exerciseDoneBuilder.trainingScheduleVersion(activeTrainingSchedule.getTrainingScheduleVersion());
                    exerciseDoneBuilder.exerciseId(workoutExercise.getExerciseId());
                    exerciseDoneBuilder.exerciseVersion(workoutExercise.getExerciseVersion());
                    exerciseDoneBuilder.workoutId(workoutExercise.getWorkoutId());
                    exerciseDoneBuilder.workoutVersion(workoutExercise.getWorkoutVersion());
                    exerciseDoneBuilder.day(trainingScheduleWorkout.getDay() + i * trainingSchedule.getIntervalLength());
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
    public TrainingSchedule saveRandom(int days, int duration, double minTarget, double maxTarget, TrainingSchedule trainingSchedule, boolean lowerDifficulty) throws ServiceException {
        LOGGER.info("Entering save for: " + trainingSchedule);
        trainingSchedule.setWorkouts(null);
        trainingSchedule.setIsPrivate(true);
        List<Workout> workouts = new ArrayList<>();

        if (lowerDifficulty) {
            workouts.addAll(iWorkoutRepository.findByLowerDifficulty(trainingSchedule.getDifficulty(), maxTarget));
        } else {
            workouts.addAll(iWorkoutRepository.findByDifficulty(trainingSchedule.getDifficulty(), maxTarget));
        }

        sum_up(workouts, minTarget, maxTarget, days, duration);

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

        int strength = 0;
        int endurance = 0;
        int other = 0;
        double strengthPercent;
        double endurancePercent;
        double otherPercent;
        double totalCalories = 0;
        double totalDuration = 0;
        double totalHours;
        int totalDays = 0;
        int totalIntervalRepetitions;
        TrainingSchedule originalTs = new TrainingSchedule();
        boolean foundOriginal = false;

        try {
            activeTrainingSchedule = iDudeRepository.findById(dudeId).get().getActiveTrainingSchedule();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }

        try {
            // delete exerciseDone from to delete activeTrainingSchedule
            List<TrainingSchedule> copiedTs = new ArrayList<>();
            for (int i = 0; i < activeTrainingSchedule.getDone().size(); i++) {
                int maxDayNotChosen = activeTrainingSchedule.getTrainingSchedule().getIntervalLength();
                if ((activeTrainingSchedule.getAdaptive()) && (!copiedTs.contains(activeTrainingSchedule.getDone().get(i).getTrainingSchedule())) && (activeTrainingSchedule.getDone().get(i).getDay() > maxDayNotChosen)) {
                    copiedTs.add(activeTrainingSchedule.getDone().get(i).getTrainingSchedule());
                }

                if (activeTrainingSchedule.getDone().get(i).getDone()) {
                    totalCalories += activeTrainingSchedule.getDone().get(i).getWorkout().getCalorieConsumption();
                    totalDuration += activeTrainingSchedule.getDone().get(i).getWorkoutExercise().getExDuration();
                    totalDays++;
                    if (activeTrainingSchedule.getDone().get(i).getExercise().getCategory() == Category.Strength) {
                        strength++;
                    }
                    if (activeTrainingSchedule.getDone().get(i).getExercise().getCategory() == Category.Endurance) {
                        endurance++;
                    }
                    if (activeTrainingSchedule.getDone().get(i).getExercise().getCategory() == Category.Other) {
                        other++;
                    }
                }

                if (!foundOriginal && activeTrainingSchedule.getDone().get(i).getDay() == 1) {
                    originalTs = activeTrainingSchedule.getDone().get(i).getTrainingSchedule();
                    foundOriginal = true;
                }

                iExerciseDoneRepository.delete(activeTrainingSchedule.getDone().get(i));
            }

            totalHours = totalDuration / 60.0;
            strengthPercent = totalDays == 0 ? 0 : ((double) strength / totalDays) * 100.0;
            endurancePercent = totalDays == 0 ? 0 : ((double) endurance / totalDays) * 100.0;
            otherPercent = totalDays == 0 ? 0 : ((double) other / totalDays) * 100.0;
            LocalDate startDate = LocalDate.from(activeTrainingSchedule.getStartDate());
            totalIntervalRepetitions = (int) startDate.until(LocalDate.now(), ChronoUnit.DAYS) / originalTs.getIntervalLength();

            FinishedTrainingScheduleStats.FinishedTrainingScheduleStatsBuilder result = new FinishedTrainingScheduleStats.FinishedTrainingScheduleStatsBuilder();
            result.trainingSchedule(originalTs);
            result.dude(activeTrainingSchedule.getDude());
            result.totalHours(totalHours);
            result.totalDays(totalDays);
            result.totalCalorieConsumption(totalCalories);
            result.totalIntervalRepetitions(totalIntervalRepetitions);
            result.strengthPercent(strengthPercent);
            result.endurancePercent(endurancePercent);
            result.otherPercent(otherPercent);

            iFinishedTrainingScheduleRepository.save(result.build());

            iActiveTrainingScheduleRepository.deleteByDudeId(dudeId);
            for (TrainingSchedule t : copiedTs) {
                LOGGER.debug("delete trainingSchedule with id " + t.getId());
                iTrainingScheduleRepository.deleteById(t.getId());
            }
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<TrainingSchedule> findByName(String name, Long dudeId) throws ServiceException {
        LOGGER.info("Entering findByName with name: " + name + "; dudeId: " + dudeId);
        Dude dude = new Dude();
        dude.setId(dudeId);
        List<TrainingSchedule> trainingSchedules;
        try {
            trainingSchedules = iTrainingScheduleRepository.findByName(name);
            trainingSchedules.addAll(iTrainingScheduleRepository.findOwnPrivateByName(name, dude));
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        return trainingSchedules;
    }

    @Override
    public TrainingSchedule findByIdAndVersion(Long id, Integer version) throws ServiceException {
        LOGGER.info("Entering findByIdAndVersion with id: " + id + "; and version: " + version);
        try {
            return iTrainingScheduleRepository.findByIdAndVersion(id, version).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<TrainingScheduleWorkout> findByTrainingScheduleIdVersionAndDay(Long id, Integer version, Integer day) throws ServiceException {
        LOGGER.info("Entering findByTrainingScheduleIdVersionAndDay with id: " + id + "; and version: " + version + "; and day: " + day);
        try {
            iTrainingScheduleRepository.findByIdAndVersion(id, version).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            return iTrainingScheduleWorkoutRepository.findByTrainingScheduleIdVersionAndDay(id, version, day);
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
            newTraining.setRatingNum(oldTraining.getRatingNum());
            newTraining.setRatingSum(oldTraining.getRatingSum());

            List<TrainingScheduleWorkout> trainingWorkouts = newTraining.getWorkouts();
            newTraining.setWorkouts(null);
            validateTrainingScheduleWorkouts(trainingWorkouts);

            iTrainingScheduleRepository.delete(id);
            Long dbId = iTrainingScheduleRepository.save(newTraining).getId();
            iTrainingScheduleRepository.updateNew(newTraining.getId(), dbId);
            saveTrainingScheduleWorkouts(trainingWorkouts, newTraining);

            return newTraining;
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<TrainingSchedule> findByFilter(String filter, Integer difficulty, Integer intervalLength, Long dudeId) throws ServiceException {
        LOGGER.info("Entering findByFilter with filter: " + filter + "; and difficulty: " + difficulty + "; intervalLength: " + intervalLength + "; dudeId: " + dudeId);
        Dude dude = new Dude();
        dude.setId(dudeId);
        List<TrainingSchedule> trainingSchedules;
        try {
            if (difficulty != null && intervalLength != null) {
                trainingSchedules = iTrainingScheduleRepository.findByFilterWithDifficultyAndInterval(filter, difficulty, intervalLength);
                trainingSchedules.addAll(iTrainingScheduleRepository.findOwnPrivateByFilterWithDifficultyAndInterval(filter, difficulty, intervalLength, dude));
            } else if (difficulty != null && intervalLength == null) {
                trainingSchedules = iTrainingScheduleRepository.findByFilterWithDifficulty(filter, difficulty);
                trainingSchedules.addAll(iTrainingScheduleRepository.findOwnPrivateByFilterWithDifficulty(filter, difficulty, dude));
            } else if (difficulty == null & intervalLength != null) {
                trainingSchedules = iTrainingScheduleRepository.findByFilterWithInterval(filter, intervalLength);
                trainingSchedules.addAll(iTrainingScheduleRepository.findOwnPrivateByFilterWithInterval(filter, intervalLength, dude));
            } else {
                trainingSchedules = iTrainingScheduleRepository.findByFilterWithoutDifficultyAndInterval(filter);
                trainingSchedules.addAll(iTrainingScheduleRepository.findOwnPrivateByFilterWithoutDifficultyAndInterval(filter, dude));
            }
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        return trainingSchedules;
    }

    @Override
    public void saveTrainingScheduleRating(Long dudeId, Long trainingScheduleId, Integer rating) throws ServiceException {
        LOGGER.info("Entering saveTrainingScheduleRating with dudeId: " + dudeId + "; trainingScheduleId: " + trainingScheduleId + "; rating: " + rating);
        // checks that rating is between 1 and 5
        try {
            if (rating < 1 || rating > 5) throw new ValidationException("Rating must be between 1 and 5");
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
        checkDudeTrainingScheduleExist(dudeId,trainingScheduleId);

        TrainingScheduleRating trainingScheduleRating = new TrainingScheduleRating();
        trainingScheduleRating.setDudeId(dudeId);
        trainingScheduleRating.setTrainingScheduleId(trainingScheduleId);
        trainingScheduleRating.setRating(rating);

        TrainingSchedule trainingSchedule = iTrainingScheduleRepository.findById(trainingScheduleId);

        // checks that dude is not rating own content
        if (trainingSchedule.getCreator().getId().equals(dudeId)){
            throw new ServiceException("You cannot rate your own content");
        }

        // checks if dude has rated this content before or not
        if (iTrainingScheduleRatingRepository.findRating(dudeId,trainingScheduleId)==null){
            trainingSchedule.setRatingSum(rating+trainingSchedule.getRatingSum());
            trainingSchedule.setRatingNum(1+trainingSchedule.getRatingNum());

            iTrainingScheduleRatingRepository.save(trainingScheduleRating);
            iTrainingScheduleRepository.save(trainingSchedule);
            System.out.println("Dude with id: " + dudeId + " rated TrainingSchedule with id: " + trainingScheduleId + " with new rating: " + rating);
        } else{
            TrainingScheduleRating foundRating = iTrainingScheduleRatingRepository.findRating(dudeId, trainingScheduleId);
            trainingSchedule.setRatingSum(rating + trainingSchedule.getRatingSum() - foundRating.getRating());

            iTrainingScheduleRatingRepository.save(trainingScheduleRating);
            iTrainingScheduleRepository.save(trainingSchedule);
            System.out.println("Dude with id: " + dudeId + " rated TrainingSchedule with id: " + trainingScheduleId + " with updated rating: " + rating);
        }
    }

    private void checkDudeTrainingScheduleExist(Long dudeId, Long trainingScheduleId) throws ServiceException {
        try {
            if (iDudeRepository.findById(dudeId).isEmpty()) {
                throw new NoSuchElementException("Could not find Dude with id: " + dudeId);
            } else if (iTrainingScheduleRepository.findById(trainingScheduleId)==null) {
                throw new NoSuchElementException("Could not find Workout with id: " + trainingScheduleId);
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteTrainingScheduleRating(Long dudeId, Long trainingScheduleId) throws ServiceException {
        LOGGER.info("Entering deleteTrainingScheduleRating with dudeId: " + dudeId + "; trainingScheduleId: " + trainingScheduleId);
        TrainingScheduleRating trainingScheduleRating = iTrainingScheduleRatingRepository.findRating(dudeId,trainingScheduleId);

        if (trainingScheduleRating==null){
            throw new ServiceException("Could not find rating to delete with dude id: " + dudeId + " and trainingSchedule " + trainingScheduleId);
        } else{
            checkDudeTrainingScheduleExist(dudeId,trainingScheduleId);
            TrainingSchedule trainingSchedule = iTrainingScheduleRepository.findById(trainingScheduleId);
            trainingSchedule.setRatingSum(trainingSchedule.getRatingSum() - trainingScheduleRating.getRating());
            trainingSchedule.setRatingNum(trainingSchedule.getRatingNum() - 1);

            try {
                iTrainingScheduleRatingRepository.delete(trainingScheduleRating);
                iTrainingScheduleRepository.save(trainingSchedule);
            } catch (DataAccessException e) {
                throw new ServiceException(e.getMessage());
            }
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

    private void saveRandomTrainingScheduleWorkout(int days, Map<Integer, List<Workout>> list, TrainingSchedule savedTrainingSchedule) throws ServiceException {
        if (list.isEmpty()) throw new ServiceException("List is empty");

        for (int a = 1; a <= days; a++) {
            int x = (a - 1) % list.size();
            for (int b = 0; b < list.get(x).size(); b++) {
                Workout w = list.get(x).get(b);
                TrainingScheduleWorkout tsW = new TrainingScheduleWorkout();
                tsW.setTrainingScheduleId(savedTrainingSchedule.getId());
                tsW.setTrainingScheduleVersion(savedTrainingSchedule.getVersion());
                tsW.setWorkoutId(w.getId());
                tsW.setWorkoutVersion(w.getVersion());
                tsW.setDay(a);

                try {
                    iTrainingScheduleWorkoutRepository.save(tsW);
                } catch (DataAccessException e) {
                    throw new ServiceException(e.getMessage());
                }
            }
        }
    }

    private static void sum_up(List<Workout> numbers, double minTarget, double maxTarget, int days, int duration) throws ServiceException {
        if (duration < 1 || duration > 1440) throw new ServiceException("Please give a valid duration per day");
        if (days < 1 || days > 7) throw new ServiceException("Please give a valid number of days per week");
        if (minTarget > maxTarget) throw new ServiceException("Please give the correct minimum and maximum values");

        sum_up_recursive(numbers, minTarget, maxTarget, new ArrayList<Workout>(), days, duration);
        if (finalList.isEmpty())
            throw new ServiceException("Training schedule could not be formed with existing workouts");
    }

    private static void sum_up_recursive(List<Workout> numbers, double minTarget, double maxTarget, List<Workout> partial, int days, int duration) {
        if (listPosition == days) return;
        double s = 0.0;
        int allDuration = 0;

        for (Workout x : partial) {
            allDuration += getDuration(x);
            s += x.getCalorieConsumption();
        }

        //if sum equals target, put it in list
        if (s <= maxTarget && s >= minTarget && allDuration <= duration) {
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
            sum_up_recursive(remaining, minTarget, maxTarget, partial_rec, days, duration);
        }
    }

    private static int getDuration(Workout workout) {
        int allDuration = 0;
        List<WorkoutExercise> workoutExercises = workout.getExercises();
        for (WorkoutExercise w : workoutExercises) {
            allDuration = allDuration + w.getExDuration();
        }
        return allDuration;
    }

    @Override
    public void saveTrainingScheduleBookmark(Long dudeId, Long trainingScheduleId, Integer trainingScheduleVersion) throws ServiceException {
        LOGGER.info("Entering saveTrainingScheduleBookmark with dudeId: " + dudeId + "; trainingScheduleId: " + trainingScheduleId + "; trainingScheduleVersion: " + trainingScheduleVersion);
        try {
            if (iDudeRepository.findById(dudeId).isEmpty()) {
                throw new NoSuchElementException("Could not find Dude with id: " + dudeId);
            } else if (iTrainingScheduleRepository.findByIdAndVersion(trainingScheduleId, trainingScheduleVersion).isEmpty()) {
                throw new NoSuchElementException("Could not find TrainingSchedule with id: " + trainingScheduleId);
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            if (trainingScheduleBookmarkRepository.checkTrainingScheduleBookmark(dudeId, trainingScheduleId, trainingScheduleVersion) != 0) {
                throw new ServiceException("You already have this training schedule bookmarked!");
            }
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            trainingScheduleBookmarkRepository.saveTrainingScheduleBookmark(dudeId, trainingScheduleId, trainingScheduleVersion);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteTrainingScheduleBookmark(Long dudeId, Long trainingScheduleId, Integer trainingScheduleVersion) throws ServiceException {
        LOGGER.info("Entering deleteTrainingScheduleBookmark with dudeId: " + dudeId + "; trainingScheduleId: " + trainingScheduleId + "; trainingScheduleVersion: " + trainingScheduleVersion);
        try {
            trainingScheduleBookmarkRepository.deleteTrainingScheduleBookmark(dudeId, trainingScheduleId, trainingScheduleVersion);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    // ----------------------------------------- start of methods for adapting training schedule automatically -----------------------------------------

    @Override
    public ActiveTrainingSchedule calculatePercentageOfChangeForInterval(ActiveTrainingSchedule activeSchedule, Dude dude, int interval) throws ServiceException {

        resetVariables();
        // old trainingSchedule
        TrainingSchedule oldTs = activeSchedule.getTrainingSchedule();
        LocalDate startDate = activeSchedule.getStartDate();
        intervalLength = oldTs.getIntervalLength();
        selfAssessment = dude.getSelfAssessment();
        intervalRepetitions = activeSchedule.getIntervalRepetitions();

        //t = ((Period.between(startDate, LocalDate.now()).getDays()) / oldTs.getIntervalLength()) + 1;
        t = interval;
        LOGGER.debug("IntervalNumber: " + t);
        if (t == 1) {
            return activeSchedule;
        }

        // copy of old trainingSchedule, which will be altered
        TrainingSchedule ts = copyOldTrainingSchedule(activeSchedule, activeSchedule.getDudeId(), oldTs);
        LOGGER.debug("Successfully copied old TrainingSchedule");

        List<TrainingScheduleWorkout> workouts = iTrainingScheduleWorkoutRepository.findByTrainingSchedule(ts);
        Exercise exercise;
        for (TrainingScheduleWorkout w : workouts) {
            Workout wa = iWorkoutRepository.findByIdAndVersion(w.getWorkoutId(), w.getWorkoutVersion()).get();
            List<WorkoutExercise> waEx = iWorkoutExerciseRepository.findByWorkoutIdAndWorkoutVersion(wa.getId(), wa.getVersion());
            for (WorkoutExercise wex : waEx) {
                wex.setWorkout(wa);
                exercise = iExerciseRepository.findByIdAndVersion(wex.getExerciseId(), wex.getExerciseVersion()).get();
                wex.setExercise(exercise);
            }
            wa.setExercises(waEx);
            w.setWorkout(wa);
        }
        ts.setWorkouts(workouts);

        LOGGER.debug("Count values needed for adaptive calculations of workouts");
        for (TrainingScheduleWorkout tsWa : workouts) {
            countExToAdaptTotalExAndStrengthEx(tsWa, tsWa.getDay());
        }

        boolean strengthFocused = false;
        if (totalEx != 0) {
            if (categoryStrength / totalEx > 0.7) {
                strengthFocused = true;
            }
        }

        if (selfAssessment == 1) {
            a = 1.5;
        } else {
            a = 3;
        }

        if (strengthFocused) {
            if (selfAssessment == 1) {
                if ((intervalRepetitions * a) > 5) {
                    s = 5;
                } else {
                    s = (intervalRepetitions * a);
                }
            } else {
                if ((intervalRepetitions * a) > 10) {
                    s = 10;
                } else {
                    s = (intervalRepetitions * a);
                }
            }
        } else {
            if (selfAssessment == 1) {
                if ((intervalRepetitions * a) > 10) {
                    s = 10;
                } else {
                    s = (intervalRepetitions * a);
                }
            } else {
                if ((intervalRepetitions * a) > 30) {
                    s = 30;
                } else {
                    s = (intervalRepetitions * a);
                }
            }
        }

        LOGGER.debug("Calculate percentage of change per interval with logistic growth function");
        // logistic growth function to generate percentage of change compared to the inital data
        k = Math.log((a * s - (s - a) * a) / ((s - a) * (s - a))) / -(s * (intervalRepetitions - 1)); // growth constant, calculated for model according to other parameters
        bt = Math.floor((a * s) / (a + (s - a) * Math.exp(-s * k * (t - 1)))); // result: percentage of change in the t-th interval

        /*
         * Percentage of change per interval is applied for each day containing workouts
         *  - percentage is split equally for all exercises of each day
         *  example: Day 1, 3 Workouts with 2 Exercises each, 3% change => 6 Exercises total => 3/6 = 0.5% change per Exercise
         *  - adaption is applied in this way:
         *  bankersRounding(oldNumberOfExerciseRepetitions + (percentageOfChangePerExercise * oldNumberOfExerciseRepetitions))
         *  - only repetitions are altered until maximumRepetitions are reached,
         *    if maximumRepetitions are reached, number of sets is altered and repetitions are set to default minimum
         */

        if (t == 2) {
            totalChangePerDayInPercent = bt;
        } else {
            totalChangePerDayInPercent = bt - (Math.floor((a * s) / (a + (s - a) * Math.exp(-s * k * (t - 2)))));
        }

        LOGGER.debug("Calculate adaptive change percent per exercise per day");
        // spread percentage of change equally on all exercises per day
        double percentPerExDay1 = (numOfExPerDay[0] == 0 ? 0 : (totalChangePerDayInPercent / numOfExPerDay[0]));
        double percentPerExDay2 = (numOfExPerDay[1] == 0 ? 0 : (totalChangePerDayInPercent / numOfExPerDay[1]));
        double percentPerExDay3 = (numOfExPerDay[2] == 0 ? 0 : (totalChangePerDayInPercent / numOfExPerDay[2]));
        double percentPerExDay4 = (numOfExPerDay[3] == 0 ? 0 : (totalChangePerDayInPercent / numOfExPerDay[3]));
        double percentPerExDay5 = (numOfExPerDay[4] == 0 ? 0 : (totalChangePerDayInPercent / numOfExPerDay[4]));
        double percentPerExDay6 = (numOfExPerDay[5] == 0 ? 0 : (totalChangePerDayInPercent / numOfExPerDay[5]));
        double percentPerExDay7 = (numOfExPerDay[6] == 0 ? 0 : (totalChangePerDayInPercent / numOfExPerDay[6]));

        // change number of sets and repetitions according to percentage of change per day
        LOGGER.debug("Apply adaptive change to workoutExercises");
        applyAdaptiveChange(waExDay1, percentPerExDay1, 1);
        applyAdaptiveChange(waExDay2, percentPerExDay2, 2);
        applyAdaptiveChange(waExDay3, percentPerExDay3, 3);
        applyAdaptiveChange(waExDay4, percentPerExDay4, 4);
        applyAdaptiveChange(waExDay5, percentPerExDay5, 5);
        applyAdaptiveChange(waExDay6, percentPerExDay6, 6);
        applyAdaptiveChange(waExDay7, percentPerExDay7, 7);

        LOGGER.debug("Apply adaptive change to workouts");
        adaptWorkouts(allWorkouts);

        ExerciseDone.ExerciseDoneBuilder builderExDone = new ExerciseDone.ExerciseDoneBuilder();
        for (int j = 0; j < allExercises.size(); j++) {

            builderExDone.dudeId(dude.getId());
            builderExDone.trainingScheduleId(ts.getId());
            builderExDone.trainingScheduleVersion(ts.getVersion());
            builderExDone.activeTrainingScheduleId(activeSchedule.getId());
            builderExDone.exerciseId(allExercises.get(j).getWorkoutExercise().getExerciseId());
            builderExDone.exerciseVersion(allExercises.get(j).getWorkoutExercise().getExerciseVersion());
            builderExDone.workoutId(allExercises.get(j).getWorkoutExercise().getWorkoutId());
            builderExDone.workoutVersion(allExercises.get(j).getWorkoutExercise().getWorkoutVersion());
            builderExDone.day(allExercises.get(j).getDay() + (t - 1) * intervalLength);
            builderExDone.done(false);
            try {
                LOGGER.debug("Save copies with adapted Workouts and WorkoutExercises to ExerciseDone in Database");
                iExerciseDoneRepository.save(builderExDone.build());
                LOGGER.debug("Successfully saved ExerciseDone");
            } catch (DataAccessException e) {
                throw new ServiceException(e.getMessage());
            }
        }

        ActiveTrainingSchedule.ActiveTrainingScheduleBuilder builderATs = new ActiveTrainingSchedule.ActiveTrainingScheduleBuilder();
        builderATs.id(activeSchedule.getId());
        builderATs.dudeId(activeSchedule.getDudeId());
        builderATs.trainingScheduleId(ts.getId());
        builderATs.trainingScheduleVersion(ts.getVersion());
        builderATs.startDate(activeSchedule.getStartDate());
        builderATs.intervalRepetitions(activeSchedule.getIntervalRepetitions());
        builderATs.isAdaptive(true);

        List<Boolean> hasBeenAdapted = activeSchedule.getHasBeenAdapted();
        LocalDate tempDate = LocalDate.from(activeSchedule.getStartDate());
        hasBeenAdapted.set((int) (tempDate.until(LocalDate.now(), ChronoUnit.DAYS) / ts.getIntervalLength()) - 1, true);
        builderATs.hasBeenAdapted(hasBeenAdapted);

        try {
            LOGGER.debug("update current ActiveTrainingSchedule in database");
            return iActiveTrainingScheduleRepository.save(builderATs.build());
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void countExToAdaptTotalExAndStrengthEx(TrainingScheduleWorkout workout, int day) {
        WorkoutExerciseHelp.WorkoutExerciseHelpBuilder builderWaExH = new WorkoutExerciseHelp.WorkoutExerciseHelpBuilder();
        WorkoutHelp.WorkoutHelpBuilder builderWaH = new WorkoutHelp.WorkoutHelpBuilder();
        int totalRepetitions = 0;
        for (WorkoutExercise e : workout.getWorkout().getExercises()) {
            totalRepetitions += e.getRepetitions() * e.getSets();
            // only count exercise to number of toChange exercises, it is not of category Endurance or Other with only 1 set and repetition
            if (e.getExercise().getCategory() == Category.Strength) {
                categoryStrength++;
            }
            if ((e.getExercise().getCategory() == Category.Endurance || e.getExercise().getCategory() == Category.Other) && e.getSets() == 1 && e.getRepetitions() == 1) {
                builderWaExH.workoutExercise(e);
                builderWaExH.day(day);
                allExercises.add(builderWaExH.build());
            } else {
                numOfExPerDay[day - 1] = numOfExPerDay[day - 1] + 1;
            }
            totalEx++;
        }
        builderWaH.day(day);
        builderWaH.workout(workout.getWorkout());
        builderWaH.caloriesConsumption(workout.getWorkout().getCalorieConsumption() / totalRepetitions);
        allWorkouts.add(builderWaH.build());
    }

    private void applyAdaptiveChange(List<WorkoutExerciseDone> ex, double percentPerExDay, int day) {
        LOGGER.debug("entering applyAdaptiveChange");
        int repsHelpIncrease;
        int repsHelpDecrease;
        WorkoutExerciseHelp.WorkoutExerciseHelpBuilder builderWaExH = new WorkoutExerciseHelp.WorkoutExerciseHelpBuilder();
        WorkoutExercise e;
        Exercise exc;
        int newDuration;

        for (WorkoutExerciseDone eDone : ex) {
            e = eDone.getWorkoutExercise();
            exc = iExerciseRepository.findByIdAndVersion(e.getExerciseId(), e.getExerciseVersion()).get();
            eDone.getWorkoutExercise().setExercise(exc);

            if (!((exc.getCategory() == Category.Endurance || exc.getCategory() == Category.Other) && e.getSets() == 1 && e.getRepetitions() == 1)) {
                // calculate new number of repetitions
                double calculateDurationPerRepetition = ((double) e.getExDuration()) / (((double) e.getRepetitions()) * ((double) e.getSets()));
                LOGGER.debug("Duration per Repetition: " + calculateDurationPerRepetition);
                repsHelpIncrease = BigDecimal.valueOf(e.getRepetitions() + (percentPerExDay * e.getRepetitions())).setScale(0, RoundingMode.HALF_UP).intValue();
                repsHelpDecrease = BigDecimal.valueOf(e.getRepetitions() - (percentPerExDay * e.getRepetitions())).setScale(0, RoundingMode.HALF_UP).intValue();

                // increase/decrease
                if (eDone.isDone()) {
                    if (repsHelpIncrease > 200) {
                        // if maximum number of repetitions is exceeded: set default number of repetitions and increase number of sets by 1
                        if (e.getSets() < 20) {
                            int newReps = repsHelpIncrease;
                            int i = 1;
                            while (newReps > 200) {
                                newReps = (int) Math.round((double) (repsHelpIncrease * e.getSets()) / (e.getSets() + i));
                                i++;
                            }
                            e.setRepetitions(newReps);
                            int newSets = e.getSets() + (i-1);
                            e.setSets(newSets < 20? newSets : 20);
                        } else {
                            e.setRepetitions(200);
                        }
                    } else {
                        e.setRepetitions(repsHelpIncrease);
                    }
                    newDuration = (int) (calculateDurationPerRepetition * (e.getRepetitions() * e.getSets()));
                    e.setExDuration(newDuration > 1440 ? 1440 : newDuration);
                } else {
                    if (repsHelpDecrease <= 1) {
                        if (e.getSets() > 1) {
                            e.setSets(e.getSets() - 1);
                            // repetitions stay the same, sets get decreased
                        }
                    } else {
                        e.setRepetitions(repsHelpDecrease);
                    }
                    newDuration = (int) (calculateDurationPerRepetition * (e.getRepetitions() * e.getSets()));
                    e.setExDuration(newDuration > 1 ? newDuration : 1);
                }

                LOGGER.debug("Update workoutExercise after adaptive change");
                WorkoutExercise updatedWaEx = iWorkoutExerciseRepository.save(e);
                LOGGER.debug("Successfully updated workoutExercise after adaptive change");

                builderWaExH.workoutExercise(updatedWaEx);
                builderWaExH.day(day);
                allExercises.add(builderWaExH.build());
            }
        }
    }

    private void adaptWorkouts(List<WorkoutHelp> waHelp) {
        LOGGER.debug("entering adaptWorkouts");
        double totalCalories = 0;
        for (WorkoutHelp workoutHelp : waHelp) {
            for (WorkoutExercise ex : workoutHelp.getWorkout().getExercises()) {
                totalCalories += workoutHelp.getCaloriesConsumption() * ex.getRepetitions() * ex.getSets();
            }
            totalCalories = Math.round(totalCalories);
            totalCalories = totalCalories < 1 ? 1 : totalCalories;
            totalCalories = totalCalories > 9999 ? 9999 : totalCalories;
            workoutHelp.getWorkout().setCalorieConsumption(totalCalories);
            totalCalories = 0;
            LOGGER.debug("Update workout after adaptive change");
            iWorkoutRepository.save(workoutHelp.getWorkout());
            LOGGER.debug("Successfully updated workout after adaptive change");
        }
    }

    @Override
    public TrainingSchedule copyOldTrainingSchedule(ActiveTrainingSchedule activeTs, Long dudeId, TrainingSchedule oldTs) throws ServiceException {
        LOGGER.debug("Entering copy old trainingSchedule");

        List<WorkoutExerciseDone> waExDoneHelp = new ArrayList<>();
        List<WorkoutExercise> copyWaEx = new ArrayList<>();
        List<TrainingScheduleWorkout> copyTsWa = new ArrayList<>();
        List<ExerciseDone> exDoneForTsWa = activeTs.getDone();
        Workout wa;

        Workout.WorkoutBuilder builderWa = new Workout.WorkoutBuilder();
        TrainingScheduleWorkout.TrainingScheduleWorkoutBuilder builderTsWa = new TrainingScheduleWorkout.TrainingScheduleWorkoutBuilder();
        WorkoutExercise.WorkoutExerciseBuilder builderWaEx = new WorkoutExercise.WorkoutExerciseBuilder();
        TrainingSchedule.TrainingScheduleBuilder builder = new TrainingSchedule.TrainingScheduleBuilder();
        WorkoutExerciseDone.WorkoutExerciseDoneBuilder builderWaExDone = new WorkoutExerciseDone.WorkoutExerciseDoneBuilder();

        builder.name(oldTs.getName());
        builder.creator(activeTs.getDude());
        builder.description(oldTs.getDescription());
        builder.difficulty(oldTs.getDifficulty());
        builder.intervalLength(oldTs.getIntervalLength());
        builder.ratingNum(oldTs.getRatingNum());
        builder.ratingSum(oldTs.getRatingSum());
        builder.isPrivate(true);
        builder.isHistory(true);

        for (TrainingScheduleWorkout tsWa : oldTs.getWorkouts()) {

            builderWa.version(1);
            builderWa.name(tsWa.getWorkout().getName());
            builderWa.description(tsWa.getWorkout().getDescription());
            builderWa.difficulty(tsWa.getWorkout().getDifficulty());
            builderWa.calorieConsumption(tsWa.getWorkout().getCalorieConsumption());
            builderWa.ratingNum(tsWa.getWorkout().getRatingNum());
            builderWa.ratingSum(tsWa.getWorkout().getRatingSum());
            builderWa.creator(activeTs.getDude());
            builderWa.isPrivate(true);
            builderWa.isHistory(true);

            int tsWaDay;
            if (t == 1) {
                tsWaDay = tsWa.getDay();
            } else {
                tsWaDay = tsWa.getDay() + (t - 2) * oldTs.getIntervalLength();
            }
            for (ExerciseDone exDone : exDoneForTsWa) {
                if ((exDone.getWorkoutId().equals(tsWa.getWorkoutId())) && (exDone.getWorkoutVersion().equals(tsWa.getWorkoutVersion())) && (exDone.getDay() == tsWaDay)) {
                    builderWaEx.exerciseId(exDone.getWorkoutExercise().getExerciseId());
                    builderWaEx.exerciseVersion(exDone.getWorkoutExercise().getExerciseVersion());
                    builderWaEx.exDuration(exDone.getWorkoutExercise().getExDuration());
                    builderWaEx.repetitions(exDone.getWorkoutExercise().getRepetitions());
                    builderWaEx.sets(exDone.getWorkoutExercise().getSets());
                    copyWaEx.add(builderWaEx.build());

                    // save workoutExercise and done information to help structure
                    builderWaExDone.workoutExercise(builderWaEx.build());
                    builderWaExDone.done(exDone.getDone());
                    waExDoneHelp.add(builderWaExDone.build());
                }
            }

            builderWa.exercises(copyWaEx);
            LOGGER.debug("Save Workout-Copy");
            wa = workoutService.save(builderWa.build());
            LOGGER.debug("Succesfully saved workout with id " + wa.getId());
            copyWaEx.clear();

            // save workoutId and workoutVersion to help-Structure
            for (WorkoutExerciseDone w : waExDoneHelp) {
                w.getWorkoutExercise().setWorkoutId(wa.getId());
                w.getWorkoutExercise().setWorkoutVersion(wa.getVersion());

                switch (tsWa.getDay()) {
                    case 1:
                        waExDay1.add(w);
                        break;
                    case 2:
                        waExDay2.add(w);
                        break;
                    case 3:
                        waExDay3.add(w);
                        break;
                    case 4:
                        waExDay4.add(w);
                        break;
                    case 5:
                        waExDay5.add(w);
                        break;
                    case 6:
                        waExDay6.add(w);
                        break;
                    case 7:
                        waExDay7.add(w);
                        break;
                    default:
                        break;
                }
            }
            waExDoneHelp.clear();

            builderTsWa.workoutId(wa.getId());
            builderTsWa.workoutVersion(wa.getVersion());
            builderTsWa.day(tsWa.getDay());
            copyTsWa.add(builderTsWa.build());
        }
        builder.workouts(copyTsWa);
        List<ActiveTrainingSchedule> activeUsages = new ArrayList<>();
        activeUsages.add(activeTs);
        builder.activeUsages(activeUsages);
        try {
            LOGGER.debug("Save TrainingSchedule-Copy");
            return save(builder.build());
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    private void resetVariables() {
        this.waExDay1.clear();
        this.waExDay2.clear();
        this.waExDay3.clear();
        this.waExDay4.clear();
        this.waExDay5.clear();
        this.waExDay6.clear();
        this.waExDay7.clear();
        this.allExercises.clear();
        this.allWorkouts.clear();
        this.t = 0;
        this.a = 0;
        this.s = 0;
        this.totalChangePerDayInPercent = 0;
        this.k = 0;
        this.bt = 0;
        this.totalEx = 0;
        this.categoryStrength = 0;
        this.intervalLength = 0;
        this.selfAssessment = 0;
        this.numOfExPerDay = new int[]{0, 0, 0, 0, 0, 0, 0};
    }

    @Override
    public List<ExerciseDone> findExDoneByActiveTrainingScheduleId(Long activeTrainingScheduleId) throws ServiceException {
        try {
            List<ExerciseDone> eDone = iExerciseDoneRepository.findByActiveTrainingScheduleId(activeTrainingScheduleId);
            List<WorkoutExercise> waEx = new ArrayList<>();
            for (ExerciseDone e : eDone) {
                e.setWorkoutExercise(iWorkoutExerciseRepository.findById(new WorkoutExerciseKey(e.getExerciseId(), e.getExerciseVersion(), e.getWorkoutId(), e.getWorkoutVersion())).get());
            }
            return eDone;
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    // ----------------------------------------- end of methods for adapting training schedule automatically -----------------------------------------

}

