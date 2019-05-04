package at.ac.tuwien.sepm.groupphase.backend.endpoint.FitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.service.FitnessComponents.IWorkoutService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkoutEndpoint {

    private final IWorkoutService iWorkoutService;

    public WorkoutEndpoint(IWorkoutService iWorkoutService) {
        this.iWorkoutService = iWorkoutService;
    }
}
