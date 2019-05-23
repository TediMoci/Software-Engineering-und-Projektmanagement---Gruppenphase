package at.ac.tuwien.sepm.groupphase.backend.endpoint.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.parameterObjects.ExercisePo;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IExerciseMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
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
@RequestMapping(value = "/exercise")
@Api(value = "exercise")
public class ExerciseEndpoint {

    private final IExerciseService iExerciseService;
    private final IExerciseMapper exerciseMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseEndpoint.class);

    public ExerciseEndpoint(IExerciseService iExerciseService, IExerciseMapper exerciseMapper) {
        this.iExerciseService = iExerciseService;
        this.exerciseMapper = exerciseMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "Get all exercises", authorizations = {@Authorization(value = "apiKey")})
    public List<ExerciseDto> findAll() {
        LOGGER.info("Entering findAll");
        List<Exercise> exercises;
        try {
            exercises = iExerciseService.findAll();
        } catch (ServiceException e) {
            LOGGER.error("Could not find all exercises");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<ExerciseDto> exerciseDtos = new ArrayList<>();
        for (Exercise exercise : exercises) {
            exerciseDtos.add(exerciseMapper.exerciseToExerciseDto(exercise));
        }
        return exerciseDtos;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get Exercises by filters", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto[] findByFilters(ExercisePo exercisePo) {
        LOGGER.info("Entering findByFilters with: " + exercisePo);
        List<Exercise> exercises;
        try {
            exercises = iExerciseService.findByFilters(exercisePo);
        } catch (ServiceException e) {
            LOGGER.error("Could not findByFilters with: " + exercisePo);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        ExerciseDto[] exerciseDtos = new ExerciseDto[exercises.size()];
        for (int i = 0; i < exercises.size(); i++) {
            exerciseDtos[i] = exerciseMapper.exerciseToExerciseDto(exercises.get(i));
        }
        return exerciseDtos;
    }

}
