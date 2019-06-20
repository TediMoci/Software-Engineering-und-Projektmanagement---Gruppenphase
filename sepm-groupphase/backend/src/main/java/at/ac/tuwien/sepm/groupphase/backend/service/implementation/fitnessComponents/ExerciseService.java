package at.ac.tuwien.sepm.groupphase.backend.service.implementation.fitnessComponents;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Exercise;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.ExerciseRating;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.MuscleGroup;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRatingRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.ExerciseBookmarkRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.fitnessComponents.IExerciseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExerciseService implements IExerciseService {

    private final IExerciseRepository iExerciseRepository;
    private final ExerciseBookmarkRepository exerciseBookmarkRepository;
    private final IDudeRepository iDudeRepository;
    private final IDudeRepository iDudeRepository;
    private final IExerciseRatingRepository iExerciseRatingRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseService.class);

    public ExerciseService(IExerciseRepository iExerciseRepository, ExerciseBookmarkRepository exerciseBookmarkRepository, IDudeRepository iDudeRepository, IExerciseRatingRepository iExerciseRatingRepository) {
        this.iExerciseRepository = iExerciseRepository;
        this.exerciseBookmarkRepository = exerciseBookmarkRepository;
        this.iDudeRepository = iDudeRepository;
        this.iDudeRepository = iDudeRepository;
        this.iExerciseRatingRepository = iExerciseRatingRepository;
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

    @Override
    public Exercise findByIdAndVersion(Long id, Integer version) throws ServiceException {
        LOGGER.info("Entering findByIdAndVersion with id: " + id + "; and version: " + version);
        try {
            return iExerciseRepository.findByIdAndVersion(id, version).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Exercise> findByName(String name, Long dudeId) throws ServiceException {
        LOGGER.info("Entering findByName with name: " + name + "; dudeId: " + dudeId);
        Dude dude = new Dude();
        dude.setId(dudeId);
        List<Exercise> exercises;
        try {
            exercises = iExerciseRepository.findByName(name);
            exercises.addAll(iExerciseRepository.findOwnPrivateByName(name, dude));
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        return exercises;
    }

    @Override
    public List<Exercise> findAll(Long dudeId) throws ServiceException {
        LOGGER.info("Entering findAll with dudeId: " + dudeId);
        Dude dude = new Dude();
        dude.setId(dudeId);
        List<Exercise> exercises;
        try {
            exercises = iExerciseRepository.findAll();
            exercises.addAll(iExerciseRepository.findOwnPrivate(dude));
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        return exercises;
    }

    @Override
    public List<Exercise> findByFilter(String filter, MuscleGroup muscleGroup, Category category, Long dudeId) throws ServiceException {
        LOGGER.info("Entering findByFilter with filter: " + filter + "; muscleGroup: " + muscleGroup + "; category: " + category + "; dudeId: " + dudeId);
        Dude dude = new Dude();
        dude.setId(dudeId);
        List<Exercise> exercises;
        try {
            if (category != null) {
                if (muscleGroup != null) {
                    exercises = iExerciseRepository.findByFilterWithMuscleGroupAndWithCategory(filter, muscleGroup, category);
                    exercises.addAll(iExerciseRepository.findOwnPrivateByFilterWithMuscleGroupAndWithCategory(filter, muscleGroup, category, dude));
                } else {
                    exercises = iExerciseRepository.findByFilterWithoutMuscleGroupAndWithCategory(filter, category);
                    exercises.addAll(iExerciseRepository.findOwnPrivateByFilterWithoutMuscleGroupAndWithCategory(filter, category, dude));
                }
            } else {
                if (muscleGroup != null) {
                    exercises = iExerciseRepository.findByFilterWithMuscleGroupAndWithoutCategory(filter, muscleGroup);
                    exercises.addAll(iExerciseRepository.findOwnPrivateByFilterWithMuscleGroupAndWithoutCategory(filter, muscleGroup, dude));
                } else {
                    exercises = iExerciseRepository.findByFilterWithoutMuscleGroupAndWithoutCategory(filter);
                    exercises.addAll(iExerciseRepository.findOwnPrivateByFilterWithoutMuscleGroupAndWithoutCategory(filter, dude));
                }
            }
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
        return exercises;
    }

    @Override
    public Exercise findById(long id) throws ServiceException {
        LOGGER.info("Entering findById with id: " + id);
        try {
            return iExerciseRepository.findById(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Exercise update(long id, Exercise newExercise) throws ServiceException {
        LOGGER.info("Updating exercise with id: " + id);
        try {
            Exercise oldExercise = iExerciseRepository.findById(id);
            if (oldExercise == null) throw new ServiceException("Could not find exercise with id: " + id);

            newExercise.setId(id);
            newExercise.setVersion(oldExercise.getVersion()+1);
            newExercise.setRatingNum(oldExercise.getRatingNum());
            newExercise.setRatingSum(oldExercise.getRatingSum());
            iExerciseRepository.delete(id);
            newExercise.setImagePath(oldExercise.getImagePath());
            iExerciseRepository.save(oldExercise);
            Long dbID = iExerciseRepository.save(newExercise).getId();
            iExerciseRepository.updateNew(newExercise.getId(), dbID);
            return newExercise;
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        LOGGER.info("Deleting exercise with id: " + id);
        try {
            Exercise exercise = iExerciseRepository.findById(id);
            if (exercise == null) throw new ServiceException("Could not find exercise with id: " + id);
            iExerciseRepository.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String updateImagePath(Long id, Integer version, String fileName) throws ServiceException {
        LOGGER.info("Entering updateImagePath with id: " + id + "; version: " + version + "; fileName: " + fileName);
        Exercise exercise;
        try {
            exercise = iExerciseRepository.findByIdAndVersion(id, version).get();
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        String imagePath = "http://localhost:8080/downloadImage/" + fileName;
        exercise.setImagePath(imagePath);
        iExerciseRepository.save(exercise);
        return imagePath;
    }

    @Override
    public void saveExerciseBookmark(Long dudeId, Long exerciseId, Integer exerciseVersion) throws ServiceException {
        LOGGER.info("Entering saveExerciseBookmark with dudeId: " + dudeId + "; exerciseId: " + exerciseId + "; exerciseVersion: " + exerciseVersion);
        try {
            if (iDudeRepository.findById(dudeId).isEmpty()) {
                throw new NoSuchElementException("Could not find Dude with id: " + dudeId);
            } else if (iExerciseRepository.findByIdAndVersion(exerciseId, exerciseVersion).isEmpty()) {
                throw new NoSuchElementException("Could not find Exercise with id: " + exerciseId);
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            if (exerciseBookmarkRepository.checkExerciseBookmark(dudeId, exerciseId, exerciseVersion) != 0) {
                throw new ServiceException("You already have this exercise bookmarked!");
            }
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
        try {
            exerciseBookmarkRepository.saveExerciseBookmark(dudeId, exerciseId, exerciseVersion);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteExerciseBookmark(Long dudeId, Long exerciseId, Integer exerciseVersion) throws ServiceException {
        LOGGER.info("Entering deleteExerciseBookmark with dudeId: " + dudeId + "; exerciseId: " + exerciseId + "; exerciseVersion: " + exerciseVersion);
        try {
            exerciseBookmarkRepository.deleteExerciseBookmark(dudeId, exerciseId, exerciseVersion);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void saveExerciseRating(Long dudeId, Long exerciseId, Integer rating) throws ServiceException {
        LOGGER.info("Entering saveExerciseRating with dudeId: " + dudeId + "; exerciseId: " + exerciseId + "; rating: " + rating);
        // checks that rating is between 1 and 5
        try {
            if (rating < 1 || rating > 5) throw new ValidationException("Rating must be between 1 and 5");
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
        checkDudeExerciseExist(dudeId,exerciseId);

        ExerciseRating exerciseRating = new ExerciseRating();
        exerciseRating.setDudeId(dudeId);
        exerciseRating.setExerciseId(exerciseId);
        exerciseRating.setRating(rating);

        Exercise exercise = iExerciseRepository.findById(exerciseId);


        // checks that dude is not rating own content
        if (exercise.getCreator().getId().equals(dudeId)){
            throw new ServiceException("You cannot rate your own content");
        }

        // checks if dude has rated this content before or not
        if (iExerciseRatingRepository.findRating(dudeId,exerciseId)==null){
            exercise.setRatingSum(rating+exercise.getRatingSum());
            exercise.setRatingNum(1+exercise.getRatingNum());

            iExerciseRatingRepository.save(exerciseRating);
            iExerciseRepository.save(exercise);
            System.out.println("Dude with id: " + dudeId + " rated Exercise with id: " + exerciseId + " with new rating: " + rating);
        } else{
            ExerciseRating foundRating = iExerciseRatingRepository.findRating(dudeId, exerciseId);
            exercise.setRatingSum(rating + exercise.getRatingSum() - foundRating.getRating());

            iExerciseRatingRepository.save(exerciseRating);
            iExerciseRepository.save(exercise);
            System.out.println("Dude with id: " + dudeId + " rated Exercise with id: " + exerciseId + " with updated rating: " + rating);
        }
    }

    @Override
    public void deleteExerciseRating(Long dudeId, Long exerciseId) throws ServiceException {
        LOGGER.info("Entering deleteExerciseRating with dudeId: " + dudeId + "; exerciseId: " + exerciseId);
        ExerciseRating exerciseRating = iExerciseRatingRepository.findRating(dudeId,exerciseId);

        if (exerciseRating==null){
            throw new ServiceException("Could not find rating to delete with dude id: " + dudeId + " and exerciseId " + exerciseId);
        } else{
            checkDudeExerciseExist(dudeId,exerciseId);
            Exercise exercise = iExerciseRepository.findById(exerciseId);

            exercise.setRatingSum(exercise.getRatingSum() - exerciseRating.getRating());
            exercise.setRatingNum(exercise.getRatingNum() - 1);

            try {
                iExerciseRatingRepository.delete(exerciseRating);
                iExerciseRepository.save(exercise);
            } catch (DataAccessException e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

    private void checkDudeExerciseExist(Long dudeId, Long exerciseId) throws ServiceException {
        try {
            if (iDudeRepository.findById(dudeId).isEmpty()) {
                throw new NoSuchElementException("Could not find Dude with id: " + dudeId);
            } else if (iExerciseRepository.findById(exerciseId)==null) {
                throw new NoSuchElementException("Could not find Exercise with id: " + exerciseId);
            }
        } catch (NoSuchElementException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
