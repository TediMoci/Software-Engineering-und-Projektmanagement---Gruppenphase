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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/trainingSchedule")
@Api(value = "trainingSchedule")
public class TrainingScheduleEndpoint {

    private final ITrainingScheduleService iTrainingScheduleService;
    private final ITrainingScheduleMapper iTrainingScheduleMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingScheduleEndpoint.class);

    public TrainingScheduleEndpoint(ITrainingScheduleService iTrainingScheduleService, ITrainingScheduleMapper iTrainingScheduleMapper) {
        this.iTrainingScheduleService = iTrainingScheduleService;
        this.iTrainingScheduleMapper = iTrainingScheduleMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a new Workout", authorizations = {@Authorization(value = "apiKey")})
    public TrainingScheduleDto save(@Valid @RequestBody TrainingScheduleDto trainingScheduleDto) {
        LOGGER.info("Entering save for: " + trainingScheduleDto);
        TrainingSchedule trainingSchedule = iTrainingScheduleMapper.trainingScheduleDtoToTrainingSchedule(trainingScheduleDto);
        try {
            return iTrainingScheduleMapper.trainingScheduleToTrainingScheduleDto(iTrainingScheduleService.save(trainingSchedule));
        } catch (ServiceException e) {
            LOGGER.error("Could not save: " + trainingScheduleDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
