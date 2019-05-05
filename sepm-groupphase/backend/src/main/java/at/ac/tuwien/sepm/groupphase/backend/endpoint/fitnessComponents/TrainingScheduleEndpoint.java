package at.ac.tuwien.sepm.groupphase.backend.endpoint.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingScheduleEndpoint {

    private final ITrainingScheduleService iTrainingScheduleService;

    public TrainingScheduleEndpoint(ITrainingScheduleService iTrainingScheduleService) {
        this.iTrainingScheduleService = iTrainingScheduleService;
    }
}
