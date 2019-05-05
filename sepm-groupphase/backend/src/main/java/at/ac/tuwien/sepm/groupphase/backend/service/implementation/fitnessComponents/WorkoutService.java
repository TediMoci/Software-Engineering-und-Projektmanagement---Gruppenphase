package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IWorkoutRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IWorkoutService;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService implements IWorkoutService {

    private final IWorkoutRepository iWorkoutRepository;

    public WorkoutService(IWorkoutRepository iWorkoutRepository) {
        this.iWorkoutRepository = iWorkoutRepository;
    }
}
