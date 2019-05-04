package at.ac.tuwien.sepm.groupphase.backend.service.implementation.Actors;
import at.ac.tuwien.sepm.groupphase.backend.repository.Actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.Actors.IFitnessProviderService;
import org.springframework.stereotype.Service;

@Service
public class FitnessProviderService implements IFitnessProviderService {

    private final IFitnessProviderRepository iFitnessProviderRepository;

    public FitnessProviderService(IFitnessProviderRepository iFitnessProviderRepository) {
        this.iFitnessProviderRepository = iFitnessProviderRepository;
    }
}
