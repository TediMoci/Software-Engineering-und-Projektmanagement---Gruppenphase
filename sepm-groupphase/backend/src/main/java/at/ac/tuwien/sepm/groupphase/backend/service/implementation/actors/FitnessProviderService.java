package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.FitnessProviderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class FitnessProviderService implements IFitnessProviderService {

    private final IFitnessProviderRepository iFitnessProviderRepository;
    private final FitnessProviderValidator fitnessProviderValidator;

    @Autowired
    public FitnessProviderService(IFitnessProviderRepository iFitnessProviderRepository, FitnessProviderValidator fitnessProviderValidator) {
        this.iFitnessProviderRepository = iFitnessProviderRepository;
        this.fitnessProviderValidator = fitnessProviderValidator;
    }

    @Override
    public FitnessProvider save(FitnessProvider fitnessProvider) throws ServiceException {

        try{
            fitnessProviderValidator.validateFitnessProvider(fitnessProvider);
        }catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }
        fitnessProvider.setRoles(Arrays.asList("FITNESS_PROVIDER"));
        return iFitnessProviderRepository.save(fitnessProvider);
    }

    @Override
    public FitnessProvider findByNameAndPassword(String name, String password) throws ServiceException {
        try {
            fitnessProviderValidator.validateNameAndPassword(name, password);
        }catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }
        FitnessProvider fitnessProvider = iFitnessProviderRepository.findByNameAndPassword(name, password);
        fitnessProvider.setPassword("XXXXXXXX");

        return fitnessProvider;
    }

    @Override
    public FitnessProvider registerNewUserAccount(FitnessProvider fitnessProvider) throws ServiceException {
        return null;
    }

}
