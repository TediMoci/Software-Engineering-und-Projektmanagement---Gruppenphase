package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public class TrainingScheduleIntegrationTest {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String DUDE_ENDPOINT = "/dudes";
    private static final String WORKOUT_ENDPOINT = "/workout";
    private static final String EXERCISE_ENDPOINT = "/exercise";
    private static final String TRAININGSCHEDULE_ENDPOINT = "/trainingSchedule";
    private static final DudeDto dudeDto = new DudeDto();
    private static final TrainingScheduleDto trainingScheduleDto = new TrainingScheduleDto();
    private static final TrainingScheduleDto invalidTrainingScheduleDto = new TrainingScheduleDto();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutDtoIn1 = new TrainingScheduleWorkoutDtoIn();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutDtoIn2 = new TrainingScheduleWorkoutDtoIn();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutDtoIn3 = new TrainingScheduleWorkoutDtoIn();
    private static final WorkoutDto workoutDto1 = new WorkoutDto();
    private static final WorkoutDto workoutDto2 = new WorkoutDto();
    private static final WorkoutExerciseDtoIn workoutExerciseDtoIn1 = new WorkoutExerciseDtoIn();
    private static final WorkoutExerciseDtoIn workoutExerciseDtoIn2 = new WorkoutExerciseDtoIn();
    private static final ExerciseDto exerciseDto1 = new ExerciseDto();
    private static final ExerciseDto exerciseDto2 = new ExerciseDto();

    private static boolean initialized = false;

    @LocalServerPort
    private int port;

    @BeforeClass
    public static void beforeClass() {
        dudeDto.setName("Dude1");
        dudeDto.setPassword("qqqqqqqq");
        dudeDto.setEmail("test@email.com");
        dudeDto.setSex(Sex.Male);
        dudeDto.setSelfAssessment(1);
        dudeDto.setBirthday(LocalDate.of(2000, 1, 1));
        dudeDto.setHeight(150.0);
        dudeDto.setWeight(50.0);

        exerciseDto1.setName("Exercise1");
        exerciseDto1.setDescription("Description1");
        exerciseDto1.setEquipment("Equipment1");
        exerciseDto1.setMuscleGroup("MuscleGroup1");
        exerciseDto1.setCategory(Category.Strength);

        exerciseDto2.setName("Exercise2");
        exerciseDto2.setDescription("Description2");
        exerciseDto2.setEquipment("Equipment2");
        exerciseDto2.setMuscleGroup("MuscleGroup2");
        exerciseDto2.setCategory(Category.Endurance);

        workoutDto1.setName("Workout1");
        workoutDto1.setDescription("Description1");
        workoutDto1.setDifficulty(1);
        workoutDto1.setCalorieConsumption(100.0);

        workoutDto2.setName("Workout2");
        workoutDto2.setDescription("Description2");
        workoutDto2.setDifficulty(2);
        workoutDto2.setCalorieConsumption(200.0);

        workoutExerciseDtoIn1.setExDuration(25);
        workoutExerciseDtoIn1.setRepetitions(8);
        workoutExerciseDtoIn1.setSets(3);

        workoutExerciseDtoIn2.setExDuration(90);
        workoutExerciseDtoIn2.setRepetitions(20);
        workoutExerciseDtoIn2.setSets(4);

        trainingScheduleWorkoutDtoIn1.setDay(1);
        trainingScheduleWorkoutDtoIn2.setDay(2);
        trainingScheduleWorkoutDtoIn3.setDay(3);

        trainingScheduleDto.setName("TrainingsSchedule1");
        trainingScheduleDto.setDescription("Description1");
        trainingScheduleDto.setDifficulty(1);
        trainingScheduleDto.setIntervalLength(3);
        trainingScheduleDto.setRating(3.0);

        invalidTrainingScheduleDto.setName("TrainingScheduleInvalid");
    }


    @Before
    public void before() {
        if (!initialized) {
            HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dudeDto);
            ResponseEntity<DudeDto> response = REST_TEMPLATE
                .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
            Long dudeId = response.getBody().getId();
            exerciseDto1.setCreatorId(dudeId);
            exerciseDto2.setCreatorId(dudeId);
            trainingScheduleDto.setCreatorId(dudeId);

            HttpEntity<ExerciseDto> exerciseRequest1 = new HttpEntity<>(exerciseDto1);
            ResponseEntity<ExerciseDto> exerciseResponse1 = REST_TEMPLATE
                .exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest1, ExerciseDto.class);
            HttpEntity<ExerciseDto> exerciseRequest2 = new HttpEntity<>(exerciseDto2);
            ResponseEntity<ExerciseDto> exerciseResponse2 = REST_TEMPLATE
                .exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest2, ExerciseDto.class);
            Long exercise1Id = exerciseResponse1.getBody().getId();
            Integer exercise1Version = exerciseResponse1.getBody().getVersion();
            Long exercise2Id = exerciseResponse2.getBody().getId();
            Integer exercise2Version = exerciseResponse2.getBody().getVersion();
            workoutExerciseDtoIn1.setExerciseId(exercise1Id);
            workoutExerciseDtoIn1.setExerciseVersion(exercise1Version);
            workoutExerciseDtoIn2.setExerciseId(exercise2Id);
            workoutExerciseDtoIn2.setExerciseVersion(exercise2Version);
            WorkoutExerciseDtoIn[] workoutExerciseDtoIns = new WorkoutExerciseDtoIn[]{workoutExerciseDtoIn1, workoutExerciseDtoIn2};
            workoutDto1.setWorkoutExercises(workoutExerciseDtoIns);
            workoutDto2.setWorkoutExercises(workoutExerciseDtoIns);
            HttpEntity<WorkoutDto> workoutRequest = new HttpEntity<>(workoutDto1);
            ResponseEntity<WorkoutDto> workoutResponse1 = REST_TEMPLATE
                .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest, WorkoutDto.class);
            HttpEntity<WorkoutDto> workoutRequest2 = new HttpEntity<>(workoutDto2);
            ResponseEntity<WorkoutDto> workoutResponse2 = REST_TEMPLATE
                .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest2, WorkoutDto.class);
            trainingScheduleWorkoutDtoIn1.setWorkoutId(workoutResponse1.getBody().getId());
            trainingScheduleWorkoutDtoIn1.setWorkoutVersion(workoutResponse1.getBody().getVersion());
            trainingScheduleWorkoutDtoIn2.setWorkoutId(workoutResponse2.getBody().getId());
            trainingScheduleWorkoutDtoIn2.setWorkoutVersion(workoutResponse2.getBody().getVersion());
            trainingScheduleWorkoutDtoIn1.setWorkoutId(workoutResponse1.getBody().getId());
            trainingScheduleWorkoutDtoIn1.setWorkoutVersion(workoutResponse1.getBody().getVersion());
            TrainingScheduleWorkoutDtoIn[] trainingScheduleWorkouts = new TrainingScheduleWorkoutDtoIn[]{trainingScheduleWorkoutDtoIn1, trainingScheduleWorkoutDtoIn2, trainingScheduleWorkoutDtoIn3};
            trainingScheduleDto.setTrainingScheduleWorkouts(trainingScheduleWorkouts);

            initialized = true;
        }
    }

     @Test
    public void whenSaveTrainingScheduleManually_then201CreatedAndGetSavedTrainingSchedule() {
        HttpEntity<TrainingScheduleDto> trainingScheduleRequest = new HttpEntity<>(trainingScheduleDto);
        ResponseEntity<TrainingScheduleDto> TrainingScheduleResponse = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT, HttpMethod.POST, trainingScheduleRequest, TrainingScheduleDto.class);
        assertEquals(HttpStatus.CREATED, TrainingScheduleResponse.getStatusCode());
        TrainingScheduleDto responseTrainingScheduleDto = TrainingScheduleResponse.getBody();
        assertNotNull(responseTrainingScheduleDto.getId());
        responseTrainingScheduleDto.setId(1L);
        trainingScheduleDto.setId(1L);
        responseTrainingScheduleDto.setTrainingScheduleWorkouts(trainingScheduleDto.getTrainingScheduleWorkouts());
        assertEquals(trainingScheduleDto, responseTrainingScheduleDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenSaveOneInvalidTrainingSchedule_then400BadRequest() {
        HttpEntity<TrainingScheduleDto> trainingScheduleRequest = new HttpEntity<>(invalidTrainingScheduleDto);
        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT, HttpMethod.POST, trainingScheduleRequest, TrainingScheduleDto.class);
    }

}
