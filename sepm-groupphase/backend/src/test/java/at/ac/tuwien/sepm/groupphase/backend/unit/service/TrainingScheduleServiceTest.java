package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class TrainingScheduleServiceTest {

    private static final TrainingSchedule validTrainingSchedule = new TrainingSchedule();
    private static final TrainingSchedule validTrainingSchedule2 = new TrainingSchedule();
    private static final List<TrainingSchedule> validTrainingSchedules = new ArrayList<>();
    private static final List<TrainingScheduleWorkout> validTrainingScheduleWorkouts1 = new ArrayList<>();

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
        validTrainingScheduleWorkout1.setWorkout(validWorkout1);
        validTrainingScheduleWorkout1.setDay(2);
        TrainingScheduleWorkout validTrainingScheduleWorkout2 = new TrainingScheduleWorkout();
        validTrainingScheduleWorkout2.setWorkout(validWorkout2);
        validTrainingScheduleWorkout2.setDay(3);
        validTrainingScheduleWorkouts1.add(validTrainingScheduleWorkout1);
        validTrainingScheduleWorkouts1.add(validTrainingScheduleWorkout2);

        validTrainingSchedule.setName("TrainingSchedule1");
        validTrainingSchedule.setDescription("Description1");
        validTrainingSchedule.setDifficulty(1);
        validTrainingSchedule.setIntervalLength(3);
        validTrainingSchedule.setRatingSum(1);
        validTrainingSchedule.setRatingNum(1);
        validTrainingSchedule.setWorkouts(validTrainingScheduleWorkouts1);

        validTrainingSchedule2.setName("TrainingSchedule2");
        validTrainingSchedule2.setDescription("Description2");
        validTrainingSchedule2.setDifficulty(2);
        validTrainingSchedule2.setIntervalLength(4);
        validTrainingSchedule2.setRatingSum(1);
        validTrainingSchedule2.setRatingNum(1);
        validTrainingSchedule2.setWorkouts(validTrainingScheduleWorkouts1);

        validTrainingSchedules.add(validTrainingSchedule2);
    }

    private TrainingSchedule buildTrainingSchedule(TrainingSchedule ts){
        TrainingSchedule trainingSchedule = new TrainingSchedule();
        trainingSchedule.setId(ts.getId());
        trainingSchedule.setVersion(ts.getVersion());
        trainingSchedule.setName(ts.getName());
        trainingSchedule.setHistory(ts.getHistory());
        trainingSchedule.setDifficulty(ts.getDifficulty());
        trainingSchedule.setDescription(ts.getDescription());
        trainingSchedule.setWorkouts(ts.getWorkouts());
        trainingSchedule.setIntervalLength(ts.getIntervalLength());
        trainingSchedule.setRatingSum(ts.getRatingSum());
        trainingSchedule.setRatingNum(ts.getRatingNum());
        trainingSchedule.setIsPrivate(ts.getIsPrivate());
        trainingSchedule.setCreator(ts.getCreator());
        trainingSchedule.setBookmarkDudes(ts.getBookmarkDudes());
        trainingSchedule.setActiveUsages(ts.getActiveUsages());
        return trainingSchedule;
    }

    @Test
    public void whenCopyTrainingSchedule_thenCopyAndOriginalContainTheSameTsWa_Wa_WaExAndEx() throws ServiceException {
        TrainingSchedule ts = new TrainingSchedule();
        ts.setName(validTrainingSchedule.getName());
        ts.setDifficulty(validTrainingSchedule.getDifficulty());
        // to be continued
    }

    @Test
    public void whenFindByFilter_thenGetTrainingScheduleWhereFilterTrueAndNotTrainingScheduleWhereFilterFalse(){
        Mockito.when(trainingScheduleRepository.findByFilterWithDifficulty("2",2)).thenReturn(validTrainingSchedules);
        assertEquals(trainingScheduleRepository.findByFilterWithDifficulty("2",2), validTrainingSchedules);
        assertFalse(trainingScheduleRepository.findByFilterWithDifficulty("1",1).contains(validTrainingSchedules.get(0)));
    }

    @Test
    public void whenFindOneTrainingScheduleByIdAndVersion_thenGetFoundTrainingSchedule() throws ServiceException{
        Mockito.when(trainingScheduleRepository.findByIdAndVersion(anyLong(),anyInt())).thenReturn(java.util.Optional.of(validTrainingSchedule));
        assertEquals(trainingScheduleService.findByIdAndVersion(1L,1), validTrainingSchedule);
    }

    @Test(expected = ServiceException.class)
    public void whenFindOneTrainingScheduleByIdAndVersion_ifNoSuchElementException_thenServiceException() throws ServiceException {
        Mockito.when(trainingScheduleRepository.findByIdAndVersion(anyLong(),anyInt())).thenThrow(Mockito.mock(NoSuchElementException.class));
        trainingScheduleService.findByIdAndVersion(1L,1);
    }

    @Test
    public void whenFindByName_thenGetFoundTrainingSchedules() throws ServiceException {
        List<TrainingSchedule> trainingSchedules = new ArrayList<>();
        TrainingSchedule trainingSchedule = buildTrainingSchedule(validTrainingSchedule);
        trainingSchedules.add(trainingSchedule);
        trainingSchedules.add(trainingSchedule);
        Mockito.when(trainingScheduleRepository.findByName(anyString())).thenReturn(trainingSchedules);
        assertEquals(trainingScheduleService.findByName(trainingSchedule.getName(), 1L), trainingSchedules);
    }

    @Test(expected = ServiceException.class)
    public void whenFindByName_ifDataAccessException_thenServiceException() throws ServiceException {
        Mockito.when(trainingScheduleRepository.findByName(anyString())).thenThrow(Mockito.mock(DataAccessException.class));
        trainingScheduleService.findByName("anyName", 1L);
    }

    @Test
    public void whenGetAllWorkoutsByTrainingScheduleIdAndVersion_thenGetFoundWorkouts() throws ServiceException{
        Mockito.when(trainingScheduleRepository.findByIdAndVersion(anyLong(), anyInt())).thenReturn(Optional.of(validTrainingSchedule));
        assertEquals(trainingScheduleService.findByIdAndVersion(1L, 1).getWorkouts(), validTrainingScheduleWorkouts1);
    }

    @Test(expected = ServiceException.class)
    public void whenGetAllWorkoutsByTrainingScheduleIdAndVersion_ifNoSuchElementException_thenServiceException() throws ServiceException {
        Mockito.when(trainingScheduleRepository.findByIdAndVersion(anyLong(), anyInt())).thenThrow(Mockito.mock(NoSuchElementException.class));
        trainingScheduleService.findByIdAndVersion(100L, 100).getWorkouts();
    }

    @Test
    public void whenBookmarkOneTs_thenSuccess() throws ServiceException {
        Dude dude = new Dude();
        dude.setId(1L);
        Optional<Dude> optionalDude = Optional.of(dude);
        Optional<TrainingSchedule> optionalTs = Optional.of(validTrainingSchedule);
        Mockito.when(dudeRepository.findById(1L)).thenReturn(optionalDude);
        Mockito.when(trainingScheduleRepository.findByIdAndVersion(1L, 1)).thenReturn(optionalTs);
        Mockito.when(trainingScheduleBookmarkRepository.checkTrainingScheduleBookmark(1L, 1L, 1)).thenReturn(0);
        trainingScheduleService.saveTrainingScheduleBookmark(1L, 1L, 1);
    }

    @Test(expected = ServiceException.class)
    public void whenBookmarkOneAlreadyBookmarkedTs_thenServiceException() throws ServiceException {
        Dude dude = new Dude();
        dude.setId(1L);
        Optional<Dude> optionalDude = Optional.of(dude);
        Optional<TrainingSchedule> optionalTs = Optional.of(validTrainingSchedule);
        Mockito.when(dudeRepository.findById(1L)).thenReturn(optionalDude);
        Mockito.when(trainingScheduleRepository.findByIdAndVersion(1L, 1)).thenReturn(optionalTs);
        Mockito.when(trainingScheduleBookmarkRepository.checkTrainingScheduleBookmark(1L, 1L, 1)).thenReturn(1);
        trainingScheduleService.saveTrainingScheduleBookmark(1L, 1L, 1);
    }

    @Test
    public void whenUpdateOneTrainingSchedule_thenGetUpdatedTrainingSchedule() throws ServiceException {
        TrainingSchedule trainingSchedule = buildTrainingSchedule(validTrainingSchedule2);
        Optional<Workout> optionalWorkout = Optional.of(new Workout());
        Mockito.when(trainingScheduleRepository.save(trainingSchedule)).thenReturn(trainingSchedule);
        Mockito.when(trainingScheduleRepository.findById(1L)).thenReturn(validTrainingSchedule);
        for (TrainingScheduleWorkout trainingScheduleWorkout : trainingSchedule.getWorkouts()) {
            Mockito.when(workoutRepository.findByIdAndVersion(trainingScheduleWorkout.getWorkoutId(), trainingScheduleWorkout.getWorkoutVersion())).thenReturn(optionalWorkout);
            Mockito.when(trainingScheduleWorkoutRepository.save(trainingScheduleWorkout)).thenReturn(trainingScheduleWorkout);
        }
        assertEquals(trainingScheduleService.update(1L, trainingSchedule), trainingSchedule);
    }

    @Test(expected = ServiceException.class)
    public void whenUpdateOneTrainingSchedule_ifTrainingScheduleNotFound_thenServiceException() throws ServiceException {
        TrainingSchedule trainingSchedule = buildTrainingSchedule(validTrainingSchedule2);
        Optional<Workout> optionalWorkout = Optional.of(new Workout());
        Mockito.when(trainingScheduleRepository.save(trainingSchedule)).thenReturn(trainingSchedule);
        Mockito.when(trainingScheduleRepository.findById(1L)).thenReturn(null);
        assertEquals(trainingScheduleService.update(1L, trainingSchedule), trainingSchedule);
    }

    @Test(expected = ServiceException.class)
    public void whenUpdateOneTrainingScheduleWithInvalidDay_thenServiceException() throws ServiceException {
        Dude dude = new Dude();
        dude.setId(1L);
        Workout workout = new Workout();
        workout.setName("Workout1");
        workout.setDifficulty(1);
        workout.setCalorieConsumption(100.0);
        workout.setCreator(dude);
        WorkoutExercise validWorkoutExercise1 = new WorkoutExercise();
        List<WorkoutExercise> validWorkoutExercises1 = new ArrayList<>();
        validWorkoutExercises1.add(validWorkoutExercise1);
        workout.setExercises(validWorkoutExercises1);

        TrainingSchedule trainingSchedule = buildTrainingSchedule(validTrainingSchedule2);
        TrainingScheduleWorkout invalidTrainingScheduleWorkout = new TrainingScheduleWorkout();
        invalidTrainingScheduleWorkout.setWorkout(workout);
        invalidTrainingScheduleWorkout.setDay(8);
        List<TrainingScheduleWorkout> invalidTrainingScheduleWorkouts = new ArrayList<>();
        invalidTrainingScheduleWorkouts.add(invalidTrainingScheduleWorkout);
        trainingSchedule.setWorkouts(invalidTrainingScheduleWorkouts);

        Optional<Workout> optionalWorkout = Optional.of(new Workout());
        Mockito.when(trainingScheduleRepository.save(trainingSchedule)).thenReturn(trainingSchedule);
        Mockito.when(trainingScheduleRepository.findById(1L)).thenReturn(validTrainingSchedule);
        for (TrainingScheduleWorkout trainingScheduleWorkout : trainingSchedule.getWorkouts()) {
            Mockito.when(workoutRepository.findByIdAndVersion(trainingScheduleWorkout.getWorkoutId(), trainingScheduleWorkout.getWorkoutVersion())).thenReturn(optionalWorkout);
            Mockito.when(trainingScheduleWorkoutRepository.save(trainingScheduleWorkout)).thenReturn(trainingScheduleWorkout);
        }
        assertEquals(trainingScheduleService.update(1L, trainingSchedule), trainingSchedule);
    }

    @Test(expected = ServiceException.class)
    public void whenDeleteTrainingSchedule_ifTrainingScheduleNotFound_thenServiceException() throws ServiceException {
        Mockito.when(trainingScheduleRepository.findById(anyLong())).thenReturn(null);
        trainingScheduleService.delete(1L);
    }
}
