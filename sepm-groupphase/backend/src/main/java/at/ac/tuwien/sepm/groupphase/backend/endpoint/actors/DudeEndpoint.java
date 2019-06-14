package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IDudeMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IFitnessProviderMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IExerciseMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IStatsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.ITrainingScheduleMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.IWorkoutMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ActiveTrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.FinishedTrainingScheduleStats;
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
import java.time.Period;
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
    private final IFitnessProviderMapper fitnessProviderMapper;
    private final ITrainingScheduleMapper trainingScheduleMapper;
    private final IExerciseMapper exerciseMapper;
    private final IWorkoutMapper workoutMapper;
    private final IStatsMapper statsMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(DudeEndpoint.class);

    public DudeEndpoint(IDudeService iDudeService, ITrainingScheduleService iTrainingScheduleService, IDudeMapper dudeMapper, IFitnessProviderMapper fitnessProviderMapper, ITrainingScheduleMapper trainingScheduleMapper, IExerciseMapper exerciseMapper, IWorkoutMapper workoutMapper, IStatsMapper statsMapper) {
        this.iDudeService = iDudeService;
        this.iTrainingScheduleService = iTrainingScheduleService;
        this.dudeMapper = dudeMapper;
        this.fitnessProviderMapper = fitnessProviderMapper;
        this.trainingScheduleMapper = trainingScheduleMapper;
        this.exerciseMapper = exerciseMapper;
        this.workoutMapper = workoutMapper;
        this.statsMapper = statsMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new Dude", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto saveDude(@Valid @RequestBody DudeDto dudeDto) {
        Dude dude = dudeMapper.dudeDtoToDude(dudeDto);
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.save(dude));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "Get all dudes", authorizations = {@Authorization(value = "apiKey")})
    public List<DudeDto> findAll() {
        List<DudeDto> dudeListDTO = new ArrayList<>();
        try {
            List<Dude> dudeList = iDudeService.findAll();
            for (int i = 0; i < dudeList.size(); i++) {
                dudeListDTO.add(dudeMapper.dudeToDudeDto(dudeList.get(i)));
            }
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return dudeListDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a Dude by id", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto findDudeById(@PathVariable("id") Long id) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.findDudeById(id));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get a Dude by name", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto findDudeByName(String name) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.findByName(name));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/bmi", method = RequestMethod.GET)
    @ApiOperation(value = "Get BMI of dude", authorizations = {@Authorization(value = "apiKey")})
    public Double getBmi(Double height, Double weight) {
        return iDudeService.calculateBMI(height, weight);
    }

    @RequestMapping(value = "/age", method = RequestMethod.GET)
    @ApiOperation(value = "Get age of dude", authorizations = {@Authorization(value = "apiKey")})
    public Integer getAge(@RequestParam("birthday") @DateTimeFormat(pattern = "\"yyyy-MM-dd\"") LocalDate birthday) {
        return iDudeService.calculateAge(birthday);
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update a Dude", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto updateDude(@PathVariable("name") String name, @RequestBody DudeDto dude) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.update(name, dudeMapper.dudeDtoToDude(dude)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{dudeId}/follow/{fitnessProviderId}", method = RequestMethod.PUT)
    @ApiOperation(value = "Follow fitness provider with given id", authorizations = {@Authorization(value = "apiKey")})
    public void followFitnessProvider(@PathVariable Long dudeId, @PathVariable Long fitnessProviderId) {
        LOGGER.info("Entering followFitnessProvider with dudeId: " + dudeId + "; fitnessProviderId: " + fitnessProviderId);
        try {
            iDudeService.followFitnessProvider(dudeId, fitnessProviderId);
        } catch (ServiceException e) {
            LOGGER.error("Could not followFitnessProvider with dudeId: " + dudeId + "; fitnessProviderId: " + fitnessProviderId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{dudeId}/follow/{fitnessProviderId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Unfollow fitness provider with given id", authorizations = {@Authorization(value = "apiKey")})
    public void unfollowFitnessProvider(@PathVariable Long dudeId, @PathVariable Long fitnessProviderId) {
        LOGGER.info("Entering unfollowFitnessProvider with dudeId: " + dudeId + "; fitnessProviderId: " + fitnessProviderId);
        try {
            iDudeService.unfollowFitnessProvider(dudeId, fitnessProviderId);
        } catch (ServiceException e) {
            LOGGER.error("Could not unfollowFitnessProvider with dudeId: " + dudeId + "; fitnessProviderId: " + fitnessProviderId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{dudeId}/follow", method = RequestMethod.GET)
    @ApiOperation(value = "Get all followed fitness providers of dude", authorizations = {@Authorization(value = "apiKey")})
    public FitnessProviderDto[] getFollowedFitnessProviders(@PathVariable Long dudeId) {
        LOGGER.info("Entering getFollowedFitnessProviders with dudeId: " + dudeId);
        List<FitnessProvider> fitnessProviders;
        try {
            fitnessProviders = iDudeService.findDudeById(dudeId).getFitnessProviders();
        } catch (ServiceException e) {
            LOGGER.error("Could not getFollowedFitnessProviders with dudeId: " + dudeId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        FitnessProviderDto[] fitnessProviderDtos = new FitnessProviderDto[fitnessProviders.size()];
        for (int i = 0; i < fitnessProviders.size(); i++) {
            fitnessProviderDtos[i] = fitnessProviderMapper.fitnessProviderToFitnessProviderDto(fitnessProviders.get(i));
            fitnessProviderDtos[i].setPassword("Not available");
        }
        return fitnessProviderDtos;
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

    @RequestMapping(value = "/filtered", method = RequestMethod.GET)
    @ApiOperation(value = "Get Workouts by filters", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto[] findByFilter(@RequestParam(defaultValue = "") String filter, @RequestParam(required = false) Integer selfAssessment) {
        LOGGER.info("Entering findByFilter with filter: " + filter + "; selfAssessment: " + selfAssessment);
        List<Dude> dudes;
        try {
            dudes = iDudeService.findByFilter(filter, selfAssessment);
        } catch (ServiceException e) {
            LOGGER.error("Could not findByFilter with filter: " + filter + "; and selfAssessment: " + selfAssessment);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        DudeDto[] dudeDtos = new DudeDto[dudes.size()];
        for (int i = 0; i < dudes.size(); i++) {
            dudeDtos[i] = dudeMapper.dudeToDudeDto(dudes.get(i));
        }
        return dudeDtos;
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
        if (tempDate.until(LocalDate.now(), ChronoUnit.DAYS) >= totalDays) {
            LOGGER.debug("ActiveTrainingSchedule of Dude with id: " + id + " has expired");
            try {
                iTrainingScheduleService.deleteActive(id);
            } catch (ServiceException e) {
                LOGGER.error("Could not deleteActive for Dude with id: " + id);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Your active training schedule has expired.");
        }
        if (activeTrainingSchedule.getAdaptive()
            && ((tempDate.until(LocalDate.now(), ChronoUnit.DAYS) / activeTrainingSchedule.getTrainingSchedule().getIntervalLength()) > 0)
            && !activeTrainingSchedule.getHasBeenAdapted().get((int)(tempDate.until(LocalDate.now(), ChronoUnit.DAYS) / activeTrainingSchedule.getTrainingSchedule().getIntervalLength())-1)) {

            Dude dude;
            try {
                dude = iDudeService.findDudeById(id);
            } catch (ServiceException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find dude with id " + id);
            }
            try {
                return trainingScheduleMapper.activeTrainingScheduleToActiveTrainingScheduleDto(iTrainingScheduleService.calculatePercentageOfChangeForInterval(activeTrainingSchedule, dude));
            } catch (ServiceException e) {
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Could not adapt activeTrainingSchedule with id " + activeTrainingSchedule.getId());
            }
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

    @RequestMapping(value = "/{id}/statistics", method = RequestMethod.GET)
    @ApiOperation(value = "Get all statistics of formerly active training schedules of Dude", authorizations = {@Authorization(value = "apiKey")})
    public FinishedTrainingScheduleStatsDto[] getStatisticsByDudeId(@PathVariable Long id) {
        LOGGER.info("Entering getStatisticsByDudeId with id " + id);
        List<FinishedTrainingScheduleStats> stats;
        try {
            stats = iDudeService.findDudeById(id).getFinishedTrainingScheduleStats();
        } catch (ServiceException e){
            LOGGER.error("Could not getStatisticsByDudeId with id: " + id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        FinishedTrainingScheduleStatsDto[] statsDtos = new FinishedTrainingScheduleStatsDto[stats.size()];
        for (int i = 0; i < stats.size(); i++) {
            statsDtos[i] = statsMapper.finishedTrainingScheduleStatsToFinishedTrainingScheduleStatsDto(stats.get(i));
            LOGGER.debug("STATS INFO: " + statsDtos[i].toString() + ", " + statsDtos[i].getTrainingScheduleDto().toString());
        }
        return statsDtos;
    }
}

