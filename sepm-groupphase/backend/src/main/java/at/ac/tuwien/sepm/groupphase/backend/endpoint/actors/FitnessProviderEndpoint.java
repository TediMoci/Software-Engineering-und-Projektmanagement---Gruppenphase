package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.ICourseMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IFitnessProviderMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
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
@RequestMapping(value = "/fitnessProvider")
@Api(value = "fitnessProvider")
public class FitnessProviderEndpoint {

    private final IFitnessProviderService iFitnessProviderService;
    private final IFitnessProviderMapper fitnessProviderMapper;
    private final ICourseMapper courseMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(FitnessProviderEndpoint.class);

    public FitnessProviderEndpoint(IFitnessProviderService iFitnessProviderService, IFitnessProviderMapper fitnessProviderMapper, ICourseMapper courseMapper) {
        this.iFitnessProviderService = iFitnessProviderService;
        this.fitnessProviderMapper = fitnessProviderMapper;
        this.courseMapper = courseMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Save new Fitness Provider", authorizations = {@Authorization(value = "apiKey")})
    public FitnessProviderDto saveFitnessProvider(@Valid @RequestBody FitnessProviderDto fitnessProviderDto){
        FitnessProvider fitnessProvider = fitnessProviderMapper.fitnessProviderDtoToFitnessProvider(fitnessProviderDto);
        try{
            fitnessProvider = iFitnessProviderService.save(fitnessProvider);
            return fitnessProviderMapper.fitnessProviderToFitnessProviderDto(fitnessProvider);
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a fitness provider by id", authorizations = {@Authorization(value = "apiKey")})
    public FitnessProviderDto findById(@PathVariable Long id) {
        LOGGER.info("Entering findById with id: " + id);
        try {
            return fitnessProviderMapper.fitnessProviderToFitnessProviderDto(iFitnessProviderService.findById(id));
        } catch (ServiceException e) {
            LOGGER.error("Could not findById with id: " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get a fitness provider by name and password", authorizations ={ @Authorization(value = "apiKey")})
    public FitnessProviderDto findByName(String name){
        try{
            return fitnessProviderMapper.fitnessProviderToFitnessProviderDto(iFitnessProviderService.findByName(name));
        }catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{name}/followers", method = RequestMethod.GET)
    @ApiOperation(value = "Get the number of followers of the fitness provider with the given name", authorizations ={ @Authorization(value = "apiKey")})
    public Integer getNumberOfFollowers(@PathVariable String name) {
        try {
            return iFitnessProviderService.getNumberOfFollowers(name);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "Get all fitness providers", authorizations = {@Authorization(value = "apiKey")})
    public List<FitnessProviderDto> findAll() {
        List<FitnessProviderDto> fpListDto = new ArrayList<>();
        try {
            List<FitnessProvider> fpList = iFitnessProviderService.findAll();
            for (int i=0; i< fpList.size(); i++){
                fpListDto.add(fitnessProviderMapper.fitnessProviderToFitnessProviderDto(fpList.get(i)));
            }
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return fpListDto;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update a fitness provider", authorizations = {@Authorization(value = "apiKey")})
    public FitnessProviderDto updateFitnessProvider(@PathVariable("name") String name, @RequestBody FitnessProviderDto fitnessProvider) {
        try {
            return fitnessProviderMapper.fitnessProviderToFitnessProviderDto(iFitnessProviderService.update(name, fitnessProviderMapper.fitnessProviderDtoToFitnessProvider(fitnessProvider)));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}/courses", method = RequestMethod.GET)
    @ApiOperation(value = "Get courses created by fitness provider", authorizations = {@Authorization(value = "apiKey")})
    public CourseDto[] getCoursesCreatedByFitnessProviderId(@PathVariable Long id) {
        LOGGER.info("Entering getCoursesCreatedByFitnessProviderId with id: " + id);
        List<Course> courses;
        try {
            courses = iFitnessProviderService.findById(id).getCourses();
        } catch (ServiceException e) {
            LOGGER.error("Could not getCoursesCreatedByFitnessProviderId with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        CourseDto[] courseDtos = new CourseDto[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseDtos[i] = courseMapper.courseToCourseDto(courses.get(i));
        }
        return courseDtos;
    }

}
