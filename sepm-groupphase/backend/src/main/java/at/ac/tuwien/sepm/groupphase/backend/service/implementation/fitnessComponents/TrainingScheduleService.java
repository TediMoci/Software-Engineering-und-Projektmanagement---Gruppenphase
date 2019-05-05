package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.ITrainingScheduleRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.ITrainingScheduleService;
import org.springframework.stereotype.Service;

@Service
public class TrainingScheduleService implements ITrainingScheduleService {

    private final ITrainingScheduleRepository iTrainingScheduleRepository;

    public TrainingScheduleService(ITrainingScheduleRepository iTrainingScheduleRepository) {
        this.iTrainingScheduleRepository = iTrainingScheduleRepository;
    }
}
