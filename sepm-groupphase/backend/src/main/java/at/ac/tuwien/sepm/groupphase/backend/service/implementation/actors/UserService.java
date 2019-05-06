package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;

import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IFitnessProviderRepository iFitnessProviderRepository;
    private final IDudeRepository iDudeRepository;

    public UserService(IFitnessProviderRepository iFitnessProviderRepository, IDudeRepository iDudeRepository) {
        this.iFitnessProviderRepository = iFitnessProviderRepository;
        this.iDudeRepository = iDudeRepository;
    }

}
