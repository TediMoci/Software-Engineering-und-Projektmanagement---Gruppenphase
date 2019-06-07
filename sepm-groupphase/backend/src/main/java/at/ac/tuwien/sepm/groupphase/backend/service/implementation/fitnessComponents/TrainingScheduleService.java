package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
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
    private final IWorkoutService workoutService;
    private final ITrainingScheduleService trainingScheduleService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingScheduleService.class);
    private static Map<Integer, List<Workout>> finalList = new HashMap<Integer, List<Workout>>();
    private static Integer listPosition = 0;


    // variables for training schedule adaption
    // --------------------------------------------------------------------------
    // list of workouts per day
    List<TrainingScheduleWorkout> workouts1 = new ArrayList<>();
    List<TrainingScheduleWorkout> workouts2 = new ArrayList<>();
    List<TrainingScheduleWorkout> workouts3 = new ArrayList<>();
    List<TrainingScheduleWorkout> workouts4 = new ArrayList<>();
    List<TrainingScheduleWorkout> workouts5 = new ArrayList<>();
    List<TrainingScheduleWorkout> workouts6 = new ArrayList<>();
    List<TrainingScheduleWorkout> workouts7 = new ArrayList<>();

    // list of exercises per day
    List<WorkoutExercise> ex1 = new ArrayList<>();
    List<WorkoutExercise> ex2 = new ArrayList<>();
    List<WorkoutExercise> ex3 = new ArrayList<>();
    List<WorkoutExercise> ex4 = new ArrayList<>();
    List<WorkoutExercise> ex5 = new ArrayList<>();
    List<WorkoutExercise> ex6 = new ArrayList<>();
    List<WorkoutExercise> ex7 = new ArrayList<>();

    // number of exercises per day of interval
    int numOfExDay1 = 0;
    int numOfExDay2 = 0;
    int numOfExDay3 = 0;
    int numOfExDay4 = 0;
    int numOfExDay5 = 0;
    int numOfExDay6 = 0;
    int numOfExDay7 = 0;

    int totalEx = 0; // total number of exercises per interval
    int categoryStrength = 0; // total number of exercises with category strength per interval
    // --------------------------------------------------------------------------

    public TrainingScheduleService(IWorkoutService workoutService, ITrainingScheduleService trainingScheduleService, ITrainingScheduleRepository iTrainingScheduleRepository, ITrainingScheduleWorkoutRepository iTrainingScheduleWorkoutRepository, IWorkoutRepository iWorkoutRepository, IActiveTrainingScheduleRepository iActiveTrainingScheduleRepository, IExerciseDoneRepository iExerciseDoneRepository, TrainingScheduleValidator trainingScheduleValidator, TrainingScheduleWorkoutValidator trainingScheduleWorkoutValidator) {
        this.workoutService = workoutService;
        this.trainingScheduleService = trainingScheduleService;
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
        sum_up(workouts, minTarget, maxTarget, days);

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

    private static void sum_up(List<Workout> numbers, double minTarget, double maxTarget, int days) throws ServiceException {
        if (days < 1 || days > 7) {
            throw new ServiceException("Please give a valid number of days per week");
        }
        if (minTarget > maxTarget) {
            throw new ServiceException("Please give the correct minimum and maximum values");
        }
        sum_up_recursive(numbers, minTarget, maxTarget, new ArrayList<Workout>(), days);
        if (finalList.isEmpty())
            throw new ServiceException("Training schedule could not be formed with existing workouts");
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

    public ActiveTrainingSchedule calculatePercentageOfChangeForInterval(ActiveTrainingSchedule activeSchedule, Dude dude) throws ServiceException{

        // old trainingSchedule
        TrainingSchedule oldTs = activeSchedule.getTrainingSchedule();
        // copy of old trainingSchedule, which will be altered
        TrainingSchedule ts = copyOldTrainingSchedule(oldTs);

        List<TrainingScheduleWorkout> workouts = ts.getWorkouts();
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
                    addToWorkoutAndExList(tsWa, workouts1, ex1, numOfExDay1);
                    break;
                case 2:
                    addToWorkoutAndExList(tsWa, workouts2, ex2, numOfExDay2);
                    break;
                case 3:
                    addToWorkoutAndExList(tsWa, workouts3, ex3, numOfExDay3);
                    break;
                case 4:
                    addToWorkoutAndExList(tsWa, workouts4, ex4, numOfExDay4);
                    break;
                case 5:
                    addToWorkoutAndExList(tsWa, workouts5, ex5, numOfExDay5);
                    break;
                case 6:
                    addToWorkoutAndExList(tsWa, workouts6, ex6, numOfExDay6);
                    break;
                case 7:
                    addToWorkoutAndExList(tsWa, workouts7, ex7, numOfExDay7);
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
            a = 3;
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

        // spread percentage of change equally on all exercises per day
        double percentPerExDay1 = (numOfExDay1 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay1));
        double percentPerExDay2 = (numOfExDay2 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay2));
        double percentPerExDay3 = (numOfExDay3 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay3));
        double percentPerExDay4 = (numOfExDay4 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay4));
        double percentPerExDay5 = (numOfExDay5 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay5));
        double percentPerExDay6 = (numOfExDay6 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay6));
        double percentPerExDay7 = (numOfExDay7 == 0 ? 0 : (totalChangePerDayInPercent / numOfExDay7));

        // TODO: Workoutexercises anpassen
        // TODO: look for done (ExerciseDone), to see where adaptive change needs to be aplied
        // TODO: adapt time and calorie consumption using change percent
        // ---------------------------------------------------------------------------------------------------------------------------
        // TODO: Done (List<ExerciseDone> anpassen (TsId, TsV, WaId, WaV))
        // TODO: DudeEndpoint: if interval is done, call adaptive method "calculatePercentageOfChangeForInterval()"

        ActiveTrainingSchedule.ActiveTrainingScheduleBuilder builderATs = new ActiveTrainingSchedule.ActiveTrainingScheduleBuilder();
        builderATs.dudeId(activeSchedule.getDudeId());
        builderATs.trainingScheduleId(ts.getId());
        builderATs.trainingScheduleVersion(ts.getVersion());
        builderATs.startDate(activeSchedule.getStartDate());
        builderATs.intervalRepetitions(activeSchedule.getIntervalRepetitions());
        builderATs.done(null); // todo adjust list of exercise done!!!
        builderATs.dude(activeSchedule.getDude());
        builderATs.trainingSchedule(ts);
        return builderATs.build();
    }

    private void addToWorkoutAndExList(TrainingScheduleWorkout currentTsWorkout, List<TrainingScheduleWorkout> workouts, List<WorkoutExercise> exercises, int numOfExDay) {
        // dont change if repetition = 1 und sets = 1 und category= other oder, endurance
        workouts.add(currentTsWorkout);
        exercises.addAll(currentTsWorkout.getWorkout().getExercises());
        for (WorkoutExercise e : currentTsWorkout.getWorkout().getExercises()) {
            // only count exercise to number of toChange exercises, it is not of category Endurance or Other with only 1 set and repetition
            if (!((e.getExercise().getCategory() == Category.Endurance || e.getExercise().getCategory() == Category.Other) && e.getSets() == 1 && e.getRepetitions() == 1)) {
                numOfExDay++;
            }
            totalEx++;
            if (e.getExercise().getCategory() == Category.Strength) {
                categoryStrength++;
            }
        }
    }

    // changes number of repetitions and sets for all given exercises
    // (expects list of WorkoutExercises of one day and the percent of Change calculated for each exercise for this day)
    private List<WorkoutExercise> applyAdaptiveChange(List<WorkoutExercise> ex, int percentPerExDay) {
        int repsHelp;

        for (WorkoutExercise e : ex) {
            if (!((e.getExercise().getCategory() == Category.Endurance || e.getExercise().getCategory() == Category.Other) && e.getSets() == 1 && e.getRepetitions() == 1)) {
                // calculate new number of repetitions
                repsHelp = (int) new BigDecimal(String.valueOf(e.getRepetitions() + (percentPerExDay * e.getRepetitions()))).setScale(0, RoundingMode.HALF_UP).doubleValue();
                if (repsHelp > 100) {
                    // if maximum number of repetitions is exceeded: set default number of repetitions to 5
                    e.setRepetitions(5);
                    if (e.getSets() < 15){
                        e.setSets(e.getSets() + 1);
                    }
                } else {
                    e.setRepetitions(repsHelp);
                }
            }
        }
        return ex;
    }

    private TrainingSchedule copyOldTrainingSchedule(TrainingSchedule oldTs) throws ServiceException {

        List<TrainingScheduleWorkout> copyTsWa = new ArrayList<>();
        List<WorkoutExercise> copyWaEx = new ArrayList<>();
        Workout wa;
        Workout.WorkoutBuilder builderWa = new Workout.WorkoutBuilder();
        TrainingScheduleWorkout.TrainingScheduleWorkoutBuilder builderTsWa = new TrainingScheduleWorkout.TrainingScheduleWorkoutBuilder();
        WorkoutExercise.WorkoutExerciseBuilder builderWaEx = new WorkoutExercise.WorkoutExerciseBuilder();
        TrainingSchedule.TrainingScheduleBuilder builder = new TrainingSchedule.TrainingScheduleBuilder();

        builder.name(oldTs.getName());
        builder.description(oldTs.getDescription());
        builder.difficulty(oldTs.getDifficulty());
        builder.intervalLength(oldTs.getIntervalLength());
        builder.rating(oldTs.getRating());
        builder.isHistory(true);

        for (TrainingScheduleWorkout tsWa: oldTs.getWorkouts()) {

            builderWa.name(tsWa.getWorkout().getName());
            builderWa.description(tsWa.getWorkout().getDescription());
            builderWa.difficulty(tsWa.getWorkout().getDifficulty());
            builderWa.calorieConsumption(tsWa.getWorkout().getCalorieConsumption());
            builderWa.rating(tsWa.getWorkout().getRating());
            builderWa.isHistory(true);

            for (WorkoutExercise wEx: tsWa.getWorkout().getExercises()) {
                builderWaEx.exerciseId(wEx.getExerciseId());
                builderWaEx.exerciseVersion(wEx.getExerciseVersion());
                builderWaEx.exercise(wEx.getExercise());
                builderWaEx.exDuration(wEx.getExDuration());
                builderWaEx.repetitions(wEx.getRepetitions());
                builderWaEx.sets(wEx.getSets());
                copyWaEx.add(builderWaEx.build());
            }

            builderWa.exercises(copyWaEx);
            copyWaEx.clear();

            builderWa.trainingSchedules(tsWa.getWorkout().getTrainingSchedules());
            builderWa.creator(tsWa.getWorkout().getCreator());
            wa = workoutService.save(builderWa.build());

            builderTsWa.workoutId(wa.getId());
            builderTsWa.workoutVersion(wa.getVersion());
            builderTsWa.day(tsWa.getDay());
            builderTsWa.workout(wa);
            copyTsWa.add(builderTsWa.build());
        }

        builder.workouts(copyTsWa);
        builder.creator(oldTs.getCreator());
        builder.activeUsages(oldTs.getActiveUsages());
        return trainingScheduleService.save(builder.build());
    }
}
