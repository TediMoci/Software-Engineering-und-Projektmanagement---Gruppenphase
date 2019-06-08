package at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.compositeKeys.ExerciseDoneKey;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseDone;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExerciseDoneRepository extends JpaRepository<ExerciseDone, ExerciseDoneKey> {

    /**
     * @param exerciseDone to be saved in the database
     * @return the saved ExerciseDone
     * @throws DataAccessException if an error occurred while trying to save the ExerciseDone in the database
     */
    ExerciseDone save(ExerciseDone exerciseDone) throws DataAccessException;

    /**
     *
     * @param exerciseDone to be deleted
     */
    void delete(ExerciseDone exerciseDone);

    /**
     *
     * @param activeTrainingScheduleId
     * @param dudeId
     * @param trainingScheduleId
     * @param trainingScheduleVersion
     * @param workoutId
     * @param workoutVersion
     * @param day
     * @return
     */
    List<ExerciseDone> findByActiveTrainingScheduleIdAndDudeIdAndTrainingScheduleIdAndTrainingScheduleVersionAndWorkoutIdAndWorkoutVersionAndDay(Long activeTrainingScheduleId, Long dudeId, Long trainingScheduleId, Integer trainingScheduleVersion, Long workoutId, Integer workoutVersion, Integer day);
}
