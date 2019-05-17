package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService implements IExerciseService {

    private final IExerciseRepository iExerciseRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseService.class);

    public ExerciseService(IExerciseRepository iExerciseRepository) {
        this.iExerciseRepository = iExerciseRepository;
    }

    @Override
    public Exercise save(Exercise exercise) throws ServiceException {
        LOGGER.info("Entering save for: " + exercise);
        try {
            return iExerciseRepository.save(exercise);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
