package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.MuscleGroup;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.ExerciseBookmarkRepository;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class ExerciseServiceTest {

    private final static Exercise validExercise1 = new Exercise();
    private final static Exercise validExercise2 = new Exercise();
    private final static List<Exercise> validExercises2 = new ArrayList<Exercise>();

    @MockBean
    private IExerciseRepository exerciseRepository;

    @MockBean
    private ExerciseBookmarkRepository exerciseBookmarkRepository;

    @MockBean
    private IDudeRepository dudeRepository;

    @Autowired
    private IExerciseService exerciseService;

    @BeforeClass
    public static void beforeClass() {
        Dude dude = new Dude();
        dude.setId(1L);

        validExercise1.setId(1L);
        validExercise1.setVersion(1);
        validExercise1.setName("Name1");
        validExercise1.setHistory(false);
        validExercise1.setCategory(Category.Strength);
        validExercise1.setDescription("Description1");
        validExercise1.setEquipment("Equipment1");
        validExercise1.setMuscleGroup(MuscleGroup.Other);
        validExercise1.setRatingNum(1);
        validExercise1.setRatingSum(1);
        validExercise1.setCreator(dude);

        validExercise2.setId(2L);
        validExercise2.setVersion(1);
        validExercise2.setName("Name2");
        validExercise2.setHistory(false);
        validExercise2.setCategory(Category.Endurance);
        validExercise2.setDescription("Description2");
        validExercise2.setEquipment("Equipment2");
        validExercise2.setMuscleGroup(MuscleGroup.Other);
        validExercise2.setRatingNum(1);
        validExercise2.setRatingSum(1);
        validExercise2.setCreator(dude);

        validExercises2.add(validExercise2);
    }

    private Exercise buildExercise(Exercise e){
        Exercise exercise = new Exercise();
        exercise.setId(e.getId());
        exercise.setVersion(e.getVersion());
        exercise.setName(e.getName());
        exercise.setHistory(e.getHistory());
        exercise.setCategory(e.getCategory());
        exercise.setDescription(e.getDescription());
        exercise.setEquipment(e.getEquipment());
        exercise.setMuscleGroup(e.getMuscleGroup());
        exercise.setRatingSum(e.getRatingSum());
        exercise.setRatingNum(e.getRatingNum());
        exercise.setCreator(e.getCreator());
        return exercise;
    }

    @Test
    public void whenSaveOneExercise_thenGetSavedExercise() throws ServiceException {
        Exercise exercise = buildExercise(validExercise1);
        Mockito.when(exerciseRepository.save(exercise)).thenReturn(exercise);
        assertEquals(exerciseService.save(exercise),exercise);
    }

    @Test(expected = ServiceException.class)
    public void whenSaveOneExercise_ifDataAccessException_thenServiceException() throws ServiceException {
        Exercise exercise = buildExercise(validExercise1);
        Mockito.when(exerciseRepository.save(anyObject())).thenThrow(Mockito.mock(DataAccessException.class));
        exerciseService.save(exercise);
    }

    @Test
    public void whenFindByIdAndVersion_thenGetFoundExercise() throws ServiceException {
        Exercise exercise = buildExercise(validExercise1);
        Mockito.when(exerciseRepository.findByIdAndVersion(anyLong(),anyInt())).thenReturn(java.util.Optional.of(exercise));
        assertEquals(exerciseService.findByIdAndVersion(exercise.getId(),exercise.getVersion()),exercise);
    }

    @Test(expected = ServiceException.class)
    public void whenFindByIdAndVersion_ifNotFound_thenServiceException() throws ServiceException {
        Mockito.when(exerciseRepository.findByIdAndVersion(anyLong(),anyInt())).thenThrow(Mockito.mock(NoSuchElementException.class));
        exerciseService.findByIdAndVersion(1L,1);
    }

    @Test
    public void whenFindByName_thenGetFoundExercise() throws ServiceException {
        List<Exercise> exercises = new ArrayList<>();
        Exercise exercise = buildExercise(validExercise1);
        exercises.add(exercise);
        exercises.add(exercise);
        Mockito.when(exerciseRepository.findByName(anyString())).thenReturn(exercises);
        assertEquals(exerciseService.findByName(exercise.getName(), 1L),exercises);
    }

    @Test(expected = ServiceException.class)
    public void whenFindByName_ifDataAccessException_thenServiceException() throws ServiceException {
        Mockito.when(exerciseRepository.findByName(anyString())).thenThrow(Mockito.mock(DataAccessException.class));
        exerciseService.findByName("anyName", 1L);
    }

    @Test
    public void whenFindAll_thenGetFoundExercises() throws ServiceException {
        List<Exercise> exercises = new ArrayList<>();
        Exercise exercise = buildExercise(validExercise1);
        exercises.add(exercise);
        exercises.add(exercise);
        Mockito.when(exerciseRepository.findAll()).thenReturn(exercises);
        assertEquals(exerciseService.findAll(1L),exercises);
    }

    @Test(expected = ServiceException.class)
    public void whenFindAll_ifDataAccessException_thenServiceException() throws ServiceException {
        Mockito.when(exerciseRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
        exerciseService.findAll(1L);
    }

    @Test
    public void whenFindOneExerciseById_thenGetFoundExercise() throws ServiceException {
        Mockito.when(exerciseRepository.findById(anyLong())).thenReturn(validExercise1);
        assertEquals(exerciseService.findById(1L), validExercise1);
    }

    @Test(expected = ServiceException.class)
    public void whenFindOneExerciseById_ifDataAccessException_thenServiceException() throws ServiceException {
        Mockito.when(exerciseRepository.findById(anyLong())).thenThrow(Mockito.mock(DataAccessException.class));
        exerciseService.findById(1L);
    }

    @Test
    public void whenUpdateExercise_thenGetUpdatedExercise() throws ServiceException {
        Exercise exercise1 = buildExercise(validExercise1);
        Exercise exercise2 = buildExercise(validExercise2);
        Mockito.when(exerciseRepository.findById(1L)).thenReturn(exercise1);
        Mockito.when(exerciseRepository.save(anyObject())).thenReturn(exercise2);
        assertEquals(exerciseService.update(1L,exercise2),exercise2);
    }

    @Test(expected = ServiceException.class)
    public void whenUpdateExercise_ifExerciseNotFound_thenServiceException() throws ServiceException {
        Exercise exercise = buildExercise(validExercise1);
        Mockito.when(exerciseRepository.findById(anyLong())).thenReturn(null);
        Mockito.when(exerciseRepository.save(anyObject())).thenReturn(exercise);
        exerciseService.update(1L,exercise);
    }

    @Test(expected = ServiceException.class)
    public void whenDeleteExercise_ifExerciseNotFound_thenServiceException() throws ServiceException {
        Exercise exercise = buildExercise(validExercise1);
        Mockito.when(exerciseRepository.findById(anyLong())).thenReturn(null);
        exerciseService.delete(exercise.getId());
    }

    @Test
    public void whenFindByFilter_thenGetExerciseWhereFilterTrueAndNotExerciseWhereFilterFalse(){
        Mockito.when(exerciseRepository.findByFilterWithoutMuscleGroupAndWithCategory("2",Category.Endurance)).thenReturn(validExercises2);
        assertEquals(exerciseRepository.findByFilterWithoutMuscleGroupAndWithCategory("2",Category.Endurance), validExercises2);
        assertFalse(exerciseRepository.findByFilterWithoutMuscleGroupAndWithCategory("2",Category.Endurance).contains(validExercise1));
    }

    @Test
    public void whenBookmarkOneExercise_thenSuccess() throws ServiceException {
        Dude dude = new Dude();
        dude.setId(1L);
        Optional<Dude> optionalDude = Optional.of(dude);
        Optional<Exercise> optionalExercise = Optional.of(validExercise1);
        Mockito.when(dudeRepository.findById(1L)).thenReturn(optionalDude);
        Mockito.when(exerciseRepository.findByIdAndVersion(1L, 1)).thenReturn(optionalExercise);
        Mockito.when(exerciseBookmarkRepository.checkExerciseBookmark(1L, 1L, 1)).thenReturn(0);
        exerciseService.saveExerciseBookmark(1L, 1L, 1);
    }

    @Test(expected = ServiceException.class)
    public void whenBookmarkOneAlreadyBookmarkedExercise_thenServiceException() throws ServiceException {
        Dude dude = new Dude();
        dude.setId(1L);
        Optional<Dude> optionalDude = Optional.of(dude);
        Optional<Exercise> optionalExercise = Optional.of(validExercise1);
        Mockito.when(dudeRepository.findById(1L)).thenReturn(optionalDude);
        Mockito.when(exerciseRepository.findByIdAndVersion(1L, 1)).thenReturn(optionalExercise);
        Mockito.when(exerciseBookmarkRepository.checkExerciseBookmark(1L, 1L, 1)).thenReturn(1);
        exerciseService.saveExerciseBookmark(1L, 1L, 1);
    }

}
