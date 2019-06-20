package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

    void storeFile(String fileName, MultipartFile file) throws ServiceException;

    Resource loadFileAsResource(String fileName) throws ServiceException;

}
