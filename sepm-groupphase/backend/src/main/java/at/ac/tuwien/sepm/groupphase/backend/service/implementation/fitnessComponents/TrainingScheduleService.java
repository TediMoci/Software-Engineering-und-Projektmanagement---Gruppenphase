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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TrainingScheduleService implements ITrainingScheduleService {

    private final ITrainingScheduleRepository iTrainingScheduleRepository;
    private final ITrainingScheduleWorkoutRepository iTrainingScheduleWorkoutRepository;
    private final IWorkoutRepository iWorkoutRepository;
    private final IActiveTrainingScheduleRepository iActiveTrainingScheduleRepository;
    private final IExerciseDoneRepository iExerciseDoneRepository;
    private final IDudeRepository iDudeRepository;
    private final IWorkoutExerciseRepository iWorkoutExerciseRepository;
    private final IExerciseRepository iExerciseRepository;
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

    // number of exercises per day of interval
    private int numOfExDay1 = 0;
    private int numOfExDay2 = 0;
    private int numOfExDay3 = 0;
    private int numOfExDay4 = 0;
    private int numOfExDay5 = 0;
    private int numOfExDay6 = 0;
    private int numOfExDay7 = 0;

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

    public TrainingScheduleService(IWorkoutService workoutService, IExerciseRepository iExerciseRepository, IWorkoutExerciseRepository iWorkoutExerciseRepository, IDudeRepository iDudeRepository, ITrainingScheduleRepository iTrainingScheduleRepository, ITrainingScheduleWorkoutRepository iTrainingScheduleWorkoutRepository, IWorkoutRepository iWorkoutRepository, IActiveTrainingScheduleRepository iActiveTrainingScheduleRepository, IExerciseDoneRepository iExerciseDoneRepository, TrainingScheduleValidator trainingScheduleValidator, TrainingScheduleWorkoutValidator trainingScheduleWorkoutValidator) {
        this.workoutService = workoutService;
        this.iTrainingScheduleRepository = iTrainingScheduleRepository;
        this.iTrainingScheduleWorkoutRepository = iTrainingScheduleWorkoutRepository;
        this.iWorkoutExerciseRepository = iWorkoutExerciseRepository;
        this.iWorkoutRepository = iWorkoutRepository;
        this.iExerciseRepository = iExerciseRepository;
        this.iActiveTrainingScheduleRepository = iActiveTrainingScheduleRepository;
        this.iExerciseDoneRepository = iExerciseDoneRepository;
        this.iDudeRepository = iDudeRepository;
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
            if (iDudeRepository.findById(activeTrainingSchedule.getDudeId()).get().getActiveTrainingSchedule() != null) {
                throw new ServiceException("You already have an active training schedule!");
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException("No Dude in database with id: " + activeTrainingSchedule.getDudeId());
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
    public TrainingSchedule saveRandom(int days, int duration, double minTarget, double maxTarget, TrainingSchedule trainingSchedule) throws ServiceException {
        LOGGER.info("Entering save for: " + trainingSchedule);
        trainingSchedule.setWorkouts(null);

        List<Workout> workouts = iWorkoutRepository.findByDifficulty(trainingSchedule.getDifficulty());
        sum_up(workouts,minTarget,maxTarget,days,duration);

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
            activeTrainingSchedule = iDudeRepository.findById(dudeId).get().getActiveTrainingSchedule();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }

        // TODO (Amir): stat-calculations and -saving for activeTrainingSchedule

        // delete exerciseDone from to delete activeTrainingSchedule
        List<TrainingSchedule> copiedTs = new ArrayList<>();
        List<TrainingScheduleWorkout> toDeleteTrainingScheduleWorkouts = new ArrayList<>();
        List<Workout> toDeleteWorkout = new ArrayList<>();
        List<WorkoutExercise> toDeleteWorkoutExercise = new ArrayList<>();

        for (int i = 0; i < activeTrainingSchedule.getDone().size(); i++) {
            int maxDayNotChosen = activeTrainingSchedule.getTrainingSchedule().getIntervalLength();
            if ((activeTrainingSchedule.getAdaptive()) && (!copiedTs.contains(activeTrainingSchedule.getDone().get(i).getTrainingSchedule())) && (activeTrainingSchedule.getDone().get(i).getDay() > maxDayNotChosen)) {
                copiedTs.add(activeTrainingSchedule.getDone().get(i).getTrainingSchedule());
            }
            iExerciseDoneRepository.delete(activeTrainingSchedule.getDone().get(i));
        }

        // delete copies of trainingSchedule created by adaptive change methods
        if (activeTrainingSchedule.getAdaptive()) {
            for (TrainingSchedule copy : copiedTs) {
                toDeleteTrainingScheduleWorkouts.addAll(copy.getWorkouts());
                LOGGER.debug("delete trainingSchedule with id " + copy.getId());
                iTrainingScheduleRepository.deleteById(copy.getId());
            }
        }
        // delete copies of TrainingScheduleWorkouts created by adaptive change methods
        if (toDeleteTrainingScheduleWorkouts != null){
            for (TrainingScheduleWorkout w: toDeleteTrainingScheduleWorkouts){
                toDeleteWorkout.add(w.getWorkout());
                iTrainingScheduleWorkoutRepository.delete(w);
            }
        }
        // delete copies of Workouts created by adaptive change methods
        if (toDeleteWorkout != null){
            for (Workout w: toDeleteWorkout) {
                toDeleteWorkoutExercise.addAll(w.getExercises());
                iWorkoutRepository.deleteById(w.getId());
            }
        }
        /*
          // delete copies of WorkoutExercises created by adaptive change methods
        if (toDeleteWorkoutExercise != null){
            for (WorkoutExercise w: toDeleteWorkoutExercise){
                iWorkoutExerciseRepository.delete(w);
            }
        }
         */


        try {
            iActiveTrainingScheduleRepository.deleteByDudeId(dudeId);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<TrainingSchedule> findByName(String name) throws ServiceException {
        LOGGER.info("Entering findByName with name: " + name);
        try {
            return iTrainingScheduleRepository.findByName(name);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
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
    public TrainingSchedule update(long id, TrainingSchedule newTraining) throws ServiceException {
        LOGGER.info("Updating training schedule with id: " + id);
        try {
            TrainingSchedule oldTraining = iTrainingScheduleRepository.findById(id);
            if (oldTraining == null) throw new ServiceException("Could not find training schedule with id: " + id);
            newTraining.setId(id);
            newTraining.setVersion(1 + oldTraining.getVersion());
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

    @Override
    public List<TrainingSchedule> findByFilter(String filter, Integer selfAssessment) throws ServiceException {
        return null;
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
        if (duration<1 || duration>1440) throw new ServiceException("Please give a valid duration per day");
        if (days<1 || days>7) throw new ServiceException("Please give a valid number of days per week");
        if (minTarget>maxTarget) throw new ServiceException("Please give the correct minimum and maximum values");

        sum_up_recursive(numbers,minTarget,maxTarget,new ArrayList<Workout>(),days,duration);
        if (finalList.isEmpty()) throw new ServiceException("Training schedule could not be formed with existing workouts");
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
        if (s <= maxTarget && s >= minTarget && allDuration<=duration) {
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
            sum_up_recursive(remaining, minTarget, maxTarget, partial_rec, days,duration);
        }
    }

    private static int getDuration(Workout workout) {
        int allDuration = 0;
        List<WorkoutExercise> workoutExercises = workout.getExercises();
        for (WorkoutExercise w : workoutExercises){
            allDuration = allDuration + w.getExDuration();
        }
        return allDuration;
    }

    // ----------------------------------------- start of methods for adapting training schedule automatically -----------------------------------------
    @Override
    public ActiveTrainingSchedule calculatePercentageOfChangeForInterval(ActiveTrainingSchedule activeSchedule, Dude dude) throws ServiceException {

        // TODO: prevent copying trainingSchedule after every reload of page

        // old trainingSchedule
        TrainingSchedule oldTs = activeSchedule.getTrainingSchedule();
        // copy of old trainingSchedule, which will be altered
        TrainingSchedule ts = copyOldTrainingSchedule(activeSchedule, activeSchedule.getDudeId(), oldTs);
        LOGGER.debug("Successfully copied old TrainingSchedule");

        List<TrainingScheduleWorkout> workouts = iTrainingScheduleWorkoutRepository.findByTrainingSchedule(ts);
        for (TrainingScheduleWorkout w: workouts) {
            Workout wa = iWorkoutRepository.findByIdAndVersion(w.getWorkoutId(), w.getWorkoutVersion()).get();
            List<WorkoutExercise> waEx = iWorkoutExerciseRepository.findByWorkout(wa);
            for (WorkoutExercise wex: waEx) {
                wex.setWorkout(wa);
                wex.setExercise(iExerciseRepository.findByIdAndVersion(wex.getExerciseId(), wex.getExerciseVersion()).get());
            }
            wa.setExercises(waEx);
            w.setWorkout(wa);
        }

        LOGGER.debug("Ts-Copy-Workouts-Size: " + workouts.size());
        LocalDate startDate = activeSchedule.getStartDate();
        int intervalRepetitions = activeSchedule.getIntervalRepetitions();
        int intervalLength = ts.getIntervalLength();
        int selfAssessment = dude.getSelfAssessment();

        int t = (Period.between(startDate, LocalDate.now()).getDays()) / intervalLength;
        if (t == 1) {
            return activeSchedule;
        }

        for (TrainingScheduleWorkout tsWa : workouts) {
            switch (tsWa.getDay()) {
                case 1:
                    LOGGER.debug("Count values needed for adaptive calculations for workouts of day 1");
                    countExToAdaptTotalExAndStrengthEx(tsWa, 1);
                    break;
                case 2:
                    LOGGER.debug("Count values needed for adaptive calculations for workouts of day 2");
                    countExToAdaptTotalExAndStrengthEx(tsWa, 2);
                    break;
                case 3:
                    LOGGER.debug("Count values needed for adaptive calculations for workouts of day 3");
                    countExToAdaptTotalExAndStrengthEx(tsWa, 3);
                    break;
                case 4:
                    LOGGER.debug("Count values needed for adaptive calculations for workouts of day 4");
                    countExToAdaptTotalExAndStrengthEx(tsWa, 4);
                    break;
                case 5:
                    LOGGER.debug("Count values needed for adaptive calculations for workouts of day 5");
                    countExToAdaptTotalExAndStrengthEx(tsWa, 5);
                    break;
                case 6:
                    LOGGER.debug("Count values needed for adaptive calculations for workouts of day 6");
                    countExToAdaptTotalExAndStrengthEx(tsWa, 6);
                    break;
                case 7:
                    LOGGER.debug("Count values needed for adaptive calculations for workouts of day 7");
                    countExToAdaptTotalExAndStrengthEx(tsWa, 7);
                    break;
            }
        }

        boolean strengthFocused = false;
        if (totalEx != 0) {
            if (categoryStrength / totalEx > 0.7) {
                strengthFocused = true;
            }
        }

        double a; // percentage for the first repetition of the interval, initial change of x%, on average x% change per interval
        if (selfAssessment == 1) {
            a = 1.5;
        } else {
            a = 10;
        }

        double s; // maximum percentage for training schedule adaption
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
        }

        LOGGER.debug("Calculate percentage of change per interval with logistic growth function");
        // logistic growth function to generate percentage of change compared to the inital data
        double k = Math.log((a * s - (s - a) * a) / ((s - a) * (s - a))) / -(s * (intervalRepetitions - 1)); // growth constant, calculated for model according to other parameters
        double bt = Math.floor((a * s) / (a + (s - a) * Math.exp(-s * k * (t - 1)))); // result: percentage of change in the t-th interval

        /*
         * Percentage of change per interval is applied for each day containing workouts
         *  - percentage is split equally for all exercises of each day
         *  example: Day 1, 3 Workouts with 2 Exercises each, 3% change => 6 Exercises total => 3/6 = 0.5% change per Exercise
         *  - adaption is applied in this way:
         *  bankersRounding(oldNumberOfExerciseRepetitions + (percentageOfChangePerExercise * oldNumberOfExerciseRepetitions))
         *  - only repetitions are altered until maximumRepetitions are reached,
         *    if maximumRepetitions are reached, number of sets is altered and repetitions are set to default minimum
         */

        double totalChangePerDayInPercent = 0;

        if (t == 2) {
            totalChangePerDayInPercent = bt;
        } else {
            totalChangePerDayInPercent = bt - (Math.floor((a * s) / (a + (s - a) * Math.exp(-s * k * (t - 2)))));
        }

        LOGGER.debug("Calculate adaptive change percent per exercise per day");
        // spread percentage of change equally on all exercises per day
        double percentPerExDay1 = (numOfExDay1 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay1));
        double percentPerExDay2 = (numOfExDay2 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay2));
        double percentPerExDay3 = (numOfExDay3 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay3));
        double percentPerExDay4 = (numOfExDay4 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay4));
        double percentPerExDay5 = (numOfExDay5 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay5));
        double percentPerExDay6 = (numOfExDay6 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay6));
        double percentPerExDay7 = (numOfExDay7 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay7));

        // change number of sets and repetitions according to percentage of change per day
        LOGGER.debug("Apply adaptive change to workoutExercises of day 1");
        applyAdaptiveChange(waExDay1, percentPerExDay1, 1);
        LOGGER.debug("Apply adaptive change to workoutExercises of day 2");
        applyAdaptiveChange(waExDay2, percentPerExDay2, 2);
        LOGGER.debug("Apply adaptive change to workoutExercises of day 3");
        applyAdaptiveChange(waExDay3, percentPerExDay3, 3);
        LOGGER.debug("Apply adaptive change to workoutExercises of day 4");
        applyAdaptiveChange(waExDay4, percentPerExDay4, 4);
        LOGGER.debug("Apply adaptive change to workoutExercises of day 5");
        applyAdaptiveChange(waExDay5, percentPerExDay5, 5);
        LOGGER.debug("Apply adaptive change to workoutExercises of day 6");
        applyAdaptiveChange(waExDay6, percentPerExDay6, 6);
        LOGGER.debug("Apply adaptive change to workoutExercises of day 7");
        applyAdaptiveChange(waExDay7, percentPerExDay7, 7);

        LOGGER.debug("Apply adaptive change to workouts");
        adaptWorkouts(allWorkouts);

        ExerciseDone.ExerciseDoneBuilder builderExDone = new ExerciseDone.ExerciseDoneBuilder();
        for (int j = 0; j < allExercises.size(); j++) {

            builderExDone.dudeId(dude.getId());
            builderExDone.trainingScheduleId(ts.getId());
            builderExDone.trainingScheduleVersion(ts.getVersion());
            builderExDone.activeTrainingScheduleId(activeSchedule.getId());
            LOGGER.debug("get info of workoutExercises from help structure");
            builderExDone.exerciseId(allExercises.get(j).getWorkoutExercise().getExerciseId());
            builderExDone.exerciseVersion(allExercises.get(j).getWorkoutExercise().getExerciseVersion());
            builderExDone.workoutId(allExercises.get(j).getWorkoutExercise().getWorkoutId());
            builderExDone.workoutVersion(allExercises.get(j).getWorkoutExercise().getWorkoutVersion());
            builderExDone.day(allExercises.get(j).getDay() + (((Period.between(LocalDate.now(), activeSchedule.getStartDate()).getDays() + intervalLength)/activeSchedule.getTrainingSchedule().getIntervalLength()) * activeSchedule.getTrainingSchedule().getIntervalLength()));
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
        try {
            LOGGER.debug("update current ActiveTrainingSchedule in database");
            return iActiveTrainingScheduleRepository.save(builderATs.build());
        } catch (DataAccessException e){
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
                switch (day) {
                    case 1:
                        numOfExDay1++;
                        break;
                    case 2:
                        numOfExDay2++;
                        break;
                    case 3:
                        numOfExDay3++;
                        break;
                    case 4:
                        numOfExDay4++;
                        break;
                    case 5:
                        numOfExDay5++;
                        break;
                    case 6:
                        numOfExDay6++;
                        break;
                    case 7:
                        numOfExDay7++;
                        break;
                }
            }
            totalEx++;
        }

        builderWaH.day(day);
        builderWaH.workout(workout.getWorkout());
        builderWaH.caloriesConsumption(workout.getWorkout().getCalorieConsumption()/totalRepetitions);
        allWorkouts.add(builderWaH.build());

    }

    private void adaptWorkouts(List<WorkoutHelp> waHelp) {
        LOGGER.debug("entering adaptWorkouts");
        double totalCalories = 0;
        LOGGER.debug("NumOfWorkouts: " + waHelp.size());
        for (WorkoutHelp workoutHelp : waHelp) {
            LOGGER.debug("Workouthelp: " + workoutHelp.toString());
            LOGGER.debug("workout: " + workoutHelp.getWorkout().toString());
            for (WorkoutExercise ex : workoutHelp.getWorkout().getExercises()) {
                totalCalories += workoutHelp.getCaloriesConsumption() * ex.getRepetitions() * ex.getSets();
            }
            workoutHelp.getWorkout().setCalorieConsumption(totalCalories);
            totalCalories = 0;
            LOGGER.debug("Update workout after adaptive change");
            iWorkoutRepository.save(workoutHelp.getWorkout());
            LOGGER.debug("Successfully updated workout after adaptive change");
        }
    }

    private void applyAdaptiveChange(List<WorkoutExerciseDone> ex, double percentPerExDay, int day) {
        LOGGER.debug("entering applyAdaptiveChange");
        int repsHelpIncrease;
        int repsHelpDecrease;
        WorkoutExerciseHelp.WorkoutExerciseHelpBuilder builderWaExH = new WorkoutExerciseHelp.WorkoutExerciseHelpBuilder();

        int calculateDurationPerRepetition = 0;
        WorkoutExercise e;
        Exercise exc;

        for (WorkoutExerciseDone eDone : ex) {
            e = eDone.getWorkoutExercise();
            exc = iExerciseRepository.findByIdAndVersion(e.getExerciseId(), e.getExerciseVersion()).get();
            eDone.getWorkoutExercise().setExercise(exc);

            if (!((exc.getCategory() == Category.Endurance || exc.getCategory() == Category.Other) && e.getSets() == 1 && e.getRepetitions() == 1)) {
                // calculate new number of repetitions
                calculateDurationPerRepetition = (e.getRepetitions() * e.getSets()) / e.getExDuration();
                repsHelpIncrease = (int) new BigDecimal(String.valueOf(e.getRepetitions() + (percentPerExDay * e.getRepetitions()))).setScale(0, RoundingMode.HALF_UP).doubleValue();
                repsHelpDecrease = (int) new BigDecimal(String.valueOf(e.getRepetitions() - (percentPerExDay * e.getRepetitions()))).setScale(0, RoundingMode.HALF_UP).doubleValue();

                // increase/decrease
                if (eDone.isDone()) {
                    if (repsHelpIncrease > 100) {
                        // if maximum number of repetitions is exceeded: set default number of repetitions to oldRepetitions/2 and increase number of sets by 1
                        e.setRepetitions(repsHelpIncrease / 2);
                        if (e.getSets() < 15) {
                            e.setSets(e.getSets() + 1);
                        }
                    } else {
                        e.setRepetitions(repsHelpIncrease);
                        e.setExDuration(calculateDurationPerRepetition * e.getRepetitions());
                    }
                } else {
                    if (repsHelpDecrease <= 1) {
                        if (e.getSets() > 1) {
                            e.setSets(e.getSets() - 1);
                            e.setRepetitions(10);
                        }
                    } else {
                        e.setRepetitions(repsHelpDecrease);
                        e.setExDuration(calculateDurationPerRepetition * e.getRepetitions());
                    }
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

    @Override
    public TrainingSchedule copyOldTrainingSchedule(ActiveTrainingSchedule activeTs, Long dudeId, TrainingSchedule oldTs) throws ServiceException {

        LOGGER.debug("Start copying old trainingSchedule");
        List<WorkoutExerciseDone> waExDoneHelp = new ArrayList<>();
        List<WorkoutExercise> copyWaEx = new ArrayList<>();
        List<TrainingScheduleWorkout> copyTsWa = new ArrayList<>();
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
        builder.rating(oldTs.getRating());
        builder.isHistory(true);

        for (TrainingScheduleWorkout tsWa : oldTs.getWorkouts()) {

            builderWa.version(1);
            builderWa.name(tsWa.getWorkout().getName());
            builderWa.description(tsWa.getWorkout().getDescription());
            builderWa.difficulty(tsWa.getWorkout().getDifficulty());
            builderWa.calorieConsumption(tsWa.getWorkout().getCalorieConsumption());
            builderWa.rating(tsWa.getWorkout().getRating());
            builderWa.creator(activeTs.getDude());
            builderWa.isHistory(true);

            List<ExerciseDone> exDoneForTsWa = activeTs.getDone();
            int tsWaDay = tsWa.getDay() + ((Period.between(LocalDate.now(), activeTs.getStartDate()).getDays()/activeTs.getTrainingSchedule().getIntervalLength()) * activeTs.getTrainingSchedule().getIntervalLength());
            for (ExerciseDone exDone : exDoneForTsWa) {
                if ((exDone.getWorkout().getId().equals(tsWa.getWorkout().getId())) && (exDone.getWorkout().getVersion().equals(tsWa.getWorkout().getVersion())) && (exDone.getDay() == tsWaDay)) {
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
            exDoneForTsWa.clear();

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

    // ----------------------------------------- end of methods for adapting training schedule automatically -----------------------------------------

}

