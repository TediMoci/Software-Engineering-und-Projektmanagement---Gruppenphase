package at.ac.tuwien.sepm.groupphase.backend.validators;

import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TrainingScheduleValidator {

    String name_is_null = "Name must be set!";
    String name_is_blank = "Name must not be blank!";
    String description_is_null = "Description must not be null!";
    String description_is_blank = "Description must not be null!";
    String description_too_long = "Description can only be 3000 characters long";
    String difficulty_is_null = "Difficulty must not be null!";
    String invalid_difficulty = "Invalid difficulty level!";

    public void validateTrainingSchedule(TrainingSchedule trainingSchedule) throws ValidationException {
        if (trainingSchedule.getName() == null) {
            throw new ValidationException(name_is_null);
        }
        if (trainingSchedule.getName().isBlank()) {
            throw new ValidationException(name_is_blank);
        }
        if (trainingSchedule.getDifficulty() == null) {
            throw new ValidationException(difficulty_is_null);
        }
        if (trainingSchedule.getDifficulty() < 1 || trainingSchedule.getDifficulty() > 3) {
            throw new ValidationException(invalid_difficulty);
        }
        if (trainingSchedule.getDescription() == null) {
            throw new ValidationException(description_is_null);
        }
        if (trainingSchedule.getDescription().isBlank()) {
            throw new ValidationException(description_is_blank);
        }
        if (trainingSchedule.getDescription().length() > 3000) {
            throw new ValidationException(description_too_long);
        }
    }

}
