package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.repository.ICourseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ICourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseService implements ICourseService {

    private final ICourseRepository iCourseRepository;

    public CourseService(ICourseRepository iCourseRepository) {
        this.iCourseRepository = iCourseRepository;
    }
}
