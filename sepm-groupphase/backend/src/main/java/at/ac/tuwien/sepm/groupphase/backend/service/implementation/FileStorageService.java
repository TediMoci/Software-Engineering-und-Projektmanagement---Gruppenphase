package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.exception.FileStorageException;
import at.ac.tuwien.sepm.groupphase.backend.exception.MyFileNotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.FileStorageRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.IFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements IFileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageService.class);

    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    @Override
    public void storeFile(String fileName, MultipartFile file) throws ServiceException {
        LOGGER.info("Entering storeFile with fileName: " + fileName);

        // Check if the file's name contains invalid characters
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        try {
            fileStorageRepository.storeFile(fileName, file);
        } catch (FileStorageException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) throws ServiceException {
        LOGGER.info("Entering loadFileAsResource with fileName: " + fileName);
        try {
            return fileStorageRepository.loadFileAsResource(fileName);
        } catch (MyFileNotFoundException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
