package at.ac.tuwien.sepm.groupphase.backend.endpoint.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.TrainingScheduleDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.fitnessComponents.ITrainingScheduleMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/trainingSchedule")
@Api(value = "trainingSchedule")
public class TrainingScheduleEndpoint {

    private final ITrainingScheduleService iTrainingScheduleService;
    private final ITrainingScheduleMapper trainingScheduleMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingScheduleEndpoint.class);

    public TrainingScheduleEndpoint(ITrainingScheduleService iTrainingScheduleService, ITrainingScheduleMapper trainingScheduleMapper) {
        this.iTrainingScheduleService = iTrainingScheduleService;
        this.trainingScheduleMapper = trainingScheduleMapper;
    }

    @RequestMapping(value = "/{days}/{minTarget}/{maxTarget}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new random Training Schedule", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleDto saveRandom(
        @PathVariable("days") int days, @PathVariable("minTarget") double minTarget,
        @PathVariable("maxTarget") double maxTarget, @Valid @RequestBody TrainingScheduleDto trainingScheduleDto) {
        LOGGER.info("Entering save for: " + trainingScheduleDto);
        TrainingSchedule trainingSchedule = trainingScheduleMapper.trainingScheduleDtoToTrainingSchedule(trainingScheduleDto);
        try {
            return trainingScheduleMapper.trainingScheduleToTrainingScheduleDto(iTrainingScheduleService.saveRandom(days,minTarget,maxTarget,trainingSchedule));
        } catch (ServiceException e) {
            LOGGER.error("Could not save: " + trainingScheduleDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new Workout", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleDto save(@Valid @RequestBody TrainingScheduleDto trainingScheduleDto) {
        LOGGER.info("Entering save for: " + trainingScheduleDto);
        TrainingSchedule trainingSchedule = trainingScheduleMapper.trainingScheduleDtoToTrainingSchedule(trainingScheduleDto);
        try {
            return trainingScheduleMapper.trainingScheduleToTrainingScheduleDto(iTrainingScheduleService.save(trainingSchedule));
        } catch (ServiceException e) {
            LOGGER.error("Could not save: " + trainingScheduleDto);
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
}
