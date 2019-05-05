package at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import at.ac.tuwien.sepm.groupphase.backend.validators.actors.DudeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

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
        return weight/Math.pow((height/100), 2);
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
}
