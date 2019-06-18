package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.configuration.properties.FileStorageProperties;
import at.ac.tuwien.sepm.groupphase.backend.exception.FileStorageException;
import at.ac.tuwien.sepm.groupphase.backend.exception.MyFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Repository
public class FileStorageRepository {

    private final Path fileStorageLocation;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageRepository.class);

    @Autowired
    public FileStorageRepository(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
            .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public void storeFile(String fileName, MultipartFile file) {
        LOGGER.info("Entering storeFile with fileName: " + fileName);
        try {
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public MultipartFile loadMultipartFile(String fileName) {
        LOGGER.info("Entering loadMultipartFile with fileName: " + fileName);
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return new MockMultipartFile("file", fileName, "image/png", Files.readAllBytes(filePath));
        } catch (IOException e) {
            throw new FileStorageException("Could not load file " + fileName, e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        LOGGER.info("Entering loadFileAsResource with fileName: " + fileName);
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new MyFileNotFoundException("File not found " + fileName, e);
        }
    }

}
