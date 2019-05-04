package at.ac.tuwien.sepm.groupphase.backend.service.implementation.FitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.repository.FitnessComponents.IExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.FitnessComponents.IExerciseService;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService implements IExerciseService {

    private final IExerciseRepository iExerciseRepository;

    public ExerciseService(IExerciseRepository iExerciseRepository) {
        this.iExerciseRepository = iExerciseRepository;
    }
}
