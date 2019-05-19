package at.ac.tuwien.sepm.groupphase.backend.endpoint.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IDudeMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IExerciseMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final IDudeMapper dudeMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseEndpoint.class);

    @Autowired
    public ExerciseEndpoint(IExerciseService iExerciseService, IExerciseMapper exerciseMapper, IDudeMapper dudeMapper) {
        this.iExerciseService = iExerciseService;
        this.exerciseMapper = exerciseMapper;
        this.dudeMapper = dudeMapper;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get an Exercise by id", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto findById(@PathVariable Long id) {
        LOGGER.info("Entering findById with id: " + id);
        try {
            return exerciseMapper.exerciseToExerciseDto(iExerciseService.findById(id));
        } catch (ServiceException e) {
            LOGGER.error("Could not find exercise with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get Exercises by name", authorizations = {@Authorization(value = "apiKey")})
    public List<ExerciseDto> findByName(@RequestParam String name) {
        LOGGER.info("Entering findByName with name: " + name);
        List<Exercise> exercises;
        try {
            exercises = iExerciseService.findByName(name);
        } catch (ServiceException e) {
            LOGGER.error("Could not find exercises with name: " + name);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<ExerciseDto> exerciseDtos = new ArrayList<>();
        for (Exercise exercise : exercises) {
            exerciseDtos.add(exerciseMapper.exerciseToExerciseDto(exercise));
        }
        return exerciseDtos;
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

    @RequestMapping(value = "/own", method = RequestMethod.GET)
    @ApiOperation(value = "Get all exercises of dude")
    public List<ExerciseDto> findAllByCreator(@RequestParam DudeDto creator){
        LOGGER.info("Entering findAllByCreator");
        List<Exercise> ownExercises;
        try {
            ownExercises = iExerciseService.findAllByCreator(dudeMapper.dudeDtoToDude(creator));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<ExerciseDto> ownExerciseDtos = new ArrayList<>();
        for (Exercise exercise : ownExercises) {
            ownExerciseDtos.add(exerciseMapper.exerciseToExerciseDto(exercise));
        }
        return ownExerciseDtos;
    }

}
