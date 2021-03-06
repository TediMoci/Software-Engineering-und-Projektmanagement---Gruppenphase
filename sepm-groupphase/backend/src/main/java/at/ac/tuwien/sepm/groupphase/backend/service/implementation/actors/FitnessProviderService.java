package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.exception.FileStorageException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.FileStorageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.FitnessProviderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FitnessProviderService implements IFitnessProviderService {

    private final IFitnessProviderRepository iFitnessProviderRepository;
    private final FileStorageRepository fileStorageRepository;
    private final FitnessProviderValidator fitnessProviderValidator;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(FitnessProviderService.class);

    public FitnessProviderService(IFitnessProviderRepository iFitnessProviderRepository, FileStorageRepository fileStorageRepository, FitnessProviderValidator fitnessProviderValidator, PasswordEncoder passwordEncoder) {
        this.iFitnessProviderRepository = iFitnessProviderRepository;
        this.fileStorageRepository = fileStorageRepository;
        this.fitnessProviderValidator = fitnessProviderValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public FitnessProvider save(FitnessProvider fitnessProvider) throws ServiceException {
        try{
            fitnessProviderValidator.validateNameUnique(fitnessProvider.getName());
            fitnessProvider.setPassword(passwordEncoder.encode(fitnessProvider.getPassword()));
        } catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }
        FitnessProvider savedFP;
        try {
            savedFP = iFitnessProviderRepository.save(fitnessProvider);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }

        String fileName = "fitness_provider_" + savedFP.getId() + ".png";
        try {
            MultipartFile multipartFile = fileStorageRepository.loadMultipartFile("kugelfisch2.png");
            fileStorageRepository.storeFile(fileName, multipartFile);
        } catch (FileStorageException e) {
            throw new ServiceException(e.getMessage());
        }
        savedFP.setImagePath(updateImagePath(savedFP.getId(), fileName));
        return savedFP;
    }

    @Override
    public FitnessProvider findById(Long id) throws ServiceException {
        LOGGER.info("Entering findById with id: " + id);
        try {
            return iFitnessProviderRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
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
        try {
            fitnessProviderValidator.validateName(name);
        } catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }
        FitnessProvider fitnessProvider = iFitnessProviderRepository.findByName(name);
        if (fitnessProvider==null) throw new ServiceException("Could not find fitness provider with name: " + name);
        return fitnessProvider;
    }

    @Override
    public FitnessProvider update(String name, FitnessProvider newFitnessProvider) throws ServiceException{
        try {
            FitnessProvider oldFitnessProvider = findByName(name);
            if (oldFitnessProvider==null) throw new ServiceException("There is no fitness provider with that name in the database.");
            if (!(oldFitnessProvider.getName().equals(newFitnessProvider.getName()))) fitnessProviderValidator.validateNameUnique(newFitnessProvider.getName());
            oldFitnessProvider.setName(newFitnessProvider.getName());
            if (!newFitnessProvider.getPassword().equals(oldFitnessProvider.getPassword())){
                oldFitnessProvider.setPassword(passwordEncoder.encode(newFitnessProvider.getPassword()));
            }
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

    @Override
    public String updateImagePath(Long id, String fileName) throws ServiceException {
        LOGGER.info("Entering updateImagePath with id: " + id + "; fileName: " + fileName);
        FitnessProvider fitnessProvider;
        try {
            fitnessProvider = iFitnessProviderRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        String imagePath = "http://localhost:8080/downloadImage/" + fileName;
        fitnessProvider.setImagePath(imagePath);
        iFitnessProviderRepository.save(fitnessProvider);
        return imagePath;
    }

    @Override
    public List<FitnessProvider> findByFilter(String filter) throws ServiceException {
        LOGGER.info("Entering findByFilter with filter: " + filter);
        try {
            return iFitnessProviderRepository.findByFilter(filter);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
