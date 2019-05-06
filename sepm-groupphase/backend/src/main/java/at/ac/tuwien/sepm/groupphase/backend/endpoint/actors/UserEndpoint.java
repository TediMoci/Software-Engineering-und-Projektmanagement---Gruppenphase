package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.service.actors.IUserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserEndpoint {

    private final IUserService iUserService;

    public UserEndpoint(IUserService iUserService) {
        this.iUserService = iUserService;
    }
}
