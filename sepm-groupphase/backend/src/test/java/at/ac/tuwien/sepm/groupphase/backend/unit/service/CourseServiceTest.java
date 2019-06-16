package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ICourseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ICourseService;
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
public class CourseServiceTest {

    private final static Course course1 = new Course();
    private final static Course course2 = new Course();
    private final static List<Course> courses2 = new ArrayList<Course>();

    @MockBean
    private ICourseRepository courseRepository;

    @Autowired
    private ICourseService courseService;

    @BeforeClass
    public static void beforeClass() {
        FitnessProvider fitnessProvider = new FitnessProvider();
        fitnessProvider.setId(1L);

        course1.setId(1L);
        course1.setName("Course1");
        course1.setDescription("Description1");
        course1.setCreator(fitnessProvider);
        course1.setRatingNum(1);
        course1.setRatingSum(1);

        course2.setId(2L);
        course2.setName("Course2");
        course2.setDescription("Description2");
        course2.setCreator(fitnessProvider);
        course2.setRatingNum(1);
        course2.setRatingSum(1);

        courses2.add(course2);
    }

    @Test
    public void whenSaveOneCourse_thenGetSavedCourse() throws ServiceException {
        Mockito.when(courseRepository.save(course1)).thenReturn(course1);
        assertEquals(courseService.save(course1), course1);
    }

    @Test(expected = ServiceException.class)
    public void whenSaveOneInvalidCourse_thenServiceException() throws ServiceException {
        Mockito.when(courseRepository.save(anyObject())).thenThrow(Mockito.mock(DataAccessException.class));
        courseService.save(course1);
    }

    @Test
    public void whenFindOneCourseById_thenGetFoundCourse() throws ServiceException {
        Mockito.when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course1));
        assertEquals(courseService.findById(1L), course1);
    }

    @Test(expected = ServiceException.class)
    public void whenFindOneCourseById_ifNoSuchElementException_thenServiceException() throws ServiceException {
        Mockito.when(courseRepository.findById(anyLong())).thenThrow(Mockito.mock(NoSuchElementException.class));
        assertEquals(courseService.findById(1L), course1);
    }

    @Test
    public void whenFindByName_thenGetListContainingCourse() throws ServiceException {
        Mockito.when(courseRepository.findByName(anyString())).thenReturn(courses2);
        assertEquals(courseService.findByName("Course2"), courses2);
    }

    @Test(expected = ServiceException.class)
    public void whenFindByName_ifDataAccessException_thenServiceException() throws ServiceException {
        Mockito.when(courseRepository.findByName(anyString())).thenThrow(Mockito.mock(DataAccessException.class));
        courseService.findByName("Course2");
    }

    @Test
    public void whenFindAll_thenGetListOfCourses() throws ServiceException {
        Mockito.when(courseRepository.findAll()).thenReturn(courses2);
        assertEquals(courseService.findAll(), courses2);
    }

    @Test(expected = ServiceException.class)
    public void whenFindAll_ifDataAccessException_thenServiceException() throws ServiceException {
        Mockito.when(courseRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
        courseService.findAll();
    }

    @Test(expected = ServiceException.class)
    public void whenDeleteCourse_ifNoSuchElementException_thenServiceException() throws ServiceException {
        Mockito.when(courseRepository.findById(anyLong())).thenThrow(Mockito.mock(NoSuchElementException.class));
        courseService.delete(1L);
    }

    @Test
    public void whenUpdateOneCourse_thenGetUpdatedCourse() throws ServiceException {
        Optional<Course> optionalCourse1 = Optional.of(course1);
        Mockito.when(courseRepository.findById(1L)).thenReturn(optionalCourse1);
        Mockito.when(courseRepository.save(course2)).thenReturn(course2);
        assertEquals(courseService.update(course1.getId(), course2), course2);
    }

    @Test(expected = ServiceException.class)
    public void whenUpdateOneCourseWithInvalidId_thenServiceException() throws ServiceException {
        Mockito.when(courseRepository.findById(3L)).thenReturn(Optional.empty());
        Mockito.when(courseRepository.save(course2)).thenReturn(course2);
        assertEquals(courseService.update(3L, course2), course2);
    }
    @Test
    public void whenFindByFilterByCharacter2_thenGetCourseWhereNameContains2andNotCourseWhereNameContains1(){
        Mockito.when(courseRepository.findByFilter("2")).thenReturn(courses2);
        assertEquals(courseRepository.findByFilter("2"), courses2);
        assertFalse(courseRepository.findByFilter("2").contains(course1));
    }

}
