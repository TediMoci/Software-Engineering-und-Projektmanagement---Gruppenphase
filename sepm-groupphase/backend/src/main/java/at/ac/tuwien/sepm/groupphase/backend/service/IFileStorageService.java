package at.ac.tuwien.sepm.groupphase.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

    void storeFile(String name, MultipartFile file);

}
