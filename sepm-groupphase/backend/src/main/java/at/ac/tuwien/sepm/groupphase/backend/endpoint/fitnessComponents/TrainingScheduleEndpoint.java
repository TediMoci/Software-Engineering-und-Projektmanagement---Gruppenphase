package at.ac.tuwien.sepm.groupphase.backend.endpoint.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.ITrainingScheduleMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IWorkoutMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
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
@RequestMapping(value = "/trainingSchedule")
@Api(value = "trainingSchedule")
public class TrainingScheduleEndpoint {

    private final ITrainingScheduleService iTrainingScheduleService;
    private final ITrainingScheduleMapper trainingScheduleMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingScheduleEndpoint.class);
    private final IWorkoutService iWorkoutService;
    private final IWorkoutMapper workoutMapper;

    public TrainingScheduleEndpoint(ITrainingScheduleService iTrainingScheduleService, ITrainingScheduleMapper trainingScheduleMapper, IWorkoutService iWorkoutService, IWorkoutMapper workoutMapper) {
        this.iTrainingScheduleService = iTrainingScheduleService;
        this.trainingScheduleMapper = trainingScheduleMapper;
        this.iWorkoutService = iWorkoutService;
        this.workoutMapper = workoutMapper;
    }

    @RequestMapping(value = "/random", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new random Training Schedule", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleDto saveRandom(@Valid @RequestBody TrainingScheduleRandomDto trainingScheduleRandomDto) {
        LOGGER.info("Entering save for: " + trainingScheduleRandomDto);
        TrainingSchedule trainingSchedule = trainingScheduleMapper.trainingScheduleRandomDtoToTrainingSchedule(trainingScheduleRandomDto);
        try {
            return trainingScheduleMapper.trainingScheduleToTrainingScheduleDto(iTrainingScheduleService.saveRandom(
                trainingScheduleRandomDto.getIntervalLength(),
                trainingScheduleRandomDto.getDuration(),
                trainingScheduleRandomDto.getMinTarget(),
                trainingScheduleRandomDto.getMaxTarget(),
                trainingSchedule,
                trainingScheduleRandomDto.isLowerDifficulty()));
        } catch (ServiceException e) {
            LOGGER.error("Could not save: " + trainingScheduleRandomDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new TrainingSchedule", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleDto save(@RequestBody TrainingScheduleDto trainingScheduleDto) {
        LOGGER.info("Entering save for: " + trainingScheduleDto);
        TrainingSchedule trainingSchedule = trainingScheduleMapper.trainingScheduleDtoToTrainingSchedule(trainingScheduleDto);
        try {
            return trainingScheduleMapper.trainingScheduleToTrainingScheduleDto(iTrainingScheduleService.save(trainingSchedule));
        } catch (ServiceException e) {
            LOGGER.error("Could not save: " + trainingScheduleDto + "because " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/active", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new ActiveTrainingSchedule", authorizations = {@Authorization(value = "apiKey")})
    public ActiveTrainingScheduleDto saveActive(@Valid @RequestBody ActiveTrainingScheduleDto activeTrainingScheduleDto) {
        LOGGER.info("Entering saveActive for: " + activeTrainingScheduleDto);
        ActiveTrainingSchedule activeTrainingSchedule = trainingScheduleMapper.activeTrainingScheduleDtoToActiveTrainingSchedule(activeTrainingScheduleDto);
        try {
            return trainingScheduleMapper.activeTrainingScheduleToActiveTrainingScheduleDto(iTrainingScheduleService.saveActive(activeTrainingSchedule));
        } catch (ServiceException e) {
            LOGGER.error("Could not saveActive for: " + activeTrainingScheduleDto);
            if (e.getMessage().equals("You already have an active training schedule!")) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        }
    }

    @RequestMapping(value = "/active/{dudeId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete the current ActiveTrainingSchedule of Dude with given id", authorizations = {@Authorization(value = "apiKey")})
    public void deleteActive(@PathVariable Long dudeId) {
        LOGGER.info("Entering deleteActive with dudeId: " + dudeId);
        try {
            iTrainingScheduleService.deleteActive(dudeId);
        } catch (ServiceException e) {
            LOGGER.error("Could not deleteActive with dudeId: " + dudeId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/active/done", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Mark Exercises in ActiveTrainingSchedule as done", authorizations = {@Authorization(value = "apiKey")})
    public void markExercisesAsDone(@RequestBody ExerciseDoneDto[] exerciseDoneDtos) {
        LOGGER.info("Entering markExercisesAsDone for: " + exerciseDoneDtos);
        ExerciseDone[] exerciseDones = new ExerciseDone[exerciseDoneDtos.length];
        for (int i = 0; i < exerciseDoneDtos.length; i++) {
            exerciseDones[i] = trainingScheduleMapper.exerciseDoneDtoToExerciseDone(exerciseDoneDtos[i]);
        }
        try {
            iTrainingScheduleService.markExercisesAsDone(exerciseDones);
        } catch (ServiceException e) {
            LOGGER.error("Could not markExercisesAsDone for: " + exerciseDoneDtos);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a Training Schedule", authorizations = {@Authorization(value = "apiKey")})
    public void delete(@PathVariable("id") long id) {
        LOGGER.info("Deleting Training Schedule with id " + id);
        try {
            iTrainingScheduleService.delete(id);
        } catch (ServiceException e){
            LOGGER.error("Could not delete Training Schedule with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update a Training Schedule", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleDto update(@PathVariable("id") long id, @RequestBody TrainingScheduleDto newTrainingSchedule) {
        LOGGER.info("Updating workout with id: " + id);
        try {
            return trainingScheduleMapper.trainingScheduleToTrainingScheduleDto(iTrainingScheduleService.update(id, trainingScheduleMapper.trainingScheduleDtoToTrainingSchedule(newTrainingSchedule)));
        } catch (ServiceException e){
            LOGGER.error("Could not update Training Schedule with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get training schedules by name", authorizations = {@Authorization(value = "apiKey")})
    public List<TrainingScheduleDto> findByName(@RequestParam String name) {
        LOGGER.info("Entering findByName with name: " + name);
        List<TrainingSchedule> trainingSchedules;
        try {
            trainingSchedules = iTrainingScheduleService.findByName(name);
        } catch (ServiceException e) {
            LOGGER.error("Could not find training schedules with name: " + name);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<TrainingScheduleDto> trainingScheduleDtos = new ArrayList<>();
        for (TrainingSchedule trainingSchedule : trainingSchedules) {
            trainingScheduleDtos.add(trainingScheduleMapper.trainingScheduleToTrainingScheduleDto(trainingSchedule));
        }
        return trainingScheduleDtos;
    }

    @RequestMapping(value = "/{id}/{version}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a Training Schedule by id and version", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleDto findByIdAndVersion(@PathVariable Long id, @PathVariable Integer version) {
        LOGGER.info("Entering findByIdAndVersion with id: " + id + "; and version: " + version);
        try {
            return trainingScheduleMapper.trainingScheduleToTrainingScheduleDto(iTrainingScheduleService.findByIdAndVersion(id, version));
        } catch (ServiceException e) {
            LOGGER.error("Could not find Training Schedule with id: " + id + "; and version: " + version);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


    @RequestMapping(value = "/{id}/{version}/workouts", method = RequestMethod.GET)
    @ApiOperation(value = "Get workouts that are part of this trainings schedule with given id and version", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleWorkoutDtoOut[] getAllWorkoutsByTrainingScheduleIdAndVersion(@PathVariable Long id, @PathVariable Integer version) {
        LOGGER.info("Entering getAllWorkoutsByTrainingScheduleIdAndVersion with id: " + id + "; and version: " + version);
        List<TrainingScheduleWorkout> trainingScheduleWorkouts;
        try {
            trainingScheduleWorkouts = iTrainingScheduleService.findById(id).getWorkouts();
        } catch (ServiceException e) {
            LOGGER.error("Could not getAllWorkoutsByTrainingScheduleIdAndVersion with id: " + id + "; and version: " + version);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        TrainingScheduleWorkoutDtoOut[] trainingScheduleWorkoutDtoOuts = new TrainingScheduleWorkoutDtoOut[trainingScheduleWorkouts.size()];
        for (int i = 0; i < trainingScheduleWorkouts.size(); i++) {
            trainingScheduleWorkoutDtoOuts[i] = trainingScheduleMapper.trainingScheduleWorkoutToTrainingScheduleWorkoutDtoOut(trainingScheduleWorkouts.get(i));
        }
        return trainingScheduleWorkoutDtoOuts;
    }

    @RequestMapping(value = "/filtered", method = RequestMethod.GET)
    @ApiOperation(value = "Get Training Schedule by filters", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleDto[] findByFilter(@RequestParam(defaultValue = "") String filter, @RequestParam(required = false) Integer selfAssessment) {
        LOGGER.info("Entering findByFilter with filter: " + filter + "; selfAssessment: " + selfAssessment);
        //todo: change Request Params
        List<TrainingSchedule> trainingSchedules;
        try {
            trainingSchedules = iTrainingScheduleService.findByFilter(filter, selfAssessment);
        } catch (ServiceException e) {
            LOGGER.error("Could not findByFilter with filter: " + filter + "; and selfAssessment: " + selfAssessment);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        TrainingScheduleDto[] trainingScheduleDtos = new TrainingScheduleDto[trainingSchedules.size()];
        for (int i = 0; i < trainingSchedules.size(); i++) {
            trainingScheduleDtos[i] = trainingScheduleMapper.trainingScheduleToTrainingScheduleDto(trainingSchedules.get(i));
        }
        return trainingScheduleDtos;
    }

    @RequestMapping(value = "/{id}/{version}/workouts/{day}", method = RequestMethod.GET)
    @ApiOperation(value = "Get workouts that are part of this trainings schedule with given id and version", authorizations = {@Authorization(value = "apiKey")})
    public List<WorkoutDto> getAllWorkoutsByTrainingScheduleIdAndVersionAndDay(@PathVariable Long id, @PathVariable Integer version, @PathVariable Integer day) {
        LOGGER.info("Entering getAllWorkoutsByTrainingScheduleIdAndVersionAndDay with id: " + id + "; and version: " + version);
        List<TrainingScheduleWorkout> trainingScheduleWorkouts;
        try {
            trainingScheduleWorkouts = iTrainingScheduleService.findById(id).getWorkouts();
            List<WorkoutDto> workoutDtos = new ArrayList<>();
            for (int i = 0; i < trainingScheduleWorkouts.size(); i++) {
                if (trainingScheduleWorkouts.get(i).getDay().equals(day)){
                    Workout w = iWorkoutService.findByIdAndVersion(trainingScheduleWorkouts.get(i).getWorkoutId(), trainingScheduleWorkouts.get(i).getWorkoutVersion());
                    workoutDtos.add(workoutMapper.workoutToWorkoutDto(w));
                }
            }
            return workoutDtos;
        } catch (ServiceException e) {
            LOGGER.error("Could not getAllWorkoutsByTrainingScheduleIdAndVersionAndDay with id: " + id + "; and version: " + version);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/rating/{dudeId}/{trainingScheduleId}/{rating}", method = RequestMethod.POST)
    @ApiOperation(value = "Dude with id rates TrainingSchedule with id", authorizations = {@Authorization(value = "apiKey")})
    public void saveTrainingScheduleRating(@PathVariable Long dudeId, @PathVariable Long trainingScheduleId, @PathVariable Integer rating) {
        LOGGER.info("Entering saveTrainingScheduleRating with dudeId: " + dudeId + "; trainingSchedule: " + trainingScheduleId + "; rating: " + rating);
        try {
            iTrainingScheduleService.saveTrainingScheduleRating(dudeId, trainingScheduleId, rating);
        } catch (ServiceException e) {
            LOGGER.error("Could not saveTrainingScheduleRating with dudeId: " + dudeId + "; trainingSchedule: " + trainingScheduleId + "; rating: " + rating);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/rating/{dudeId}/{trainingScheduleId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Dude with id deleted rating from trainingSchedule with id", authorizations = {@Authorization(value = "apiKey")})
    public void deleteTrainingScheduleRating(@PathVariable Long dudeId, @PathVariable Long trainingScheduleId) {
        LOGGER.info("Entering deleteTrainingScheduleRating with dudeId: " + dudeId + "; trainingSchedule: " + trainingScheduleId);
        try {
            iTrainingScheduleService.deleteTrainingScheduleRating(dudeId, trainingScheduleId);
        } catch (ServiceException e) {
            LOGGER.error("Could not deleteTrainingScheduleRating with dudeId: " + dudeId + "; trainingSchedule: " + trainingScheduleId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
