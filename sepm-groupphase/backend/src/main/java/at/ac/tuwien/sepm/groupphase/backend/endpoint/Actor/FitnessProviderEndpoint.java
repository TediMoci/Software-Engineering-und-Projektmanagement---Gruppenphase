package at.ac.tuwien.sepm.groupphase.backend.endpoint.Actor;

import at.ac.tuwien.sepm.groupphase.backend.service.Actors.IFitnessProviderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FitnessProviderEndpoint {

    private final IFitnessProviderService iFitnessProviderService;

    public FitnessProviderEndpoint(IFitnessProviderService iFitnessProviderService) {
        this.iFitnessProviderService = iFitnessProviderService;
    }
}
