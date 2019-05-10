package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

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

    public UserEndpoint(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Check if username exists in database", authorizations = {@Authorization(value = "apiKey")})
    public int nameTaken(String name) {
        try {
            return iUserService.nameTaken(name);
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during checking username availability: " + e.getMessage(), e);
        }
    }
}
