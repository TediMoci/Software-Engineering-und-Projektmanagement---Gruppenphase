package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.util.List;

public interface ICourseService {

    /**
     * @param course
     * @return
     * @throws ServiceException
     */
    Course save(Course course) throws ServiceException;

    /**
     * @param id
     * @return
     * @throws ServiceException
     */
    Course findById(Long id) throws ServiceException;

    /**
     * @param name
     * @return
     * @throws ServiceException
     */
    List<Course> findByName(String name) throws ServiceException;

    /**
     * @return
     * @throws ServiceException
     */
    List<Course> findAll() throws ServiceException;

}
