package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.ICourseMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/course")
@Api(value = "course")
public class CourseEndpoint {

    private final ICourseService iCourseService;
    private final ICourseMapper courseMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseEndpoint.class);

    public CourseEndpoint(ICourseService iCourseService, ICourseMapper courseMapper) {
        this.iCourseService = iCourseService;
        this.courseMapper = courseMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Save a new Course", authorizations = {@Authorization(value = "apiKey")})
    public CourseDto save(@Valid @RequestBody CourseDto courseDto) {
        LOGGER.info("Entering save for: " + courseDto);
        Course course = courseMapper.courseDtoToCourse(courseDto);
        try {
            return courseMapper.courseToCourseDto(iCourseService.save(course));
        } catch (ServiceException e) {
            LOGGER.error("Could not save: " + courseDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a Course by id", authorizations = {@Authorization(value = "apiKey")})
    public CourseDto findById(@PathVariable Long id) {
        LOGGER.info("Entering findById with id: " + id);
        try {
            return courseMapper.courseToCourseDto(iCourseService.findById(id));
        } catch (ServiceException e) {
            LOGGER.error("Could not find course with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get Courses by name", authorizations = {@Authorization(value = "apiKey")})
    public List<CourseDto> findByName(@RequestParam String name) {
        LOGGER.info("Entering findByName with name: " + name);
        List<Course> courses;
        try {
            courses = iCourseService.findByName(name);
        } catch (ServiceException e) {
            LOGGER.error("Could not find courses with name: " + name);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses) {
            courseDtos.add(courseMapper.courseToCourseDto(course));
        }
        return courseDtos;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "Get all Courses", authorizations = {@Authorization(value = "apiKey")})
    public List<CourseDto> findAll() {
        LOGGER.info("Entering findAll");
        List<Course> courses;
        try {
            courses = iCourseService.findAll();
        } catch (ServiceException e) {
            LOGGER.error("Could not find all courses");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses) {
            courseDtos.add(courseMapper.courseToCourseDto(course));
        }
        return courseDtos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update a Course", authorizations = {@Authorization(value = "apiKey")})
    public CourseDto updateCourse(@PathVariable("id") long id, @RequestBody CourseDto newCourse) {
        LOGGER.info("Updating course with id: " + id);
        try {
            return courseMapper.courseToCourseDto(iCourseService.update(id, courseMapper.courseDtoToCourse(newCourse)));
        } catch (ServiceException e){
            LOGGER.error("Could not update course with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a Course", authorizations = {@Authorization(value = "apiKey")})
    public void deleteCourse(@PathVariable("id") long id) {
        LOGGER.info("Deleting course with id: " + id);
        try {
            iCourseService.delete(id);
        } catch (ServiceException e){
            LOGGER.error("Could not delete course with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
