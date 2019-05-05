package at.ac.tuwien.sepm.groupphase.backend.service.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.time.LocalDate;

public interface IDudeService {

    /**
     *
     * @param height
     * @param weight
     * @return
     */
    double calculateBMI(double height, double weight);

    /**
     *
     * @param birthday
     * @return
     */
    int calculateAge(LocalDate birthday);

    /**
     *
     * @param dude
     * @return
     */
    Dude save(Dude dude) throws ServiceException;
}
