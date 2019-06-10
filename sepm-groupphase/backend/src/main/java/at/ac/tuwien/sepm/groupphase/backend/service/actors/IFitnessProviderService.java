package at.ac.tuwien.sepm.groupphase.backend.service.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.util.List;

public interface IFitnessProviderService {

    FitnessProvider save(FitnessProvider fitnessProvider) throws ServiceException;

    /**
     * @param id of the FitnessProvider to find in the system
     * @return the FitnessProvider with the given id
     * @throws ServiceException if something went wrong while finding the FitnessProvider in the system
     */
    FitnessProvider findById(Long id) throws ServiceException;

    Integer getNumberOfFollowers(String name) throws ServiceException;

    List<FitnessProvider> findAll() throws ServiceException;

    FitnessProvider update(String name, FitnessProvider newFP) throws ServiceException;

    FitnessProvider findByName(String name) throws ServiceException;


    List<FitnessProvider> findByFilter(String filter) throws ServiceException;
    /**
     * @param id of the FitnessProvider
     * @param fileName of the image in the path
     * @return the full image-path
     * @throws ServiceException if something went wrong while updating the imagePath in the system
     */
    String updateImagePath(Long id, String fileName) throws ServiceException;
}
