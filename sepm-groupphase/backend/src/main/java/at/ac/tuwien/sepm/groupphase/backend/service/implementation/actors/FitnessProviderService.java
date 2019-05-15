package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.FitnessProviderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FitnessProviderService implements IFitnessProviderService {

    private final IFitnessProviderRepository iFitnessProviderRepository;
    private final FitnessProviderValidator fitnessProviderValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public FitnessProviderService(IFitnessProviderRepository iFitnessProviderRepository, FitnessProviderValidator fitnessProviderValidator) {
        this.iFitnessProviderRepository = iFitnessProviderRepository;
        this.fitnessProviderValidator = fitnessProviderValidator;
    }

    @Override
    public FitnessProvider save(FitnessProvider fitnessProvider) throws ServiceException {
        try{
            fitnessProvider.setPassword(passwordEncoder.encode(fitnessProvider.getPassword()));
            fitnessProviderValidator.validateFitnessProvider(fitnessProvider);
        }catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }
        return iFitnessProviderRepository.save(fitnessProvider);
    }

    @Override
    public FitnessProvider findByNameAndPassword(String name, String password) throws ServiceException {
        try {
            fitnessProviderValidator.validateNameAndPassword(name, password);
        } catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }
        FitnessProvider fitnessProvider = iFitnessProviderRepository.findByNameAndPassword(name, password);
        if (fitnessProvider==null) throw new ServiceException("Could not find your Fitness Provider Profile");
        fitnessProvider.setPassword("XXXXXXXX");

        return fitnessProvider;
    }

    @Override
    public FitnessProvider registerNewUserAccount(FitnessProvider fitnessProvider) throws ServiceException {
        return null;
    }


    @Override
    public Integer getNumberOfFollowers(String name) throws ServiceException {
        if (name.isBlank()) {
            throw new ServiceException("No name given.");
        }
        FitnessProvider fitnessProvider = iFitnessProviderRepository.findByName(name);
        if (fitnessProvider != null) {
            return fitnessProvider.getDudes().size();
        } else {
            throw new ServiceException("The Fitness Provider with the name '" + name + "' does not exist.");
        }
    }

    @Override
    public List<FitnessProvider> findAll(){
        List<FitnessProvider> fitnessProviders = new ArrayList<>();
        iFitnessProviderRepository.findAll().forEach(fitnessProviders::add);
        return fitnessProviders;
    }

    @Override
    public FitnessProvider findByName(String name) throws ServiceException {

        return iFitnessProviderRepository.findByName(name);
    }

    @Override
    public FitnessProvider update(String name, FitnessProvider newFP) throws ServiceException{
        try {
            FitnessProvider oldFP = findByName(name);
            if (oldFP==null) throw new ServiceException("There is no fitness provider with that name in the database.");
            FitnessProvider fitnessProvider = fitnessProviderValidator.validateUpdate(oldFP, newFP);

            return iFitnessProviderRepository.save(fitnessProvider);

        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
