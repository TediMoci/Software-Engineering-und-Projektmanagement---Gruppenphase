package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.DudeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private final DudeValidator dudeValidator;

    @Autowired
    public DudeService(IDudeRepository iDudeRepository, DudeValidator dudeValidator) {
        this.iDudeRepository = iDudeRepository;
        this.dudeValidator = dudeValidator;
    }

    // TODO: refresh age and bmi calculation upon login; apply age/bmi calculation automatically after registration

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
            dudeValidator.validateDude(dude);
        } catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }

        double bmi = calculateBMI(dude.getHeight(), dude.getWeight());
        int age = calculateAge(dude.getBirthday());

        return iDudeRepository.save(dude);
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

        return iDudeRepository.findByName(name);
    }

    /**
     *
     * @param name
     * @param password
     * @return
     * @throws ServiceException
     */
    @Override
    public Dude findByNameAndPassword(String name, String password) throws ServiceException {
        try {
            dudeValidator.validateNameAndPassword(name, password);
        } catch (ValidationException e){
            throw new ServiceException(e.getMessage());
        }

        Dude dude = iDudeRepository.findByNameAndPassword(name, password);
        if (dude==null) throw new ServiceException("Could not find your Dude Profile");
        dude.setPassword("XXXXXXXX");

        return dude;
    }
    @Override
    public List<Dude> findAll(){
        List<Dude> dudes = new ArrayList<>();
        iDudeRepository.findAll().forEach(dudes::add);
        return dudes;
    }

    @Override
    public Dude findDudeById(Long id) throws ServiceException{
        try{
            Dude dude = iDudeRepository.findById(id).get();
            return dude;
        } catch (NoSuchElementException e){
            throw new ServiceException(e.getMessage());
        }
    }

    //TODO: Separate validator class to be written
    //TODO: Description and status taking default values if null
    @Override
    public Dude update(String name, Dude newDude) throws ServiceException {
        try {
            Dude oldDude = findByName(name);
            if (oldDude==null) throw new ServiceException("There is no dude with that name in the database.");
            Dude dude = dudeValidator.validateUpdate(oldDude, newDude);

            return iDudeRepository.save(dude);

        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
