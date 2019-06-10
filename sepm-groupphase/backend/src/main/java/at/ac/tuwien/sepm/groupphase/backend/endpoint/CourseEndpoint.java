package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.ICourseMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.ICourseService;
import at.ac.tuwien.sepm.groupphase.backend.service.IFileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/course")
@Api(value = "course")
public class CourseEndpoint {

    private final ICourseService iCourseService;
    private final IFileStorageService iFileStorageService;
    private final ICourseMapper courseMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseEndpoint.class);

    public CourseEndpoint(ICourseService iCourseService, IFileStorageService iFileStorageService, ICourseMapper courseMapper) {
        this.iCourseService = iCourseService;
        this.iFileStorageService = iFileStorageService;
        this.courseMapper = courseMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
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
    public CourseDto[] findByName(@RequestParam String name) {
        LOGGER.info("Entering findByName with name: " + name);
        List<Course> courses;
        try {
            courses = iCourseService.findByName(name);
        } catch (ServiceException e) {
            LOGGER.error("Could not find courses with name: " + name);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        CourseDto[] courseDtos = new CourseDto[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseDtos[i] = courseMapper.courseToCourseDto(courses.get(i));
        }
        return courseDtos;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "Get all Courses", authorizations = {@Authorization(value = "apiKey")})
    public CourseDto[] findAll() {
        LOGGER.info("Entering findAll");
        List<Course> courses;
        try {
            courses = iCourseService.findAll();
        } catch (ServiceException e) {
            LOGGER.error("Could not find all courses");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        CourseDto[] courseDtos = new CourseDto[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseDtos[i] = courseMapper.courseToCourseDto(courses.get(i));
        }
        return courseDtos;
    }

    @RequestMapping(value = "/filtered", method = RequestMethod.GET)
    @ApiOperation(value = "Get Courses by filter", authorizations = {@Authorization(value = "apiKey")})
    public CourseDto[] findByFilter(@RequestParam(defaultValue = "") String filter) {
        LOGGER.info("Entering findByFilter with filter: " + filter);
        List<Course> courses;
        try {
            courses = iCourseService.findByFilter(filter);
        } catch (ServiceException e) {
            LOGGER.error("Could not findByFilter with filter: " + filter);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        CourseDto[] courseDtos = new CourseDto[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseDtos[i] = courseMapper.courseToCourseDto(courses.get(i));
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

    @PostMapping("/{id}/uploadImage")
    @ApiOperation(value = "Upload image for Course", authorizations = {@Authorization(value = "apiKey")})
    public void uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        LOGGER.info("Entering uploadImage with id: " + id);
        String fileName = "course_" + id;
        if (file.getContentType().substring(file.getContentType().length() - 3).equals("png")) {
            fileName += ".png";
        } else {
            fileName += ".jpg";
        }
        iFileStorageService.storeFile(fileName, file);

        try {
            iCourseService.updateImagePath(id, fileName);
        } catch (ServiceException e) {
            LOGGER.error("Could not updateImagePath with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
