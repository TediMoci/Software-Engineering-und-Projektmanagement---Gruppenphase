package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IWorkoutService;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class WorkoutServiceTest {

    private static final Workout validWorkout1 = new Workout();
    private static final Workout invalidWorkout1 = new Workout();

    @MockBean
    private IWorkoutRepository workoutRepository;

    @MockBean
    private IWorkoutExerciseRepository workoutExerciseRepository;

    @MockBean
    private IExerciseRepository exerciseRepository;

    @Autowired
    private IWorkoutService workoutService;

    @BeforeClass
    public static void beforeClass() {
        Dude dude = new Dude();
        dude.setId(1L);

        WorkoutExercise validWorkoutExercise1 = new WorkoutExercise();
        List<WorkoutExercise> validWorkoutExercises1 = new ArrayList<>();
        validWorkoutExercises1.add(validWorkoutExercise1);

        WorkoutExercise invalidWorkoutExercise1 = new WorkoutExercise();
        invalidWorkoutExercise1.setSets(0);
        List<WorkoutExercise> invalidWorkoutExercises1 = new ArrayList<>();
        invalidWorkoutExercises1.add(invalidWorkoutExercise1);

        validWorkout1.setName("Workout1");
        validWorkout1.setDifficulty(1);
        validWorkout1.setCalorieConsumption(100.0);
        validWorkout1.setCreator(dude);
        validWorkout1.setExercises(validWorkoutExercises1);

        invalidWorkout1.setName("Workout1");
        invalidWorkout1.setDifficulty(1);
        invalidWorkout1.setCalorieConsumption(100.0);
        invalidWorkout1.setCreator(dude);
        invalidWorkout1.setExercises(invalidWorkoutExercises1);
    }

    @Test
    public void whenSaveOneWorkout_thenGetSavedWorkout() throws ServiceException {
        Optional<Exercise> optionalExercise = Optional.of(new Exercise());
        Mockito.when(workoutRepository.save(validWorkout1)).thenReturn(validWorkout1);
        for (WorkoutExercise workoutExercise : validWorkout1.getExercises()) {
            Mockito.when(exerciseRepository.findByIdAndVersion(workoutExercise.getExerciseId(), workoutExercise.getExerciseVersion())).thenReturn(optionalExercise);
            Mockito.when(workoutExerciseRepository.save(workoutExercise)).thenReturn(workoutExercise);
        }
        assertEquals(workoutService.save(validWorkout1), validWorkout1);
    }

    @Test(expected = ServiceException.class)
    public void whenSaveOneInvalidWorkout_thenServiceException() throws ServiceException {
        Optional<Exercise> optionalExercise = Optional.of(new Exercise());
        Mockito.when(workoutRepository.save(invalidWorkout1)).thenReturn(invalidWorkout1);
        for (WorkoutExercise workoutExercise : invalidWorkout1.getExercises()) {
            Mockito.when(exerciseRepository.findByIdAndVersion(workoutExercise.getExerciseId(), workoutExercise.getExerciseVersion())).thenReturn(optionalExercise);
            Mockito.when(workoutExerciseRepository.save(workoutExercise)).thenReturn(workoutExercise);
        }
        workoutService.save(invalidWorkout1);
    }

}
