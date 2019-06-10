package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.util.List;

public interface ICourseService {

    /**
     * @param course to be saved in the system
     * @return the saved Course
     * @throws ServiceException if an error occurred while trying to save the Course in the system
     */
    Course save(Course course) throws ServiceException;

    /**
     * @param id of the Course to find
     * @return the Course with the given id
     * @throws ServiceException if an error occurred while trying to find the Course in the system
     */
    Course findById(Long id) throws ServiceException;

    /**
     * @param name of the Courses to find
     * @return Courses with name beginning with the given name-string
     * @throws ServiceException if an error occurred while trying to find the Courses in the system
     */
    List<Course> findByName(String name) throws ServiceException;

    /**
     * @return all Courses in the system
     * @throws ServiceException if an error occurred while trying to find the Courses in the system
     */
    List<Course> findAll() throws ServiceException;

    /**
     * @param filter containing the string to be filtered for across all string-values of the entity
     * @return all Courses in the system according to the given filters
     * @throws ServiceException if an error occurred while trying to find the Exercises in the system
     */
    List<Course> findByFilter(String filter) throws ServiceException;

    /**
     * @param id of Course to be found
     * @param newCourse that will be updated
     * @return updated Course
     * @throws ServiceException if an error occurred while trying to find the Course in the system
     */
    Course update(long id, Course newCourse) throws ServiceException;

    /**
     * @param id of Course to be deleted
     * @throws ServiceException if an error occurred while trying to find the Course in the system
     */
    void delete(long id) throws ServiceException;

    /**
     * @param id of the Course
     * @param fileName of the image in the path
     * @return the full image-path
     * @throws ServiceException if something went wrong while updating the imagePath in the system
     */
    String updateImagePath(Long id, String fileName) throws ServiceException;

}
