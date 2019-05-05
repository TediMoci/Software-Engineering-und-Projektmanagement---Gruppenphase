package at.ac.tuwien.sepm.groupphase.backend.endpoint.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseEndpoint {

    private final IExerciseService iExerciseService;

    public ExerciseEndpoint(IExerciseService iExerciseService) {
        this.iExerciseService = iExerciseService;
    }
}
