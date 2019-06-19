package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class TrainingScheduleServiceTest {

    private static final TrainingSchedule validTrainingSchedule = new TrainingSchedule();

    @MockBean
    private IExerciseRepository exerciseRepository;

    @MockBean
    private IWorkoutRepository workoutRepository;

    @MockBean
    private IWorkoutExerciseRepository workoutExerciseRepository;

    @MockBean
    private ITrainingScheduleRepository trainingScheduleRepository;

    @MockBean
    private ITrainingScheduleWorkoutRepository trainingScheduleWorkoutRepository;

    @MockBean
    private IActiveTrainingScheduleRepository activeTrainingScheduleRepository;

    @MockBean
    private TrainingScheduleBookmarkRepository trainingScheduleBookmarkRepository;

    @MockBean
    private IDudeRepository dudeRepository;

    @Autowired
    private ITrainingScheduleService trainingScheduleService;

    @BeforeClass
    public static void beforeClass() {
        Dude dude = new Dude();
        dude.setId(1L);

        WorkoutExercise validWorkoutExercise1 = new WorkoutExercise();
        List<WorkoutExercise> validWorkoutExercises1 = new ArrayList<>();
        validWorkoutExercises1.add(validWorkoutExercise1);

        Workout validWorkout1 = new Workout();
        validWorkout1.setName("Workout1");
        validWorkout1.setDifficulty(1);
        validWorkout1.setCalorieConsumption(100.0);
        validWorkout1.setCreator(dude);
        validWorkout1.setExercises(validWorkoutExercises1);

        Workout validWorkout2 = new Workout();
        validWorkout2.setName("Workout2");
        validWorkout2.setDifficulty(2);
        validWorkout2.setCalorieConsumption(200.0);
        validWorkout2.setCreator(dude);
        validWorkout2.setExercises(validWorkoutExercises1);

        TrainingScheduleWorkout validTrainingScheduleWorkout1 = new TrainingScheduleWorkout();
        TrainingScheduleWorkout validTrainingScheduleWorkout2 = new TrainingScheduleWorkout();
        List<TrainingScheduleWorkout> validTrainingScheduleWorkouts1 = new ArrayList<>();
        validTrainingScheduleWorkouts1.add(validTrainingScheduleWorkout1);
        validTrainingScheduleWorkouts1.add(validTrainingScheduleWorkout2);

        validTrainingSchedule.setName("TrainingSchedule1");
        validTrainingSchedule.setDescription("Description1");
        validTrainingSchedule.setDifficulty(1);
        validTrainingSchedule.setIntervalLength(3);
        validTrainingSchedule.setRating(3.0);
        validTrainingSchedule.setWorkouts(validTrainingScheduleWorkouts1);

    }

    @Test
    public void whenCopyTrainingSchedule_thenCopyAndOriginalAreTheSame() throws ServiceException {
        TrainingSchedule ts = new TrainingSchedule();
        ts.setName(validTrainingSchedule.getName());
        ts.setDifficulty(validTrainingSchedule.getDifficulty());
        // to be continued
    }

    @Test
    public void whenBookmarkOneWorkout_thenSuccess() throws ServiceException {
        Dude dude = new Dude();
        dude.setId(1L);
        Optional<Dude> optionalDude = Optional.of(dude);
        Optional<TrainingSchedule> optionalTS = Optional.of(validTrainingSchedule);
        Mockito.when(dudeRepository.findById(1L)).thenReturn(optionalDude);
        Mockito.when(trainingScheduleRepository.findByIdAndVersion(1L, 1)).thenReturn(optionalTS);
        Mockito.when(trainingScheduleBookmarkRepository.checkTrainingScheduleBookmark(1L, 1L, 1)).thenReturn(0);
        trainingScheduleService.saveTrainingScheduleBookmark(1L, 1L, 1);
    }

    @Test(expected = ServiceException.class)
    public void whenBookmarkOneAlreadyBookmarkedWorkout_thenServiceException() throws ServiceException {
        Dude dude = new Dude();
        dude.setId(1L);
        Optional<Dude> optionalDude = Optional.of(dude);
        Optional<TrainingSchedule> optionalTS = Optional.of(validTrainingSchedule);
        Mockito.when(dudeRepository.findById(1L)).thenReturn(optionalDude);
        Mockito.when(trainingScheduleRepository.findByIdAndVersion(1L, 1)).thenReturn(optionalTS);
        Mockito.when(trainingScheduleBookmarkRepository.checkTrainingScheduleBookmark(1L, 1L, 1)).thenReturn(1);
        trainingScheduleService.saveTrainingScheduleBookmark(1L, 1L, 1);
    }
}
