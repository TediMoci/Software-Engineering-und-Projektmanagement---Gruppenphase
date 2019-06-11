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

    @Override
    public List<Course> findByFilter(String filter) throws ServiceException {
        LOGGER.info("Entering findByFilter with filter: " + filter);
        try {
            return iCourseRepository.findByFilter(filter);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Course update(long id, Course newCourse) throws ServiceException {
        LOGGER.info("Updating course with id: " + id);
        try {
            Course oldCourse = findById(id);
            if (oldCourse==null) throw new ServiceException("Could not find cours with id: " + id);
            newCourse.setId(oldCourse.getId());
            newCourse.setImagePath(oldCourse.getImagePath());
            return iCourseRepository.save(newCourse);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) throws ServiceException{
        LOGGER.info("Deleting course with id: " + id);
        try {
            Course course = findById(id);
            if (course==null) throw new ServiceException("Could not find cours with id: " + id);
            iCourseRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String updateImagePath(Long id, String fileName) throws ServiceException {
        LOGGER.info("Entering updateImagePath with id: " + id + "; fileName: " + fileName);
        Course course;
        try {
            course = iCourseRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        String imagePath = "/assets/img/" + fileName;
        course.setImagePath(imagePath);
        iCourseRepository.save(course);
        return imagePath;
    }
}
