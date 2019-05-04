package at.ac.tuwien.sepm.groupphase.backend.endpoint.Actor;

import at.ac.tuwien.sepm.groupphase.backend.service.Actors.IDudeService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DudeEndpoint {

    private final IDudeService iDudeService;

    public DudeEndpoint(IDudeService iDudeService) {
        this.iDudeService = iDudeService;
    }
}
