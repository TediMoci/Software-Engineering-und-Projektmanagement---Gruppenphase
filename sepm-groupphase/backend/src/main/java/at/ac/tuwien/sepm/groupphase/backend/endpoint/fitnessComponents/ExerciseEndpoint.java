package at.ac.tuwien.sepm.groupphase.backend.endpoint.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/exercise")
@Api(value = "exercise")
public class ExerciseEndpoint {

    private final IExerciseService iExerciseService;
    private final IExerciseMapper exerciseMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseEndpoint.class);

    @Autowired
    public ExerciseEndpoint(IExerciseService iExerciseService, IExerciseMapper exerciseMapper) {
        this.iExerciseService = iExerciseService;
        this.exerciseMapper = exerciseMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Save a new Exercise", authorizations = {@Authorization(value = "apiKey")})
    public ExerciseDto save(@RequestBody ExerciseDto exerciseDto) {
        LOGGER.info("Entering save for: " + exerciseDto);
        Exercise exercise = exerciseMapper.exerciseDtoToExercise(exerciseDto);
        try {
            return exerciseMapper.exerciseToExerciseDto(iExerciseService.save(exercise));
        } catch (ServiceException e) {
            LOGGER.error("Could not save: " + exerciseDto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
