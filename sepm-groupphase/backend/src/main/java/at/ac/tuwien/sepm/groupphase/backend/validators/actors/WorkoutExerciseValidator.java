package at.ac.tuwien.sepm.groupphase.backend.validators.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class WorkoutExerciseValidator {

    public void validateWorkoutExercise(WorkoutExercise workoutExercise) throws ValidationException {
        String invalid_exDuration = "Min for exDuration is 1";
        String invalid_repetitions = "Min for repetitions is 1";
        String invalid_sets = "Min for sets is 1";

        if (workoutExercise.getExDuration() < 1) {
            throw new ValidationException(invalid_exDuration);
        }
        if (workoutExercise.getRepetitions() < 1) {
            throw new ValidationException(invalid_repetitions);
        }
        if (workoutExercise.getSets() < 1) {
            throw new ValidationException(invalid_sets);
        }
    }
}
