package at.ac.tuwien.sepm.groupphase.backend.endpoint.FitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.service.FitnessComponents.IExerciseService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseEndpoint {

    private final IExerciseService iExerciseService;

    public ExerciseEndpoint(IExerciseService iExerciseService) {
        this.iExerciseService = iExerciseService;
    }
}
