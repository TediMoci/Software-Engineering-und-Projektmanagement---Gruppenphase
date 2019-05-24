package at.ac.tuwien.sepm.groupphase.backend.endpoint.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDtoOut;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IWorkoutMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IWorkoutService;
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
@RequestMapping(value = "/workout")
@Api(value = "workout")
public class WorkoutEndpoint {

    private final IWorkoutService iWorkoutService;
    private final IWorkoutMapper workoutMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutEndpoint.class);

    public WorkoutEndpoint(IWorkoutService iWorkoutService, IWorkoutMapper workoutMapper) {
        this.iWorkoutService = iWorkoutService;
        this.workoutMapper = workoutMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Save a new Workout", authorizations = {@Authorization(value = "apiKey")})
    public WorkoutDto save(WorkoutDto workoutDto) {
        LOGGER.info("Entering save for: " + workoutDto);
        Workout workout = workoutMapper.workoutDtoToWorkout(workoutDto);
        try {
            return workoutMapper.workoutToWorkoutDto(iWorkoutService.save(workout));
        } catch (ServiceException e) {
            LOGGER.error("Could not save: " + workoutDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}/{version}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a Workout by id and version", authorizations = {@Authorization(value = "apiKey")})
    public WorkoutDto findByIdAndVersion(@PathVariable Long id, @PathVariable Integer version) {
        LOGGER.info("Entering findByIdAndVersion with id: " + id + "; and version: " + version);
        try {
            return workoutMapper.workoutToWorkoutDto(iWorkoutService.findByIdAndVersion(id, version));
        } catch (ServiceException e) {
            LOGGER.error("Could not find workout with id: " + id + "; and version: " + version);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get Workouts by name", authorizations = {@Authorization(value = "apiKey")})
    public List<WorkoutDto> findByName(@RequestParam String name) {
        LOGGER.info("Entering findByName with name: " + name);
        List<Workout> workouts;
        try {
            workouts = iWorkoutService.findByName(name);
        } catch (ServiceException e) {
            LOGGER.error("Could not find workouts with name: " + name);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<WorkoutDto> workoutDtos = new ArrayList<>();
        for (Workout workout : workouts) {
            workoutDtos.add(workoutMapper.workoutToWorkoutDto(workout));
        }
        return workoutDtos;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "Get all Workouts", authorizations = {@Authorization(value = "apiKey")})
    public List<WorkoutDto> findAll() {
        LOGGER.info("Entering findAll");
        List<Workout> workouts;
        try {
            workouts = iWorkoutService.findAll();
        } catch (ServiceException e) {
            LOGGER.error("Could not find all workouts");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<WorkoutDto> workoutDtos = new ArrayList<>();
        for (Workout workout : workouts) {
            workoutDtos.add(workoutMapper.workoutToWorkoutDto(workout));
        }
        return workoutDtos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update a Workout", authorizations = {@Authorization(value = "apiKey")})
    public WorkoutDto update(@PathVariable("id") long id, @RequestBody WorkoutDto newWorkout) {
        LOGGER.info("Updating workout with id: " + id);
        try {
            return workoutMapper.workoutToWorkoutDto(iWorkoutService.update(id, workoutMapper.workoutDtoToWorkout(newWorkout)));
        } catch (ServiceException e){
            LOGGER.error("Could not update workout with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a Workout", authorizations = {@Authorization(value = "apiKey")})
    public void delete(@PathVariable("id") long id) {
        LOGGER.info("Deleting workout with id " + id);
        try {
            iWorkoutService.delete(id);
        } catch (ServiceException e){
            LOGGER.error("Could not delete workout with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}/{version}/exercises", method = RequestMethod.GET)
    @ApiOperation(value = "Get exercises that are part of workout with given id and version", authorizations = {@Authorization(value = "apiKey")})
    public WorkoutExerciseDtoOut[] getAllExercisesByWorkoutIdAndVersion(@PathVariable Long id, @PathVariable Integer version) {
        LOGGER.info("Entering getAllExercisesByWorkoutIdAndVersion with id: " + id + "; and version: " + version);
        List<WorkoutExercise> workoutExercises;
        try {
            workoutExercises = iWorkoutService.findByIdAndVersion(id, version).getExercises();
        } catch (ServiceException e) {
            LOGGER.error("Could not getAllExercisesByWorkoutIdAndVersion with id: " + id + "; and version: " + version);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        WorkoutExerciseDtoOut[] workoutExerciseDtoOuts = new WorkoutExerciseDtoOut[workoutExercises.size()];
        for (int i = 0; i < workoutExercises.size(); i++) {
            workoutExerciseDtoOuts[i] = workoutMapper.workoutExerciseToWorkoutExerciseDtoOut(workoutExercises.get(i));
        }
        return workoutExerciseDtoOuts;
    }

}
