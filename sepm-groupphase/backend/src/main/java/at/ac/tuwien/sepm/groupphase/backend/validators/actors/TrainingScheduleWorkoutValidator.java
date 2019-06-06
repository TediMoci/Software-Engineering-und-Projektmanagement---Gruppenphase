package at.ac.tuwien.sepm.groupphase.backend.validators.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TrainingScheduleWorkoutValidator {
    public void validateTrainingScheduleWorkout(TrainingScheduleWorkout trainingScheduleWorkout) throws ValidationException {
        String day_tooSmall = "Min for day is 1";
        String day_tooBig = "Max for day is 7";

        if (trainingScheduleWorkout.getDay() < 1) {
            throw new ValidationException(day_tooSmall);
        }
        if (trainingScheduleWorkout.getDay() > 7) {
            throw new ValidationException(day_tooBig);
        }
    }
}
