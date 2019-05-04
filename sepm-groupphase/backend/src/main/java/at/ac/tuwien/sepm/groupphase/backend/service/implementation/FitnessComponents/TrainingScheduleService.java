package at.ac.tuwien.sepm.groupphase.backend.service.implementation.FitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.repository.FitnessComponents.ITrainingScheduleRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.FitnessComponents.ITrainingScheduleService;
import org.springframework.stereotype.Service;

@Service
public class TrainingScheduleService implements ITrainingScheduleService {

    private final ITrainingScheduleRepository iTrainingScheduleRepository;

    public TrainingScheduleService(ITrainingScheduleRepository iTrainingScheduleRepository) {
        this.iTrainingScheduleRepository = iTrainingScheduleRepository;
    }
}
