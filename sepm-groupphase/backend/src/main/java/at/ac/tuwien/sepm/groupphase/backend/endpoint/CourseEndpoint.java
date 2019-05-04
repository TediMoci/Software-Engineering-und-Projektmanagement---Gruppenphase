package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.service.ICourseService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseEndpoint {

    private final ICourseService iCourseService;

    public CourseEndpoint(ICourseService iCourseService) {
        this.iCourseService = iCourseService;
    }
}
