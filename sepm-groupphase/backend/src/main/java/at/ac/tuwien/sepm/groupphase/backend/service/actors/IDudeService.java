package at.ac.tuwien.sepm.groupphase.backend.service.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;

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

    /**
     *
     * @param name
     * @return
     * @throws ServiceException
     */
    Dude findByName(String name) throws ServiceException;

    /**
     *
     * @param name
     * @param password
     * @return
     * @throws ServiceException
     */
    Dude findByNameAndPassword(String name, String password) throws ServiceException;

    //TODO
    List<Dude> findAll() throws ServiceException;

    //TODO
    Dude findDudeById(Long id) throws ServiceException;

    //TODO
    Dude update(String name, Dude newDude) throws ServiceException;


    }
