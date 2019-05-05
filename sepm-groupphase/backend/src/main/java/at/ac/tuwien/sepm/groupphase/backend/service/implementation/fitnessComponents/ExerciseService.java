package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService implements IExerciseService {

    private final IExerciseRepository iExerciseRepository;

    public ExerciseService(IExerciseRepository iExerciseRepository) {
        this.iExerciseRepository = iExerciseRepository;
    }
}
