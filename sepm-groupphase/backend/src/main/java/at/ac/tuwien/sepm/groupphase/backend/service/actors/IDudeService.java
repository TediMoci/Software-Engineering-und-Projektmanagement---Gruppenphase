package at.ac.tuwien.sepm.groupphase.backend.service.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import java.time.LocalDate;
import java.util.List;

public interface IDudeService {

    /**
     * @param height
     * @param weight
     * @return
     */
    double calculateBMI(double height, double weight);

    /**
     * @param birthday
     * @return
     */
    int calculateAge(LocalDate birthday);

    /**
     * @param dude
     * @return
     */
    Dude save(Dude dude) throws ServiceException;

    /**
     * @return
     * @throws ServiceException
     */
    List<Dude> findAll() throws ServiceException;

    /**
     * @param name
     * @return
     * @throws ServiceException
     */
    Dude findByName(String name) throws ServiceException;

    /**
     * @param id
     * @return
     * @throws ServiceException
     */
    Dude findDudeById(Long id) throws ServiceException;

    /**
     * @param name
     * @param newDude
     * @return
     * @throws ServiceException
     */
    Dude update(String name, Dude newDude) throws ServiceException;

    /**
     * @param id of the Dude
     * @param fileName of the image in the path
     * @throws ServiceException if something went wrong while updating the imagePath in the system
     */
    void updateImagePath(Long id, String fileName) throws ServiceException;

}
