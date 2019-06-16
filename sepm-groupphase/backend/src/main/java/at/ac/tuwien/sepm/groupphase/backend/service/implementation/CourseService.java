package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.CourseRating;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ICourseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.ICourseRatingRepository;
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
    private final ICourseRatingRepository iCourseRatingRepository;
    private final IDudeRepository iDudeRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    public CourseService(ICourseRepository iCourseRepository, ICourseRatingRepository iCourseRatingRepository, IDudeRepository iDudeRepository) {
        this.iCourseRepository = iCourseRepository;
        this.iCourseRatingRepository = iCourseRatingRepository;
        this.iDudeRepository = iDudeRepository;
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
            newCourse.setRatingNum(oldCourse.getRatingNum());
            newCourse.setRatingSum(oldCourse.getRatingSum());
            return iCourseRepository.save(newCourse);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        LOGGER.info("Deleting course with id: " + id);
        try {
            Course course = findById(id);
            if (course==null) throw new ServiceException("Could not find course with id: " + id);
            iCourseRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void saveCourseRating(Long dudeId, Long courseId, Integer rating) throws ServiceException {
        LOGGER.info("Entering saveCourseRating with dudeId: " + dudeId + "; courseId: " + courseId + "; rating: " + rating);
        // checks that rating is between 1 and 5
        try {
            if (rating < 1 || rating > 5) throw new ValidationException("Rating must be between 1 and 5");
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
        checkDudeCourseExist(dudeId,courseId);

        CourseRating courseRating = new CourseRating();
        courseRating.setDudeId(dudeId);
        courseRating.setCourseId(courseId);
        courseRating.setRating(rating);

        Course course = iCourseRepository.findById(courseId).get();

        // checks if dude has rated this content before or not
        if (iCourseRatingRepository.findRating(dudeId,courseId)==null){
            course.setRatingSum(rating+course.getRatingSum());
            course.setRatingNum(1+course.getRatingNum());

            iCourseRatingRepository.save(courseRating);
            iCourseRepository.save(course);
            System.out.println("Dude with id: " + dudeId + " rated Course with id: " + courseId + " with new rating: " + rating);
        } else{
            CourseRating foundRating = iCourseRatingRepository.findRating(dudeId, courseId);
            course.setRatingSum(rating + course.getRatingSum() - foundRating.getRating());

            iCourseRatingRepository.save(courseRating);
            iCourseRepository.save(course);
            System.out.println("Dude with id: " + dudeId + " rated Course with id: " + courseId + " with updated rating: " + rating);
        }
    }

    @Override
    public void deleteCourseRating(Long dudeId, Long courseId) throws ServiceException {
        LOGGER.info("Entering deleteCourseRating with dudeId: " + dudeId + "; courseId: " + courseId);
        CourseRating courseRating = iCourseRatingRepository.findRating(dudeId,courseId);

        if (courseRating==null){
            throw new ServiceException("Could not find rating to delete with dude id: " + dudeId + " and courseId " + courseId);
        } else{
            checkDudeCourseExist(dudeId,courseId);
            Course course = iCourseRepository.findById(courseId).get();

            course.setRatingSum(course.getRatingSum() - courseRating.getRating());
            course.setRatingNum(course.getRatingNum() - 1);

            try {
                iCourseRatingRepository.delete(courseRating);
                iCourseRepository.save(course);
            } catch (DataAccessException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

    private void checkDudeCourseExist(Long dudeId, Long courseId) throws ServiceException {
        try {
            if (iDudeRepository.findById(dudeId).isEmpty()) {
                throw new NoSuchElementException("Could not find Dude with id: " + dudeId);
            } else if (iCourseRepository.findById(courseId).isEmpty()) {
                throw new NoSuchElementException("Could not find Course with id: " + courseId);
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
