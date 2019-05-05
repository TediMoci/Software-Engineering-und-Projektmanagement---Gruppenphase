package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FitnessProviderEndpoint {

    private final IFitnessProviderService iFitnessProviderService;

    public FitnessProviderEndpoint(IFitnessProviderService iFitnessProviderService) {
        this.iFitnessProviderService = iFitnessProviderService;
    }
}
