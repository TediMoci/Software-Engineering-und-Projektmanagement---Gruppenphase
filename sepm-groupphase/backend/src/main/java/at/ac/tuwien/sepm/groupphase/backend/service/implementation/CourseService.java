package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ICourseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ICourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseService implements ICourseService {

    private final ICourseRepository iCourseRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    public CourseService(ICourseRepository iCourseRepository) {
        this.iCourseRepository = iCourseRepository;
    }

    @Override
    public Course save(Course course) throws ServiceException {
        LOGGER.info("Entering save for: " + course);
        try {
            return iCourseRepository.save(course);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Course findById(Long id) throws ServiceException {
        LOGGER.info("Entering findById with id: " + id);
        try {
            return iCourseRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Course> findByName(String name) throws ServiceException {
        LOGGER.info("Entering findByName with name: " + name);
        try {
            return iCourseRepository.findByName(name);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Course> findAll() throws ServiceException {
        LOGGER.info("Entering findAll");
        try {
            return iCourseRepository.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
