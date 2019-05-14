package at.ac.tuwien.sepm.groupphase.backend.service.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

import java.util.List;

public interface IFitnessProviderService {

    FitnessProvider save(FitnessProvider fitnessProvider) throws ServiceException;

    FitnessProvider findByNameAndPassword(String name, String password) throws ServiceException;

    Integer getNumberOfFollowers(String name) throws ServiceException;

    FitnessProvider registerNewUserAccount(FitnessProvider fitnessProvider) throws  ServiceException;

    List<FitnessProvider> findAll() throws ServiceException;

    FitnessProvider update(String name, FitnessProvider newFP) throws ServiceException;

    FitnessProvider findByName(String name) throws ServiceException;


}
