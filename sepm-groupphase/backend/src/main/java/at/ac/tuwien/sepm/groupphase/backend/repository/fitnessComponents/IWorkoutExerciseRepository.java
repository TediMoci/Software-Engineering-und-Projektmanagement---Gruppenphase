package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;
import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.WorkoutExerciseKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWorkoutExerciseRepository extends JpaRepository<WorkoutExercise, WorkoutExerciseKey> {

    /**
     * @param workoutExercise to be saved in the database
     * @return the saved workoutExercise
     * @throws DataAccessException if an error occurred while trying to save the workoutExercise in the database
     */
    WorkoutExercise save(WorkoutExercise workoutExercise) throws DataAccessException;

    /**
     *
     * @param workout
     * @return
     */
    List<WorkoutExercise> findByWorkout(Workout workout);
}
