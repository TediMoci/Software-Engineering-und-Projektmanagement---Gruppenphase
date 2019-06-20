package at.ac.tuwien.sepm.groupphase.backend.endpoint.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IExerciseMapper;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.MuscleGroup;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.IFileStorageService;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
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
import java.util.List;

@RestController
@RequestMapping(value = "/exercise")
@Api(value = "exercise")
public class ExerciseEndpoint {

    private final IExerciseService iExerciseService;
    private final IFileStorageService iFileStorageService;
    private final IExerciseMapper exerciseMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseEndpoint.class);

    public ExerciseEndpoint(IExerciseService iExerciseService, IFileStorageService iFileStorageService, IExerciseMapper exerciseMapper) {
        this.iExerciseService = iExerciseService;
        this.iFileStorageService = iFileStorageService;
        this.exerciseMapper = exerciseMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new Exercise", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto save(@Valid @RequestBody ExerciseDto exerciseDto) {
        LOGGER.info("Entering save for: " + exerciseDto);
        Exercise exercise = exerciseMapper.exerciseDtoToExercise(exerciseDto);
        try {
            return exerciseMapper.exerciseToExerciseDto(iExerciseService.save(exercise));
        } catch (ServiceException e) {
            LOGGER.error("Could not save: " + exerciseDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}/{version}", method = RequestMethod.GET)
    @ApiOperation(value = "Get an Exercise by id and version", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto findByIdAndVersion(@PathVariable Long id, @PathVariable Integer version) {
        LOGGER.info("Entering findByIdAndVersion with id: " + id + "; and version: " + version);
        try {
            return exerciseMapper.exerciseToExerciseDto(iExerciseService.findByIdAndVersion(id, version));
        } catch (ServiceException e) {
            LOGGER.error("Could not find exercise with id: " + id + "; and version: " + version);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{dudeId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get exercises with given name", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto[] findByName(@RequestParam String name, @PathVariable Long dudeId) {
        LOGGER.info("Entering findByName with name: " + name + "; dudeId: " + dudeId);
        List<Exercise> exercises;
        try {
            exercises = iExerciseService.findByName(name, dudeId);
        } catch (ServiceException e) {
            LOGGER.error("Could not findByName with name: " + name);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        ExerciseDto[] exerciseDtos = new ExerciseDto[exercises.size()];
        for (int i = 0; i < exercises.size(); i++) {
            exerciseDtos[i] = exerciseMapper.exerciseToExerciseDto(exercises.get(i));
        }
        return exerciseDtos;
    }

    @RequestMapping(value = "/all/{dudeId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get all exercises", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto[] findAll(@PathVariable Long dudeId) {
        LOGGER.info("Entering findAll with dudeId: " + dudeId);
        List<Exercise> exercises;
        try {
            exercises = iExerciseService.findAll(dudeId);
        } catch (ServiceException e) {
            LOGGER.error("Could not find all exercises");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        ExerciseDto[] exerciseDtos = new ExerciseDto[exercises.size()];
        for (int i = 0; i < exercises.size(); i++) {
            exerciseDtos[i] = exerciseMapper.exerciseToExerciseDto(exercises.get(i));
        }
        return exerciseDtos;
    }

    @RequestMapping(value = "/filtered/{dudeId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get Exercises by filters", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto[] findByFilter(@RequestParam(defaultValue = "") String filter, @RequestParam(required = false) MuscleGroup muscleGroup,
                                      @RequestParam(required = false) Category category, @PathVariable Long dudeId) {
        LOGGER.info("Entering findByFilter with filter: " + filter + "; muscleGroup: " + muscleGroup + "; category: " + category + "; dudeId: " + dudeId);
        List<Exercise> exercises;
        try {
            exercises = iExerciseService.findByFilter(filter, muscleGroup, category, dudeId);
        } catch (ServiceException e) {
            LOGGER.error("Could not findByFilter with filter: " + filter + "; muscleGroup: " + muscleGroup + "; category: " + category);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        ExerciseDto[] exerciseDtos = new ExerciseDto[exercises.size()];
        for (int i = 0; i < exercises.size(); i++) {
            exerciseDtos[i] = exerciseMapper.exerciseToExerciseDto(exercises.get(i));
        }
        return exerciseDtos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update an Exercise", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto updateExercise(@PathVariable("id") long id, @Valid @RequestBody ExerciseDto newExercise) {
        LOGGER.info("Updating exercise with id: " + id);
        try {
            return exerciseMapper.exerciseToExerciseDto(iExerciseService.update(id, exerciseMapper.exerciseDtoToExercise(newExercise)));
        } catch (ServiceException e){
            LOGGER.error("Could not update exercise with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete an Exercise", authorizations = {@Authorization(value = "apiKey")})
    public void deleteExercise(@PathVariable("id") long id) {
        LOGGER.info("Deleting exercise with id " + id);
        try {
            iExerciseService.delete(id);
        } catch (ServiceException e){
            LOGGER.error("Could not delete exercise with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/{id}/{version}/uploadImage")
    @ApiOperation(value = "Upload image for Exercise", authorizations = {@Authorization(value = "apiKey")})
    public String uploadImage(@PathVariable Long id, @PathVariable Integer version, @RequestParam("file") MultipartFile file) {
        LOGGER.info("Entering uploadImage with id: " + id + "; version: " + version);
        String fileName = "exercise_" + id + "_" + version + ".png";

        try {
            iFileStorageService.storeFile(fileName, file);
        } catch (ServiceException e) {
            LOGGER.error("Could not uploadImage with id: " + id + "; version: " + version);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        try {
            return iExerciseService.updateImagePath(id, version, fileName);
        } catch (ServiceException e) {
            LOGGER.error("Could not updateImagePath with id: " + id + "; version: " + version);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/bookmark/{dudeId}/{exerciseId}/{exerciseVersion}", method = RequestMethod.PUT)
    @ApiOperation(value = "Dude with given id bookmarks Exercise with given id and version", authorizations = {@Authorization(value = "apiKey")})
    public void saveExerciseBookmark(@PathVariable Long dudeId, @PathVariable Long exerciseId, @PathVariable Integer exerciseVersion) {
        LOGGER.info("Entering saveExerciseBookmark with dudeId: " + dudeId + "; exerciseId: " + exerciseId + "; exerciseVersion: " + exerciseVersion);
        try {
            iExerciseService.saveExerciseBookmark(dudeId, exerciseId, exerciseVersion);
        } catch (ServiceException e) {
            LOGGER.error("Could not saveExerciseBookmark with dudeId: " + dudeId + "; exerciseId: " + exerciseId + "; exerciseVersion: " + exerciseVersion);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/bookmark/{dudeId}/{exerciseId}/{exerciseVersion}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Dude with given id deletes bookmark for Exercise with given id and version", authorizations = {@Authorization(value = "apiKey")})
    public void deleteExerciseBookmark(@PathVariable Long dudeId, @PathVariable Long exerciseId, @PathVariable Integer exerciseVersion) {
        LOGGER.info("Entering deleteExerciseBookmark with dudeId: " + dudeId + "; exerciseId: " + exerciseId + "; exerciseVersion: " + exerciseVersion);
        try {
            iExerciseService.deleteExerciseBookmark(dudeId, exerciseId, exerciseVersion);
        } catch (ServiceException e) {
            LOGGER.error("Could not deleteExerciseBookmark with dudeId: " + dudeId + "; exerciseId: " + exerciseId + "; exerciseVersion: " + exerciseVersion);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
