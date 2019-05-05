package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
import org.springframework.stereotype.Service;

@Service
public class FitnessProviderService implements IFitnessProviderService {

    private final IFitnessProviderRepository iFitnessProviderRepository;

    public FitnessProviderService(IFitnessProviderRepository iFitnessProviderRepository) {
        this.iFitnessProviderRepository = iFitnessProviderRepository;
    }
}
