package at.ac.tuwien.sepm.groupphase.backend.validators;

import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TrainingScheduleValidator {

    private String name_is_null = "Name must be set!";
    private String name_is_blank = "Name must not be blank!";
    private String description_is_null = "Description must not be null!";
    private String description_is_blank = "Description must not be null!";
    private String description_too_long = "Description can only be 3000 characters long";
    private String difficulty_is_null = "Difficulty must not be null!";
    private String invalid_difficulty = "Invalid difficulty level!";
    private String interval_length_is_null = "Interval length must not be null!";
    private String invalid_interval_length = "Interval length must be between 1 and 7!";
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingScheduleValidator.class);

    public void validateTrainingSchedule(TrainingSchedule trainingSchedule) throws ValidationException {
        if (trainingSchedule.getName() == null) {
            LOGGER.error("Name must be set!");
            throw new ValidationException(name_is_null);
        } if(trainingSchedule.getName().isBlank()) {
            LOGGER.error("Name must not be blank!");
            throw new ValidationException(name_is_blank);
        } if (trainingSchedule.getDescription() == null) {
            throw new ValidationException(description_is_null);
        } if (trainingSchedule.getDescription().isBlank()) {
            throw new ValidationException(description_is_blank);
        } if (trainingSchedule.getDescription().length() > 3000) {
            throw new ValidationException(description_too_long);
        } if (trainingSchedule.getDifficulty() == null) {
            throw new ValidationException(difficulty_is_null);
        } if (trainingSchedule.getDifficulty() < 1 || trainingSchedule.getDifficulty() > 3) {
            throw new ValidationException(invalid_difficulty);
        } if (trainingSchedule.getIntervalLength() == null) {
            throw new ValidationException(interval_length_is_null);
        } if (trainingSchedule.getIntervalLength() < 1 || trainingSchedule.getIntervalLength() > 7) {
            throw new ValidationException(invalid_interval_length);
        }
    }

}
