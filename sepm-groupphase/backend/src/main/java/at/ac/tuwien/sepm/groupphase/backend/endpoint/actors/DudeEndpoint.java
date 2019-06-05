package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IDudeMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IExerciseMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.ITrainingScheduleMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IWorkoutMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/dudes")
@Api(value = "dudes")
public class DudeEndpoint {

    private final IDudeService iDudeService;
    private final ITrainingScheduleService iTrainingScheduleService;
    private final IDudeMapper dudeMapper;
    private final ITrainingScheduleMapper trainingScheduleMapper;
    private final IExerciseMapper exerciseMapper;
    private final IWorkoutMapper workoutMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(DudeEndpoint.class);

    public DudeEndpoint(IDudeService iDudeService, ITrainingScheduleService iTrainingScheduleService, IDudeMapper dudeMapper, ITrainingScheduleMapper trainingScheduleMapper, IExerciseMapper exerciseMapper, IWorkoutMapper workoutMapper) {
        this.iDudeService = iDudeService;
        this.iTrainingScheduleService = iTrainingScheduleService;
        this.dudeMapper = dudeMapper;
        this.trainingScheduleMapper = trainingScheduleMapper;
        this.exerciseMapper = exerciseMapper;
        this.workoutMapper = workoutMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new Dude", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto saveDude(@Valid @RequestBody DudeDto dudeDto) {
        Dude dude = dudeMapper.dudeDtoToDude(dudeDto);
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.save(dude));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "Get all dudes", authorizations = {@Authorization(value = "apiKey")})
    public List<DudeDto> findAll() {
        List<DudeDto> dudeListDTO = new ArrayList<>();
        try {
            List<Dude> dudeList = iDudeService.findAll();
            for (int i=0; i< dudeList.size(); i++){
                dudeListDTO.add(dudeMapper.dudeToDudeDto(dudeList.get(i)));
            }
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return dudeListDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a Dude by id", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto findDudeById(@PathVariable("id") Long id) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.findDudeById(id));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get a Dude by name", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto findDudeByName(String name) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.findByName(name));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/bmi", method = RequestMethod.GET)
    @ApiOperation(value = "Get BMI of dude", authorizations = {@Authorization(value = "apiKey")})
    public Double getBmi(Double height, Double weight){
        return iDudeService.calculateBMI(height, weight);
    }

    @RequestMapping(value = "/age", method = RequestMethod.GET)
    @ApiOperation(value = "Get age of dude", authorizations = {@Authorization(value = "apiKey")})
    public Integer getAge(@RequestParam("birthday")@DateTimeFormat(pattern = "\"yyyy-MM-dd\"") LocalDate birthday){
        return iDudeService.calculateAge(birthday);
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update a Dude", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto updateDude(@PathVariable("name") String name, @RequestBody DudeDto dude) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.update(name, dudeMapper.dudeDtoToDude(dude)));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}/exercises", method = RequestMethod.GET)
    @ApiOperation(value = "Get exercises created by dude", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto[] getExercisesCreatedByDudeId(@PathVariable Long id) {
        LOGGER.info("Entering getExercisesCreatedByDudeId with id: " + id);
        List<Exercise> allExercises;

        try {
            allExercises = iDudeService.findDudeById(id).getExercises();
        } catch (ServiceException e) {
            LOGGER.error("Could not getExercisesCreatedByDudeId with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<Exercise> exercises = new ArrayList<>();
        for (Exercise exercise : allExercises) {
            if (!exercise.getHistory()) {
                exercises.add(exercise);
            }
        }
        ExerciseDto[] exerciseDtos = new ExerciseDto[exercises.size()];
        for (int i = 0; i < exercises.size(); i++) {
            if (!exercises.get(i).getHistory()) {
                exerciseDtos[i] = exerciseMapper.exerciseToExerciseDto(exercises.get(i));
            }
        }
        return exerciseDtos;
    }

    @RequestMapping(value = "/{id}/workouts", method = RequestMethod.GET)
    @ApiOperation(value = "Get workouts created by dude", authorizations = {@Authorization(value = "apiKey")})
    public WorkoutDto[] getWorkoutsCreatedByDudeId(@PathVariable Long id) {
        LOGGER.info("Entering getWorkoutsCreatedByDudeId with id: " + id);
        List<Workout> allWorkouts;
        try {
            allWorkouts = iDudeService.findDudeById(id).getWorkouts();
        } catch (ServiceException e) {
            LOGGER.error("Could not getWorkoutsCreatedByDudeId with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<Workout> workouts = new ArrayList<>();
        for (Workout workout : allWorkouts) {
            if (!workout.getHistory()) {
                workouts.add(workout);
            }
        }
        WorkoutDto[] workoutDtos = new WorkoutDto[workouts.size()];
        for (int i = 0; i < workouts.size(); i++) {
            if (!workouts.get(i).getHistory()) {
                workoutDtos[i] = workoutMapper.workoutToWorkoutDto(workouts.get(i));
            }
        }
        return workoutDtos;
    }

    @RequestMapping(value = "/{id}/trainingSchedules", method = RequestMethod.GET)
    @ApiOperation(value = "Get training schedules created by dude", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleDto[] getTrainingSchedulesCreatedByDudeId(@PathVariable Long id) {
        LOGGER.info("Entering getTrainingSchedulesCreatedByDudeId with id: " + id);
        List<TrainingSchedule> allTrainingSchedules;
        try {
            allTrainingSchedules = iDudeService.findDudeById(id).getTrainingSchedules();
        } catch (ServiceException e) {
            LOGGER.error("Could not getTrainingSchedulesCreatedByDudeId with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<TrainingSchedule> trainingSchedules = new ArrayList<>();
        for (TrainingSchedule trainingSchedule : allTrainingSchedules) {
            if (!trainingSchedule.getHistory()) {
                trainingSchedules.add(trainingSchedule);
            }
        }
        TrainingScheduleDto[] trainingScheduleDtos = new TrainingScheduleDto[trainingSchedules.size()];
        for (int i = 0; i < trainingSchedules.size(); i++) {
            if (!trainingSchedules.get(i).getHistory()) {
                trainingScheduleDtos[i] = trainingScheduleMapper.trainingScheduleToTrainingScheduleDto(trainingSchedules.get(i));
            }
        }
        return trainingScheduleDtos;
    }

    @RequestMapping(value = "/{id}/activeTrainingSchedule", method = RequestMethod.GET)
    @ApiOperation(value = "Get active training schedule of dude", authorizations = {@Authorization(value = "apiKey")})
    public ActiveTrainingScheduleDto getActiveTrainingScheduleByDudeId(@PathVariable Long id) {
        LOGGER.info("Entering getActiveTrainingScheduleByDudeId with id: " + id);
        ActiveTrainingSchedule activeTrainingSchedule;
        try {
            activeTrainingSchedule = iDudeService.findDudeById(id).getActiveTrainingSchedule();
        } catch (ServiceException e) {
            LOGGER.error("Could not getActiveTrainingScheduleByDudeId with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        if (activeTrainingSchedule == null) {
            LOGGER.debug("Dude with id: " + id + " does currently not have an ActiveTrainingSchedule");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You currently do not have an active training schedule.");
        }
        int totalDays = activeTrainingSchedule.getIntervalRepetitions() * activeTrainingSchedule.getTrainingSchedule().getIntervalLength();
        LocalDate tempDate = LocalDate.from(activeTrainingSchedule.getStartDate());
        if (tempDate.until(LocalDate.now(), ChronoUnit.DAYS) > totalDays) {
            LOGGER.debug("ActiveTrainingSchedule of Dude with id: " + id + " has expired");
            try {
                iTrainingScheduleService.deleteActive(id);
            } catch (ServiceException e) {
                LOGGER.error("Could not deleteActive for Dude with id: " + id);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Your active training schedule has expired.");
        }
        return trainingScheduleMapper.activeTrainingScheduleToActiveTrainingScheduleDto(activeTrainingSchedule);
    }

    @RequestMapping(value = "/{id}/activeTrainingSchedule/done", method = RequestMethod.GET)
    @ApiOperation(value = "Get ExerciseDone-array of ActiveTrainingSchedule of Dude", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDoneDto[] getExercisesDoneByDudeId(@PathVariable Long id) {
        LOGGER.info("Entering getExercisesDoneByDudeId with id: " + id);
        ActiveTrainingSchedule activeTrainingSchedule;
        try {
            activeTrainingSchedule = iDudeService.findDudeById(id).getActiveTrainingSchedule();
        } catch (ServiceException e) {
            LOGGER.error("Could not getExercisesDoneByDudeId with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        List<ExerciseDone> exerciseDones = activeTrainingSchedule.getDone();
        ExerciseDoneDto[] exerciseDoneDtos = new ExerciseDoneDto[exerciseDones.size()];
        for (int i = 0; i < exerciseDones.size(); i++) {
            exerciseDoneDtos[i] = trainingScheduleMapper.exerciseDoneToExerciseDoneDto(exerciseDones.get(i));
        }
        return exerciseDoneDtos;
    }
}

