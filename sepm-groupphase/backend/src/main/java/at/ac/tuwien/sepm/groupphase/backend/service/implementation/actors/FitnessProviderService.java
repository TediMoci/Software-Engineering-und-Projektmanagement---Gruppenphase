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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FitnessProviderService(IFitnessProviderRepository iFitnessProviderRepository, FitnessProviderValidator fitnessProviderValidator, PasswordEncoder passwordEncoder) {
        this.iFitnessProviderRepository = iFitnessProviderRepository;
        this.fitnessProviderValidator = fitnessProviderValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public FitnessProvider save(FitnessProvider fitnessProvider) throws ServiceException {
        try{
            fitnessProviderValidator.validateNameUnique(fitnessProvider.getName());
            fitnessProvider.setPassword(passwordEncoder.encode(fitnessProvider.getPassword()));
            fitnessProviderValidator.validateFitnessProvider(fitnessProvider);
        }catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }
        return iFitnessProviderRepository.save(fitnessProvider);
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
    public FitnessProvider update(String name, FitnessProvider newFitnessProvider) throws ServiceException{
        try {
            FitnessProvider oldFitnessProvider = findByName(name);
            if (oldFitnessProvider==null) throw new ServiceException("There is no fitness provider with that name in the database.");
            if (!(oldFitnessProvider.getName().equals(newFitnessProvider.getName()))) fitnessProviderValidator.validateNameUnique(newFitnessProvider.getName());
            oldFitnessProvider.setName(newFitnessProvider.getName());
            oldFitnessProvider.setPassword(newFitnessProvider.getPassword());
            oldFitnessProvider.setDescription(newFitnessProvider.getDescription());
            oldFitnessProvider.setAddress(newFitnessProvider.getAddress());
            oldFitnessProvider.setEmail(newFitnessProvider.getEmail());
            oldFitnessProvider.setPhoneNumber(newFitnessProvider.getPhoneNumber());
            oldFitnessProvider.setWebsite(newFitnessProvider.getWebsite());
            fitnessProviderValidator.validateFitnessProvider(oldFitnessProvider);
            return iFitnessProviderRepository.save(oldFitnessProvider);

        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
