package at.ac.tuwien.sepm.groupphase.backend.service.actors;

import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;

public interface IUserService {

    /**
     *
     * @param name
     * @return
     * @throws ServiceException
     */
    int nameTaken(String name);

}
