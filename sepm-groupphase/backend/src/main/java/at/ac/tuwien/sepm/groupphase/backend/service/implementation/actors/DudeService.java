package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.FileStorageException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.FileStorageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.FollowFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.DudeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DudeService implements IDudeService {

    private final IDudeRepository iDudeRepository;
    private final IFitnessProviderRepository iFitnessProviderRepository;
    private final FollowFitnessProviderRepository followFitnessProviderRepository;
    private final FileStorageRepository fileStorageRepository;
    private final DudeValidator dudeValidator;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(DudeService.class);

    public DudeService(IDudeRepository iDudeRepository, IFitnessProviderRepository iFitnessProviderRepository, FollowFitnessProviderRepository followFitnessProviderRepository, FileStorageRepository fileStorageRepository, DudeValidator dudeValidator, PasswordEncoder passwordEncoder) {
        this.iDudeRepository = iDudeRepository;
        this.iFitnessProviderRepository = iFitnessProviderRepository;
        this.followFitnessProviderRepository = followFitnessProviderRepository;
        this.fileStorageRepository = fileStorageRepository;
        this.dudeValidator = dudeValidator;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * @param height
     * @param weight
     * @return
     */
    @Override
    public double calculateBMI(double height, double weight){
        double bmi = weight/Math.pow((height/100), 2);
        return new BigDecimal(String.valueOf(bmi)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     *
     * @param birthday
     * @return
     */
    @Override
    public int calculateAge(LocalDate birthday){
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    /**
     *
     * @param dude
     * @return
     * @throws ServiceException
     */
    @Override
    public Dude save(Dude dude) throws ServiceException {
        try {
            dude.setPassword(passwordEncoder.encode(dude.getPassword()));
            dudeValidator.validateNameUnique(dude.getName());
        } catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }
        Dude savedDude;
        try {
            savedDude = iDudeRepository.save(dude);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }

        String fileName = "dude_" + savedDude.getId() + ".png";
        try {
            MultipartFile multipartFile = fileStorageRepository.loadMultipartFile("kugelfisch.png");
            fileStorageRepository.storeFile(fileName, multipartFile);
        } catch (FileStorageException e) {
            throw new ServiceException(e.getMessage());
        }
        savedDude.setImagePath(updateImagePath(savedDude.getId(), fileName));
        return savedDude;
    }

    /**
     *
     * @param name
     * @return
     * @throws ServiceException
     */
    @Override
    public Dude findByName(String name) throws ServiceException {
        try {
            dudeValidator.validateName(name);
        } catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }
        Dude dude = iDudeRepository.findByName(name);
        if (dude==null) throw new ServiceException("Could not find dude with name: " + name);
        return dude;
    }

    @Override
    public List<Dude> findAll(){
        List<Dude> dudes = new ArrayList<>(iDudeRepository.findAll());
        return dudes;
    }

    @Override
    public Dude findDudeById(Long id) throws ServiceException{
        try {
            return iDudeRepository.findById(id).get();
        } catch (NoSuchElementException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Dude update(String name, Dude newDude) throws ServiceException {
        try {
            Dude oldDude = findByName(name);
            if (oldDude==null) throw new ServiceException("There is no dude with that name in the database.");
            if (!(newDude.getName().equals(oldDude.getName()))) dudeValidator.validateNameUnique(newDude.getName());

            oldDude.setName(newDude.getName());
            if (!newDude.getPassword().equals(oldDude.getPassword())){
                oldDude.setPassword(passwordEncoder.encode(newDude.getPassword()));
            }
            oldDude.setDescription(newDude.getDescription());
            oldDude.setEmail(newDude.getEmail());
            oldDude.setSex(newDude.getSex());
            oldDude.setSelfAssessment(newDude.getSelfAssessment());
            oldDude.setBirthday(newDude.getBirthday());
            oldDude.setHeight(newDude.getHeight());
            oldDude.setWeight(newDude.getWeight());
            oldDude.setIsPrivate(newDude.getIsPrivate());
            dudeValidator.validateDude(oldDude);
            return iDudeRepository.save(oldDude);

        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Boolean updateIsPrivate(String name, Boolean isPrivate) throws ServiceException {
        Dude dude = findByName(name);
        if (dude==null) throw new ServiceException("There is no dude with that name in the database.");
        dude.setIsPrivate(isPrivate);
        iDudeRepository.save(dude);
        return isPrivate;
    }


    @Override
    public void followFitnessProvider(Long dudeId, Long fitnessProviderId) throws ServiceException {
        LOGGER.info("Entering followFitnessProvider with dudeId: " + dudeId + "; fitnessProviderId: " + fitnessProviderId);
        try {
            if (iDudeRepository.findById(dudeId).isEmpty()) {
                throw new NoSuchElementException("Could not find Dude with id: " + dudeId);
            } else if (iFitnessProviderRepository.findById(fitnessProviderId).isEmpty()) {
                throw new NoSuchElementException("Could not find FitnessProvider with id: " + fitnessProviderId);
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            if (followFitnessProviderRepository.checkFollowedFitnessProvider(dudeId, fitnessProviderId) != 0) {
                throw new ServiceException("You already follow this fitness provider!");
            }
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            followFitnessProviderRepository.followFitnessProvider(dudeId, fitnessProviderId);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void unfollowFitnessProvider(Long dudeId, Long fitnessProviderId) throws ServiceException {
        LOGGER.info("Entering unfollowFitnessProvider with dudeId: " + dudeId + "; fitnessProviderId: " + fitnessProviderId);
        try {
            followFitnessProviderRepository.unfollowFitnessProvider(dudeId, fitnessProviderId);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Dude> findByFilter(String filter, Integer selfAssessment) throws ServiceException {
        try {
            if (selfAssessment != null) {
                return iDudeRepository.findByFilterWithSelfAssesment(filter, selfAssessment);
            } else {
                return iDudeRepository.findByFilterWithoutAssesment(filter);
            }

        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String updateImagePath(Long id, String fileName) throws ServiceException {
        LOGGER.info("Entering updateImagePath with id: " + id + "; fileName: " + fileName);
        Dude dude;
        try {
            dude = iDudeRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        String imagePath = "http://localhost:8080/downloadImage/" + fileName;
        dude.setImagePath(imagePath);
        iDudeRepository.save(dude);
        return imagePath;
    }
}
