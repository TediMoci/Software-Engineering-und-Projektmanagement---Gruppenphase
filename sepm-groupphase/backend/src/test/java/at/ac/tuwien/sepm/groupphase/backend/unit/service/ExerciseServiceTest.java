package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class ExerciseServiceTest {

    private final static Exercise exercise1 = new Exercise();
    private final static Exercise exercise2 = new Exercise();

    @MockBean
    IExerciseRepository exerciseRepository;

    @Autowired
    IExerciseService exerciseService;

    @BeforeClass
    public static void beforeClass() {
        Dude dude = new Dude();
        dude.setId(1L);

        exercise1.setId(1L);
        exercise1.setVersion(1);
        exercise1.setName("Name1");
        exercise1.setHistory(false);
        exercise1.setCategory(Category.Strength);
        exercise1.setDescription("Description1");
        exercise1.setEquipment("Equipment1");
        exercise1.setMuscleGroup("Muscles1");
        exercise1.setRating(1.0);
        exercise1.setCreator(dude);

        exercise2.setId(2L);
        exercise2.setVersion(1);
        exercise2.setName("Name2");
        exercise2.setHistory(false);
        exercise2.setCategory(Category.Endurance);
        exercise2.setDescription("Description2");
        exercise2.setEquipment("Equipment2");
        exercise2.setMuscleGroup("Muscles2");
        exercise2.setRating(1.0);
        exercise2.setCreator(dude);
    }

    @Test
    public void TestSaveExercise() throws ServiceException {
        Mockito.when(exerciseRepository.save(exercise1)).thenReturn(exercise1);
        assertEquals(exerciseService.save(exercise1),exercise1);
    }

    @Test(expected = ServiceException.class)
    public void TestSaveExercise_DataAccessException() throws ServiceException {
        Mockito.when(exerciseRepository.save(anyObject())).thenThrow(Mockito.mock(DataAccessException.class));
        exerciseService.save(exercise1);
    }

    @Test
    public void TestFindByIdAndVersion() throws ServiceException {
        Mockito.when(exerciseRepository.findByIdAndVersion(anyLong(),anyInt())).thenReturn(java.util.Optional.of(exercise1));
        assertEquals(exerciseService.findByIdAndVersion(exercise1.getId(),exercise1.getVersion()),exercise1);
    }

    @Test(expected = ServiceException.class)
    public void TestFindByIdAndVersionNotFound() throws ServiceException {
        Mockito.when(exerciseRepository.findByIdAndVersion(anyLong(),anyInt())).thenThrow(Mockito.mock(NoSuchElementException.class));
        exerciseService.findByIdAndVersion(exercise1.getId(),exercise1.getVersion());
    }

    @Test
    public void TestFindByName() throws ServiceException {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise1);
        exercises.add(exercise1);
        Mockito.when(exerciseRepository.findByName(anyString())).thenReturn(exercises);
        assertEquals(exerciseService.findByName(exercise1.getName()),exercises);
    }

    @Test
    public void TestUpdate() throws ServiceException {
        Mockito.when(exerciseRepository.findById(anyLong())).thenReturn(exercise1);
        Mockito.when(exerciseRepository.save(anyObject())).thenReturn(exercise2);
        assertEquals(exerciseService.update(exercise1.getId(),exercise2),exercise2);
    }

    @Test(expected = ServiceException.class)
    public void TestUpdate_NotFound() throws ServiceException {
        Mockito.when(exerciseRepository.findById(anyLong())).thenReturn(null);
        Mockito.when(exerciseRepository.save(anyObject())).thenReturn(exercise2);
        exerciseService.update(exercise1.getId(),exercise2);
    }

    @Test(expected = ServiceException.class)
    public void TestDelete_NotFound() throws ServiceException {
        Mockito.when(exerciseRepository.findById(anyLong())).thenReturn(null);
        exerciseService.delete(exercise1.getId());
    }
}
