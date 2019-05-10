package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
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

    @Override
    public int nameTaken(String name) {

        if (iFitnessProviderRepository.findByName(name) != null){
            return 0;
        } else if (iDudeRepository.findByName(name) != null){
            return 1;
        } else {
            return 2;
        }

    }

    /**
     *
     * @param name
     * @param password
     * @return
     * @throws ServiceException
     */
    @Override
    public Object findUserByNameAndPassword(String name, String password) throws ServiceException {

        Dude dude = iDudeRepository.findByNameAndPassword(name, password);
        FitnessProvider fitnessProvider = iFitnessProviderRepository.findByNameAndPassword(name, password);

        if (dude != null){
            dude.setPassword("XXXXXXXX");
            return dude;
        } else if (fitnessProvider != null){
            fitnessProvider.setPassword("XXXXXXXX");
            return fitnessProvider;
        } else {
            throw new ServiceException("Wrong name or password!");
        }
    }

}
