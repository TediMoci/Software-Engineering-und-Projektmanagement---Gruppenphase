package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.FollowFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.DudeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final DudeValidator dudeValidator;
    private final PasswordEncoder passwordEncoder;

    public DudeService(IDudeRepository iDudeRepository, IFitnessProviderRepository iFitnessProviderRepository, FollowFitnessProviderRepository followFitnessProviderRepository, DudeValidator dudeValidator, PasswordEncoder passwordEncoder) {
        this.iDudeRepository = iDudeRepository;
        this.iFitnessProviderRepository = iFitnessProviderRepository;
        this.followFitnessProviderRepository = followFitnessProviderRepository;
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
        try {
            return iDudeRepository.save(dude);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }

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
            dudeValidator.validateDude(oldDude);
            return iDudeRepository.save(oldDude);

        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void followFitnessProvider(Long dudeId, Long fitnessProviderId) throws ServiceException {
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
            followFitnessProviderRepository.followFitnessProvider(dudeId, fitnessProviderId);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void unfollowFitnessProvider(Long dudeId, Long fitnessProviderId) throws ServiceException {
        try {
            followFitnessProviderRepository.unfollowFitnessProvider(dudeId, fitnessProviderId);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
