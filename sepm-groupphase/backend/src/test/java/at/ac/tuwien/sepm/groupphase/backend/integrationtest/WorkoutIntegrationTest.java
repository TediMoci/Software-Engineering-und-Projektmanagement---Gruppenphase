package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.WorkoutExerciseDtoIn;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.MuscleGroup;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public class WorkoutIntegrationTest {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String WORKOUT_ENDPOINT = "/workout";
    private static final String EXERCISE_ENDPOINT = "/exercise";
    private static final String DUDE_ENDPOINT = "/dudes";
    private static final DudeDto dudeDto = new DudeDto();
    private static final ExerciseDto exerciseDto1 = new ExerciseDto();
    private static final ExerciseDto exerciseDto2 = new ExerciseDto();
    private static final WorkoutDto validWorkoutDto1 = new WorkoutDto();
    private static final WorkoutDto validWorkoutDto2 = new WorkoutDto();
    private static final WorkoutDto invalidWorkoutDto1 = new WorkoutDto();

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
        exerciseDto1.setMuscleGroup(MuscleGroup.Other);
        exerciseDto1.setCategory(Category.Strength);

        exerciseDto2.setName("Exercise2");
        exerciseDto2.setDescription("Description2");
        exerciseDto2.setEquipment("Equipment2");
        exerciseDto2.setMuscleGroup(MuscleGroup.Other);
        exerciseDto2.setCategory(Category.Endurance);

        validWorkoutDto1.setName("Workout1");
        validWorkoutDto1.setDescription("Description1");
        validWorkoutDto1.setDifficulty(1);
        validWorkoutDto1.setCalorieConsumption(100.0);

        validWorkoutDto2.setName("Workout2");
        validWorkoutDto2.setDescription("Description2");
        validWorkoutDto2.setDifficulty(2);
        validWorkoutDto2.setCalorieConsumption(200.0);

        invalidWorkoutDto1.setName("Workout3");
        invalidWorkoutDto1.setDescription("Description3");
        invalidWorkoutDto1.setDifficulty(0);
        invalidWorkoutDto1.setCalorieConsumption(300.0);
    }

    @Before
    public void before() {
        if (!initialized) {
            HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dudeDto);
            ResponseEntity<DudeDto> dudeResponse = REST_TEMPLATE
                .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
            Long dudeId = dudeResponse.getBody().getId();
            exerciseDto1.setCreatorId(dudeId);
            exerciseDto2.setCreatorId(dudeId);
            validWorkoutDto1.setCreatorId(dudeId);
            validWorkoutDto2.setCreatorId(dudeId);
            invalidWorkoutDto1.setCreatorId(dudeId);
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
            WorkoutExerciseDtoIn workoutExerciseDtoIn1 = new WorkoutExerciseDtoIn();
            workoutExerciseDtoIn1.setExerciseId(exercise1Id);
            workoutExerciseDtoIn1.setExerciseVersion(exercise1Version);
            WorkoutExerciseDtoIn workoutExerciseDtoIn2 = new WorkoutExerciseDtoIn();
            workoutExerciseDtoIn2.setExerciseId(exercise2Id);
            workoutExerciseDtoIn2.setExerciseVersion(exercise2Version);
            WorkoutExerciseDtoIn[] workoutExerciseDtoIns = new WorkoutExerciseDtoIn[]{workoutExerciseDtoIn1, workoutExerciseDtoIn2};
            validWorkoutDto1.setWorkoutExercises(workoutExerciseDtoIns);
            validWorkoutDto2.setWorkoutExercises(workoutExerciseDtoIns);
            invalidWorkoutDto1.setWorkoutExercises(workoutExerciseDtoIns);

            initialized = true;
        }
    }

    @Test
    public void whenSaveOneWorkout_then201CreatedAndGetSavedWorkout() {
        HttpEntity<WorkoutDto> workoutRequest = new HttpEntity<>(validWorkoutDto1);
        ResponseEntity<WorkoutDto> workoutResponse = REST_TEMPLATE
            .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest, WorkoutDto.class);
        assertEquals(HttpStatus.CREATED, workoutResponse.getStatusCode());
        WorkoutDto responseWorkoutDto = workoutResponse.getBody();
        assertNotNull(responseWorkoutDto.getId());
        responseWorkoutDto.setId(1L);
        responseWorkoutDto.setWorkoutExercises(validWorkoutDto1.getWorkoutExercises());
        assertEquals(validWorkoutDto1, responseWorkoutDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenSaveOneInvalidWorkout_then400BadRequest() {
        HttpEntity<WorkoutDto> workoutRequest = new HttpEntity<>(invalidWorkoutDto1);
        REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest, WorkoutDto.class);
    }

    @Test
    public void whenUpdateOneWorkout_then200OkAndGetUpdatedWorkout() {
        HttpEntity<WorkoutDto> workoutRequest1 = new HttpEntity<>(validWorkoutDto1);
        ResponseEntity<WorkoutDto> workoutResponse1 = REST_TEMPLATE
            .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest1, WorkoutDto.class);
        Long savedWorkoutId = workoutResponse1.getBody().getId();
        HttpEntity<WorkoutDto> workoutRequest2 = new HttpEntity<>(validWorkoutDto2);
        ResponseEntity<WorkoutDto> workoutResponse2 = REST_TEMPLATE
            .exchange(BASE_URL + port + WORKOUT_ENDPOINT + "/" + savedWorkoutId, HttpMethod.PUT, workoutRequest2, WorkoutDto.class);
        assertEquals(HttpStatus.OK, workoutResponse2.getStatusCode());
        WorkoutDto responseWorkoutDto = workoutResponse2.getBody();
        assertEquals(savedWorkoutId, responseWorkoutDto.getId());
        assertEquals((Integer)2, responseWorkoutDto.getVersion());
        responseWorkoutDto.setId(2L);
        responseWorkoutDto.setVersion(1);
        responseWorkoutDto.setWorkoutExercises(validWorkoutDto2.getWorkoutExercises());
        assertEquals(validWorkoutDto2, responseWorkoutDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenUpdateOneWorkoutWithInvalidId_then400BadRequest() {
        HttpEntity<WorkoutDto> workoutRequest1 = new HttpEntity<>(validWorkoutDto1);
        ResponseEntity<WorkoutDto> workoutResponse1 = REST_TEMPLATE
            .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest1, WorkoutDto.class);
        Long savedWorkoutId = workoutResponse1.getBody().getId();
        HttpEntity<WorkoutDto> workoutRequest2 = new HttpEntity<>(validWorkoutDto2);
        REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT + "/" + savedWorkoutId+1, HttpMethod.PUT, workoutRequest2, WorkoutDto.class);
    }

    @Test()
    public void whenSaveTwoAndFilterByCharacter2_thenGetOneResult(){
        validWorkoutDto1.setId(1L);
        validWorkoutDto2.setId(2L);
        HttpEntity<WorkoutDto> courseRequest1 = new HttpEntity<>(validWorkoutDto1);
        HttpEntity<WorkoutDto> courseRequest2 = new HttpEntity<>(validWorkoutDto2);
        REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, courseRequest1, WorkoutDto.class);
        REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, courseRequest2, WorkoutDto.class);
        ResponseEntity<WorkoutDto[]> response3 = REST_TEMPLATE
            .exchange(BASE_URL + port + WORKOUT_ENDPOINT +"/filtered"+"?filter=2", HttpMethod.GET, null, WorkoutDto[].class);
        assertEquals(1, response3.getBody() == null ? 0 : response3.getBody().length);
    }

}
