package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IFitnessProviderMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/fitnessProvider")
@Api(value = "fitnessProvider")
public class FitnessProviderEndpoint {

    private final IFitnessProviderService iFitnessProviderService;
    private final IFitnessProviderMapper fitnessProviderMapper;

    @Autowired
    public FitnessProviderEndpoint(IFitnessProviderService iFitnessProviderService, IFitnessProviderMapper fitnessProviderMapper){
        this.iFitnessProviderService = iFitnessProviderService;
        this.fitnessProviderMapper = fitnessProviderMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Save new Fitness Provider", authorizations = {@Authorization(value = "apiKey")})
    public FitnessProviderDto saveFitnessProvider(@RequestBody FitnessProviderDto fitnessProviderDto){
        FitnessProvider fitnessProvider = fitnessProviderMapper.fitnessProviderDtoToFitnessProvider(fitnessProviderDto);
        try{
            fitnessProvider = iFitnessProviderService.save(fitnessProvider);
            return fitnessProviderMapper.fitnessProviderToFitnessProviderDto(fitnessProvider);
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get a fitness provider by name and password", authorizations ={ @Authorization(value = "apiKey")})
    public FitnessProviderDto findByNameAndPassword(String name, String password){
        try{
            return fitnessProviderMapper.fitnessProviderToFitnessProviderDto(iFitnessProviderService.findByNameAndPassword(name, password));
        }catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{name}/followers", method = RequestMethod.GET)
    @ApiOperation(value = "Get the number of followers of the fitness provider with the given name", authorizations ={ @Authorization(value = "apiKey")})
    public Integer getNumberOfFollowers(@PathVariable String name) {
        try {
            return iFitnessProviderService.getNumberOfFollowers(name);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
