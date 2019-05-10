package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;

import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
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

    public int nameTaken(String name) {
        /*
        if (iFitnessProviderRepository.findByName(name) != null){
            return 0;
        } else
         */
         if (iDudeRepository.findByName(name) != null){
            return 1;
        } else {
            return 2;
        }

    }

}
