package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.ITrainingScheduleRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.ITrainingScheduleWorkoutRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.TrainingScheduleWorkoutValidator;
import at.ac.tuwien.sepm.groupphase.backend.validators.TrainingScheduleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@Service
public class TrainingScheduleService implements ITrainingScheduleService {

    private final ITrainingScheduleRepository iTrainingScheduleRepository;
    private final ITrainingScheduleWorkoutRepository iTrainingScheduleWorkoutRepository;
    private final IWorkoutRepository iWorkoutRepository;
    private final TrainingScheduleValidator trainingScheduleValidator;
    private final TrainingScheduleWorkoutValidator trainingScheduleWorkoutValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingScheduleService.class);
    private static Map<Integer, List<Workout>> finalList = new HashMap<Integer, List<Workout>>();
    private static Integer listPosition = 0;

    public TrainingScheduleService(ITrainingScheduleRepository iTrainingScheduleRepository, IWorkoutRepository iWorkoutRepository, ITrainingScheduleWorkoutRepository iTrainingScheduleWorkoutRepository, TrainingScheduleWorkoutValidator trainingScheduleWorkoutValidator, TrainingScheduleValidator trainingScheduleValidator) {
        this.iTrainingScheduleRepository = iTrainingScheduleRepository;
        this.iTrainingScheduleWorkoutRepository = iTrainingScheduleWorkoutRepository;
        this.trainingScheduleValidator = trainingScheduleValidator;
        this.trainingScheduleWorkoutValidator = trainingScheduleWorkoutValidator;
        this.iWorkoutRepository = iWorkoutRepository;
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

    public int calculatePercentageOfChangeForInterval(ActiveTrainingSchedule activeSchedule, Dude dude) {

        LocalDate startDate = activeSchedule.getStartDate();
        int intervalRepetitions = activeSchedule.getIntervalRepetitions();
        int intervalLength = activeSchedule.getTrainingSchedule().getIntervalLength();
        int selfAssessment = dude.getSelfAssessment();
        double bmi = dude.getWeight()/Math.pow((dude.getHeight()/100), 2);
        bmi = new BigDecimal(String.valueOf(bmi)).setScale(2, RoundingMode.HALF_UP).doubleValue();
        boolean strengthFocused = false;

        int t = (Period.between(startDate, LocalDate.now()).getYears())/intervalLength;

        double a; // percentage for the first repetition of the interval, initial change of x%, on average x% change per interval
        if (selfAssessment == 1){
            a = 1.5;
        } else {
            a = 3;
        }

        // TODO: if very strength focused make max 10%, 5% for beginners
        double s; // maximum percentage for training schedule adaption
        if (selfAssessment == 1){
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
            if (selfAssessment == 1){
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

        double k = Math.log((a*s - (s-3)*a)/((s-3)*(s-a)))/-(s*(intervalRepetitions-1)); // growth constant, calculated for model according to other parameters
        double bt = (a*s)/(a + (s-a)*Math.exp(-s*k*t)); // result: percentage of change in the t-th interval

        List<TrainingScheduleWorkout> workouts = activeSchedule.getTrainingSchedule().getWorkouts();

        // TODO: first repetition of interval gets default max percent (3/1.5%)
        // TODO: find most common category for choosing percentage of change
        // TODO: look for done (ExerciseDone), to see where adaptive change needs to be aplied

        return 1;
    }
}
