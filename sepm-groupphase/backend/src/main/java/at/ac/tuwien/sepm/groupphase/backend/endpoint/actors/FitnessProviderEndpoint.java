package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.FitnessProviderMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/fitnessProvider")
@Api(value = "fitnessProvider")
public class FitnessProviderEndpoint {

    private final IFitnessProviderService iFitnessProviderService;
    private final FitnessProviderMapper fitnessProviderMapper;

    @Autowired
    public FitnessProviderEndpoint(IFitnessProviderService iFitnessProviderService, FitnessProviderMapper fitnessProviderMapper){
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during saving fitness provider: " + e.getMessage(), e);
        }
    }
}
