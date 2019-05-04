package at.ac.tuwien.sepm.groupphase.backend.service.implementation.FitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.repository.FitnessComponents.IWorkoutRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.FitnessComponents.IWorkoutService;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService implements IWorkoutService {

    private final IWorkoutRepository iWorkoutRepository;

    public WorkoutService(IWorkoutRepository iWorkoutRepository) {
        this.iWorkoutRepository = iWorkoutRepository;
    }
}
