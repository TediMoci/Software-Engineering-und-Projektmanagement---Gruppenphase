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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class CourseServiceTest {

    private final static Course course1 = new Course();

    @MockBean
    private ICourseRepository courseRepository;

    @Autowired
    private ICourseService courseService;

    @BeforeClass
    public static void beforeClass() {
        FitnessProvider fitnessProvider = new FitnessProvider();
        fitnessProvider.setId(1L);

        course1.setName("Course1");
        course1.setDescription("Description1");
        course1.setCreator(fitnessProvider);

    }

    @Test
    public void whenSaveOneValidCourse_thenSuccess() throws ServiceException {
        Mockito.when(courseRepository.save(course1)).thenReturn(course1);
        assertEquals(courseService.save(course1), course1);
    }

    @Test(expected = ServiceException.class)
    public void whenSaveOneInvalidCourse_thenServiceException() throws ServiceException {
        Mockito.when(courseRepository.save(anyObject())).thenThrow(Mockito.mock(DataAccessException.class));
        courseService.save(course1);
    }



}
