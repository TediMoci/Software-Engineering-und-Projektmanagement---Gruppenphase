package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IDudeMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IFitnessProviderMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/user")
@Api(value = "user")
public class UserEndpoint {

    private final IUserService iUserService;
    private final IDudeMapper dudeMapper;
    private final IFitnessProviderMapper fitnessProviderMapper;

    public UserEndpoint(IUserService iUserService, IDudeMapper dudeMapper, IFitnessProviderMapper fitnessProviderMapper) {
        this.iUserService = iUserService;
        this.dudeMapper = dudeMapper;
        this.fitnessProviderMapper = fitnessProviderMapper;
    }


    @RequestMapping(value = "/name", method = RequestMethod.GET)
    @ApiOperation(value = "Check if username exists in database", authorizations = {@Authorization(value = "apiKey")})
    public int nameTaken(String name) {
        return iUserService.nameTaken(name);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get user data from database", authorizations = {@Authorization(value = "apiKey")})
    public Object getUserByNameAndPassword(String name, String password) {
        try {
            Object o = iUserService.findUserByNameAndPassword(name, password);
            if (o.getClass() == Dude.class){
                return dudeMapper.dudeToDudeDto((Dude)o);
            } if (o.getClass() == FitnessProvider.class){
                return fitnessProviderMapper.fitnessProviderToFitnessProviderDto((FitnessProvider)o);
            }
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } return null;
    }

}
