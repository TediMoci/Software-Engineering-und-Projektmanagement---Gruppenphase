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
     * @param name of the Dude
     * @param isPrivate gives if the Dude wants his profile to be private or not
     * @return the updated value of isPrivate
     * @throws ServiceException if something went wrong while updating isPrivate in the system
     */
    Boolean updateIsPrivate(String name, Boolean isPrivate) throws ServiceException;

    /**
     * @param id of the Dude
     * @param fileName of the image in the path
     * @return the full image-path
     * @throws ServiceException if something went wrong while updating the imagePath in the system
     */
    String updateImagePath(Long id, String fileName) throws ServiceException;
    /**
     * @param dudeId of the Dude
     * @param fitnessProviderId of the FitnessProvider that the Dude wants to follow
     * @throws ServiceException if an error occurred while trying to follow the FitnessProvider
     */
    void followFitnessProvider(Long dudeId, Long fitnessProviderId) throws ServiceException;

    /**
     * @param dudeId of the Dude
     * @param fitnessProviderId of the FitnessProvider that the Dude wants to unfollow
     * @throws ServiceException if an error occurred while trying to unfollow the FitnessProvider
     */
    void unfollowFitnessProvider(Long dudeId, Long fitnessProviderId) throws ServiceException;

    List<Dude> findByFilter(String filter, Integer selfAssessment) throws ServiceException;

}
