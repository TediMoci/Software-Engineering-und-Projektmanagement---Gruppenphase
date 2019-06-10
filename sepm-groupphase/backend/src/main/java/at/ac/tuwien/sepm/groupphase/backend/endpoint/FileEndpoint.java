package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UploadFileResponseDto;
import at.ac.tuwien.sepm.groupphase.backend.service.IFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileEndpoint {

    private final IFileStorageService iFileStorageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileEndpoint.class);

    @Autowired
    public FileEndpoint(IFileStorageService iFileStorageService) {
        this.iFileStorageService = iFileStorageService;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponseDto uploadFile(@RequestParam("file") MultipartFile file) {
        LOGGER.info("Entering uploadFile");
        String fileName = iFileStorageService.storeFile("", file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/downloadFile/")
            .path(fileName)
            .toUriString();

        return new UploadFileResponseDto(fileName, fileDownloadUri,
            file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        LOGGER.info("Entering downloadFile");
        // Load file as Resource
        Resource resource = iFileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOGGER.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

}
