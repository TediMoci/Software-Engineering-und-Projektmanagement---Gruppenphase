package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.ICourseMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IDudeMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IFitnessProviderMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.IFileStorageService;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
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
@RequestMapping(value = "/fitnessProvider")
@Api(value = "fitnessProvider")
public class FitnessProviderEndpoint {

    private final IFitnessProviderService iFitnessProviderService;
    private final IFileStorageService iFileStorageService;
    private final IFitnessProviderMapper fitnessProviderMapper;
    private final ICourseMapper courseMapper;
    private final IDudeMapper dudeMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(FitnessProviderEndpoint.class);

    public FitnessProviderEndpoint(IFitnessProviderService iFitnessProviderService, IFitnessProviderMapper fitnessProviderMapper, ICourseMapper courseMapper, IDudeMapper dudeMapper,IFileStorageService iFileStorageService ) {
        this.iFitnessProviderService = iFitnessProviderService;
        this.iFileStorageService = iFileStorageService;
        this.fitnessProviderMapper = fitnessProviderMapper;
        this.courseMapper = courseMapper;
        this.dudeMapper = dudeMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
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
    public FitnessProviderDto updateFitnessProvider(@PathVariable("name") String name, @Valid @RequestBody FitnessProviderDto fitnessProvider) {
        try {
            return fitnessProviderMapper.fitnessProviderToFitnessProviderDto(iFitnessProviderService.update(name, fitnessProviderMapper.fitnessProviderDtoToFitnessProvider(fitnessProvider)));
        } catch (ServiceException e){
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

    @RequestMapping(value = "/{id}/allFollowers", method = RequestMethod.GET)
    @ApiOperation(value = "Get all followers of the fitness provider with the given id", authorizations ={ @Authorization(value = "apiKey")})
    public DudeDto[] getDudesFollowingFitnessProvider(@PathVariable Long id) {
        LOGGER.info("Entering getDudesFollowingFitnessProvider with id: " + id);
        List<Dude> allDudes;
        try {
            allDudes = iFitnessProviderService.findById(id).getDudes();
        } catch (ServiceException e) {
            LOGGER.error("Could not getDudesFollowingFitnessProvider with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<Dude> dudes = new ArrayList<>();
        for (Dude dude : allDudes) {
            if (!dude.getIsPrivate()) {
                dudes.add(dude);
            }
        }
        DudeDto[] dudeDtos = new DudeDto[dudes.size()];
        for (int i = 0; i < dudes.size(); i++) {
            dudeDtos[i] = dudeMapper.dudeToDudeDto(dudes.get(i));
            dudeDtos[i].setPassword("Not available");
        }
        return dudeDtos;
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

    @RequestMapping(value = "/filtered", method = RequestMethod.GET)
    @ApiOperation(value = "Get Fitness Provider by filters", authorizations = {@Authorization(value = "apiKey")})
    public FitnessProviderDto[] findByFilter(@RequestParam(defaultValue = "") String filter) {
        LOGGER.info("Entering findByFilter with filter: " + filter);
        List<FitnessProvider> fitnessProviders;
        try {
            fitnessProviders = iFitnessProviderService.findByFilter(filter);
        } catch (ServiceException e) {
            LOGGER.error("Could not findByFilter with filter: " + filter);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        FitnessProviderDto[] fitnessProviderDtos = new FitnessProviderDto[fitnessProviders.size()];
        for (int i = 0; i < fitnessProviders.size(); i++) {
            fitnessProviderDtos[i] = fitnessProviderMapper.fitnessProviderToFitnessProviderDto(fitnessProviders.get(i));
        }
        return fitnessProviderDtos;
    }

    @PostMapping("/{id}/uploadImage")
    @ApiOperation(value = "Upload image for FitnessProvider", authorizations = {@Authorization(value = "apiKey")})
    public String uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        LOGGER.info("Entering uploadImage with id: " + id);
        String fileName = "fitness_provider_" + id + ".png";

        try {
            iFileStorageService.storeFile(fileName, file);
        } catch (ServiceException e) {
            LOGGER.error("Could not uploadImage with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        try {
            return iFitnessProviderService.updateImagePath(id, fileName);
        } catch (ServiceException e) {
            LOGGER.error("Could not updateImagePath with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
