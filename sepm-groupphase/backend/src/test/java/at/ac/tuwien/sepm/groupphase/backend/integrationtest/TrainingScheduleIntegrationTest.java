package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.MuscleGroup;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

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
    private static final DudeDto advancedDudeDto = new DudeDto();
    private static final TrainingScheduleDto trainingScheduleDto = new TrainingScheduleDto();
    private static final TrainingScheduleDto trainingScheduleDto2 = new TrainingScheduleDto();
    private static final TrainingScheduleDto trainingScheduleDtoForAdaptiveChange = new TrainingScheduleDto();
    private static final TrainingScheduleDto trainingScheduleMinReps = new TrainingScheduleDto();
    private static final TrainingScheduleDto trainingScheduleMaxReps = new TrainingScheduleDto();
    private static final TrainingScheduleDto trainingScheduleMinSets = new TrainingScheduleDto();
    private static final TrainingScheduleDto trainingScheduleMaxSets = new TrainingScheduleDto();
    private static final TrainingScheduleDto invalidTrainingScheduleDto = new TrainingScheduleDto();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutDtoIn1 = new TrainingScheduleWorkoutDtoIn();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutDtoIn2 = new TrainingScheduleWorkoutDtoIn();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutDtoIn3 = new TrainingScheduleWorkoutDtoIn();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutMinReps = new TrainingScheduleWorkoutDtoIn();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutMaxReps = new TrainingScheduleWorkoutDtoIn();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutMinSets = new TrainingScheduleWorkoutDtoIn();
    private static final TrainingScheduleWorkoutDtoIn trainingScheduleWorkoutMaxSets = new TrainingScheduleWorkoutDtoIn();
    private static final WorkoutDto workoutDto1 = new WorkoutDto();
    private static final WorkoutDto workoutDto2 = new WorkoutDto();
    private static final WorkoutDto workoutMinReps = new WorkoutDto();
    private static final WorkoutDto workoutMaxReps = new WorkoutDto();
    private static final WorkoutDto workoutMinSets = new WorkoutDto();
    private static final WorkoutDto workoutMaxSets = new WorkoutDto();
    private static final WorkoutExerciseDtoIn workoutExerciseDtoIn1 = new WorkoutExerciseDtoIn();
    private static final WorkoutExerciseDtoIn workoutExerciseDtoIn2 = new WorkoutExerciseDtoIn();
    private static final WorkoutExerciseDtoIn workoutExerciseDtoMinReps = new WorkoutExerciseDtoIn();
    private static final WorkoutExerciseDtoIn workoutExerciseDtoMaxReps = new WorkoutExerciseDtoIn();
    private static final WorkoutExerciseDtoIn workoutExerciseDtoMinSets = new WorkoutExerciseDtoIn();
    private static final WorkoutExerciseDtoIn workoutExerciseDtoMaxSets = new WorkoutExerciseDtoIn();
    private static final ExerciseDto exerciseDto1 = new ExerciseDto();
    private static final ExerciseDto exerciseDto2 = new ExerciseDto();
    private static final ActiveTrainingScheduleDto activeTsDto = new ActiveTrainingScheduleDto();
    private static final ActiveTrainingScheduleDto activeTsForAdaptivChangeDto = new ActiveTrainingScheduleDto();

    private static boolean initialized = false;

    @LocalServerPort
    private int port;

    @Autowired
    ITrainingScheduleRepository trainingScheduleRepository;

    @Autowired
    IActiveTrainingScheduleRepository activeTrainingScheduleRepository;

    @BeforeClass
    public static void beforeClass() {
        dudeDto.setName("DudeTS1");
        dudeDto.setPassword("1234567890");
        dudeDto.setEmail("test@email.com");
        dudeDto.setSex(Sex.Male);
        dudeDto.setSelfAssessment(1);
        dudeDto.setBirthday(LocalDate.of(2000, 1, 1));
        dudeDto.setHeight(150.0);
        dudeDto.setWeight(50.0);

        advancedDudeDto.setName("AdvancedDude");
        advancedDudeDto.setPassword("1234567890");
        advancedDudeDto.setEmail("test@email.com");
        advancedDudeDto.setSex(Sex.Female);
        advancedDudeDto.setSelfAssessment(2);
        advancedDudeDto.setBirthday(LocalDate.of(1999, 5, 10));
        advancedDudeDto.setHeight(180.0);
        advancedDudeDto.setWeight(70.0);

        exerciseDto1.setName("Exercise1");
        exerciseDto1.setDescription("Description1");
        exerciseDto1.setEquipment("Equipment1");
        exerciseDto1.setMuscleGroup(MuscleGroup.Chest);
        exerciseDto1.setCategory(Category.Strength);

        exerciseDto2.setName("Exercise2");
        exerciseDto2.setDescription("Description2");
        exerciseDto2.setEquipment("Equipment2");
        exerciseDto2.setMuscleGroup(MuscleGroup.Arms);
        exerciseDto2.setCategory(Category.Endurance);

        workoutDto1.setName("Workout1");
        workoutDto1.setDescription("Description1");
        workoutDto1.setDifficulty(1);
        workoutDto1.setCalorieConsumption(100.0);

        workoutDto2.setName("Workout2");
        workoutDto2.setDescription("Description2");
        workoutDto2.setDifficulty(2);
        workoutDto2.setCalorieConsumption(200.0);

        workoutMinReps.setName("WorkoutMinReps");
        workoutMinReps.setDescription("DescriptionWorkoutMinReps");
        workoutMinReps.setDifficulty(2);
        workoutMinReps.setCalorieConsumption(200.0);

        workoutMaxReps.setName("WorkoutMaxReps");
        workoutMaxReps.setDescription("DescriptionWorkoutMaxReps");
        workoutMaxReps.setDifficulty(2);
        workoutMaxReps.setCalorieConsumption(300.0);

        workoutMinSets.setName("WorkoutMinSets");
        workoutMinSets.setDescription("DescriptionWorkoutMinSets");
        workoutMinSets.setDifficulty(2);
        workoutMinSets.setCalorieConsumption(400.0);

        workoutMaxSets.setName("WorkoutMaxSets");
        workoutMaxSets.setDescription("DescriptionWorkoutMaxSets");
        workoutMaxSets.setDifficulty(2);
        workoutMaxSets.setCalorieConsumption(500.0);

        workoutExerciseDtoIn1.setExDuration(25);
        workoutExerciseDtoIn1.setRepetitions(8);
        workoutExerciseDtoIn1.setSets(3);

        workoutExerciseDtoIn2.setExDuration(90);
        workoutExerciseDtoIn2.setRepetitions(20);
        workoutExerciseDtoIn2.setSets(4);

        workoutExerciseDtoMinReps.setExDuration(30);
        workoutExerciseDtoMinReps.setRepetitions(1);
        workoutExerciseDtoMinReps.setSets(15);

        workoutExerciseDtoMaxReps.setExDuration(90);
        workoutExerciseDtoMaxReps.setRepetitions(200);
        workoutExerciseDtoMaxReps.setSets(5);

        workoutExerciseDtoMinSets.setExDuration(40);
        workoutExerciseDtoMinSets.setRepetitions(20);
        workoutExerciseDtoMinSets.setSets(1);

        workoutExerciseDtoMaxSets.setExDuration(160);
        workoutExerciseDtoMaxSets.setRepetitions(40);
        workoutExerciseDtoMaxSets.setSets(20);

        trainingScheduleWorkoutDtoIn1.setDay(1);
        trainingScheduleWorkoutDtoIn2.setDay(2);
        trainingScheduleWorkoutDtoIn3.setDay(3);
        trainingScheduleWorkoutMinSets.setDay(1);
        trainingScheduleWorkoutMaxSets.setDay(1);
        trainingScheduleWorkoutMinReps.setDay(1);
        trainingScheduleWorkoutMaxReps.setDay(1);

        trainingScheduleDto.setName("TrainingsSchedule1");
        trainingScheduleDto.setDescription("Description1");
        trainingScheduleDto.setDifficulty(1);
        trainingScheduleDto.setIntervalLength(3);
        trainingScheduleDto.setRating(0.0);

        trainingScheduleDto2.setName("TrainingsSchedule2");
        trainingScheduleDto2.setDescription("Description2");
        trainingScheduleDto2.setDifficulty(2);
        trainingScheduleDto2.setIntervalLength(2);
        trainingScheduleDto2.setRating(0.0);

        trainingScheduleDtoForAdaptiveChange.setName("TrainingsScheduleAdaptive");
        trainingScheduleDtoForAdaptiveChange.setDescription("DescriptionAdaptive");
        trainingScheduleDtoForAdaptiveChange.setDifficulty(2);
        trainingScheduleDtoForAdaptiveChange.setIntervalLength(2);
        trainingScheduleDtoForAdaptiveChange.setRating(5.0);
        trainingScheduleDtoForAdaptiveChange.setIsPrivate(true);

        trainingScheduleMinReps.setName("TrainingsScheduleMinReps");
        trainingScheduleMinReps.setDescription("DescriptionMinReps");
        trainingScheduleMinReps.setDifficulty(2);
        trainingScheduleMinReps.setIntervalLength(2);
        trainingScheduleMinReps.setRating(0.0);
        trainingScheduleMinReps.setIsPrivate(true);

        trainingScheduleMaxReps.setName("TrainingsScheduleMaxReps");
        trainingScheduleMaxReps.setDescription("DescriptionMaxReps");
        trainingScheduleMaxReps.setDifficulty(2);
        trainingScheduleMaxReps.setIntervalLength(2);
        trainingScheduleMaxReps.setRating(0.0);
        trainingScheduleMaxReps.setIsPrivate(true);

        trainingScheduleMinSets.setName("TrainingsScheduleMinSets");
        trainingScheduleMinSets.setDescription("DescriptionMinSets");
        trainingScheduleMinSets.setDifficulty(2);
        trainingScheduleMinSets.setIntervalLength(2);
        trainingScheduleMinSets.setRating(0.0);
        trainingScheduleMinSets.setIsPrivate(true);

        trainingScheduleMaxSets.setName("TrainingsScheduleMaxSets");
        trainingScheduleMaxSets.setDescription("DescriptionMaxSets");
        trainingScheduleMaxSets.setDifficulty(2);
        trainingScheduleMaxSets.setIntervalLength(2);
        trainingScheduleMaxSets.setRating(0.0);
        trainingScheduleMaxSets.setIsPrivate(true);

        activeTsDto.setStartDate(LocalDate.now());
        activeTsDto.setIntervalRepetitions(2);
        activeTsDto.setAdaptive(false);

        activeTsForAdaptivChangeDto.setStartDate(LocalDate.now().minusDays(3));
        activeTsForAdaptivChangeDto.setIntervalRepetitions(3);
        activeTsForAdaptivChangeDto.setAdaptive(true);

        invalidTrainingScheduleDto.setName("TrainingScheduleInvalid");
    }


    @Before
    public void before() {
        if (!initialized) {
            HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dudeDto);
            ResponseEntity<DudeDto> response = REST_TEMPLATE
                .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
            Long dudeId = response.getBody().getId();

            HttpEntity<DudeDto> advancedDudeRequest = new HttpEntity<>(advancedDudeDto);
            ResponseEntity<DudeDto> advancedDudeResponse = REST_TEMPLATE
                .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, advancedDudeRequest, DudeDto.class);
            Long advancedDudeId = advancedDudeResponse.getBody().getId();

            System.out.println("Created Dude: " + response.getBody());

            exerciseDto1.setCreatorId(dudeId);
            exerciseDto2.setCreatorId(advancedDudeId);
            trainingScheduleDto.setCreatorId(dudeId);
            trainingScheduleDto2.setCreatorId(advancedDudeId);
            trainingScheduleMaxReps.setCreatorId(dudeId);
            trainingScheduleMinReps.setCreatorId(dudeId);
            trainingScheduleMaxSets.setCreatorId(dudeId);
            trainingScheduleMinSets.setCreatorId(dudeId);
            trainingScheduleDtoForAdaptiveChange.setCreatorId(dudeId);

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
            workoutExerciseDtoMaxReps.setExerciseId(exercise1Id);
            workoutExerciseDtoMaxReps.setExerciseVersion(exercise1Version);
            workoutExerciseDtoMinReps.setExerciseId(exercise2Id);
            workoutExerciseDtoMinReps.setExerciseVersion(exercise2Version);
            workoutExerciseDtoMaxSets.setExerciseId(exercise1Id);
            workoutExerciseDtoMaxSets.setExerciseVersion(exercise1Version);
            workoutExerciseDtoMinSets.setExerciseId(exercise1Id);
            workoutExerciseDtoMinSets.setExerciseVersion(exercise1Version);

            WorkoutExerciseDtoIn[] workoutExerciseDtoIns = new WorkoutExerciseDtoIn[]{workoutExerciseDtoIn1, workoutExerciseDtoIn2};
            workoutDto1.setWorkoutExercises(workoutExerciseDtoIns);
            workoutDto2.setWorkoutExercises(workoutExerciseDtoIns);
            workoutMaxReps.setWorkoutExercises(new WorkoutExerciseDtoIn[]{workoutExerciseDtoMaxReps});
            workoutMinReps.setWorkoutExercises(new WorkoutExerciseDtoIn[]{workoutExerciseDtoMinReps});
            workoutMaxSets.setWorkoutExercises(new WorkoutExerciseDtoIn[]{workoutExerciseDtoMaxSets});
            workoutMinSets.setWorkoutExercises(new WorkoutExerciseDtoIn[]{workoutExerciseDtoMinSets});
            workoutDto1.setCreatorId(dudeId);
            workoutDto2.setCreatorId(dudeId);
            workoutMaxReps.setCreatorId(dudeId);
            workoutMinReps.setCreatorId(dudeId);
            workoutMaxSets.setCreatorId(dudeId);
            workoutMinSets.setCreatorId(dudeId);

            HttpEntity<WorkoutDto> workoutRequest = new HttpEntity<>(workoutDto1);
            ResponseEntity<WorkoutDto> workoutResponse1 = REST_TEMPLATE
                .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest, WorkoutDto.class);
            HttpEntity<WorkoutDto> workoutRequest2 = new HttpEntity<>(workoutDto2);
            ResponseEntity<WorkoutDto> workoutResponse2 = REST_TEMPLATE
                .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest2, WorkoutDto.class);
            HttpEntity<WorkoutDto> workoutMinRepsRequest = new HttpEntity<>(workoutMinReps);
            ResponseEntity<WorkoutDto> workoutMinRepsResponse = REST_TEMPLATE
                .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutMinRepsRequest, WorkoutDto.class);
            HttpEntity<WorkoutDto> workoutMaxRepsRequest = new HttpEntity<>(workoutMaxReps);
            ResponseEntity<WorkoutDto> workoutMaxRepsResponse = REST_TEMPLATE
                .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutMaxRepsRequest, WorkoutDto.class);
            HttpEntity<WorkoutDto> workoutMaxSetsRequest = new HttpEntity<>(workoutMaxSets);
            ResponseEntity<WorkoutDto> workoutMaxSetsResponse = REST_TEMPLATE
                .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutMaxSetsRequest, WorkoutDto.class);
            HttpEntity<WorkoutDto> workoutMinSetsRequest = new HttpEntity<>(workoutMinSets);
            ResponseEntity<WorkoutDto> workoutMinSetsResponse = REST_TEMPLATE
                .exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutMinSetsRequest, WorkoutDto.class);
            trainingScheduleWorkoutDtoIn1.setWorkoutId(workoutResponse1.getBody().getId());
            trainingScheduleWorkoutDtoIn1.setWorkoutVersion(workoutResponse1.getBody().getVersion());
            trainingScheduleWorkoutDtoIn2.setWorkoutId(workoutResponse2.getBody().getId());
            trainingScheduleWorkoutDtoIn2.setWorkoutVersion(workoutResponse2.getBody().getVersion());
            trainingScheduleWorkoutMinReps.setWorkoutId(workoutMinRepsResponse.getBody().getId());
            trainingScheduleWorkoutMinReps.setWorkoutVersion(workoutMinRepsResponse.getBody().getVersion());
            trainingScheduleWorkoutMaxReps.setWorkoutId(workoutMaxRepsResponse.getBody().getId());
            trainingScheduleWorkoutMaxReps.setWorkoutVersion(workoutMaxRepsResponse.getBody().getVersion());
            trainingScheduleWorkoutMinSets.setWorkoutId(workoutMinSetsResponse.getBody().getId());
            trainingScheduleWorkoutMinSets.setWorkoutVersion(workoutMinSetsResponse.getBody().getVersion());
            trainingScheduleWorkoutMaxSets.setWorkoutId(workoutMaxSetsResponse.getBody().getId());
            trainingScheduleWorkoutMaxSets.setWorkoutVersion(workoutMaxSetsResponse.getBody().getVersion());

            TrainingScheduleWorkoutDtoIn[] trainingScheduleWorkouts = new TrainingScheduleWorkoutDtoIn[]{trainingScheduleWorkoutDtoIn1, trainingScheduleWorkoutDtoIn2};
            trainingScheduleDto.setTrainingScheduleWorkouts(trainingScheduleWorkouts);
            trainingScheduleDto2.setTrainingScheduleWorkouts(new TrainingScheduleWorkoutDtoIn[]{trainingScheduleWorkoutDtoIn1, trainingScheduleWorkoutDtoIn2 });
            trainingScheduleMinSets.setTrainingScheduleWorkouts(new TrainingScheduleWorkoutDtoIn[]{trainingScheduleWorkoutMinSets});
            trainingScheduleMaxSets.setTrainingScheduleWorkouts(new TrainingScheduleWorkoutDtoIn[]{trainingScheduleWorkoutMaxSets});
            trainingScheduleMinReps.setTrainingScheduleWorkouts(new TrainingScheduleWorkoutDtoIn[]{trainingScheduleWorkoutMinReps});
            trainingScheduleMaxReps.setTrainingScheduleWorkouts(new TrainingScheduleWorkoutDtoIn[]{trainingScheduleWorkoutMaxReps});

            trainingScheduleDtoForAdaptiveChange.setTrainingScheduleWorkouts(trainingScheduleWorkouts);

            activeTsForAdaptivChangeDto.setDudeId(dudeId);
            activeTsDto.setDudeId(dudeId);

            initialized = true;
        }
    }

    @After
    public void clearRepository(){
        activeTrainingScheduleRepository.deleteAll();
    }

    private ResponseEntity<TrainingScheduleDto> postTrainingSchedule(TrainingScheduleDto trainingSchedule) {
        HttpEntity<TrainingScheduleDto> trainingScheduleRequest = new HttpEntity<>(trainingSchedule);
        ResponseEntity<TrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT, HttpMethod.POST, trainingScheduleRequest, TrainingScheduleDto.class);
        return response;
    }

    private ResponseEntity<ActiveTrainingScheduleDto> postTsAndActiveTs() {
        ResponseEntity<TrainingScheduleDto> tsResponse = postTrainingSchedule(trainingScheduleDto);
        activeTsDto.setTrainingScheduleId(tsResponse.getBody().getId());
        activeTsDto.setTrainingScheduleVersion(tsResponse.getBody().getVersion());
        HttpEntity<ActiveTrainingScheduleDto> activeTsRequest = new HttpEntity<>(activeTsDto);
        ResponseEntity<ActiveTrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active", HttpMethod.POST, activeTsRequest, ActiveTrainingScheduleDto.class);
        return response;
    }

     @Test
    public void whenSaveTrainingScheduleManually_then201CreatedAndGetSavedTrainingSchedule() {
        HttpEntity<TrainingScheduleDto> trainingScheduleResponse = postTrainingSchedule(trainingScheduleDto);
        assertEquals(HttpStatus.CREATED, ((ResponseEntity<TrainingScheduleDto>) trainingScheduleResponse).getStatusCode());
        TrainingScheduleDto responseTrainingScheduleDto = trainingScheduleResponse.getBody();
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

    @Test
    public void givenTrainingSchedule_whenFindTrainingScheduleByIdAndVersion_thenGetTrainingScheduleAndStatusOK(){
        TrainingScheduleDto t = postTrainingSchedule(trainingScheduleDto).getBody();
        ResponseEntity<TrainingScheduleDto> ts = REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + t.getId() + "/1", HttpMethod.GET, null, TrainingScheduleDto.class);
        TrainingScheduleDto foundTrainingSchedule = ts.getBody();
        assertEquals(foundTrainingSchedule.getName(), t.getName());
        assertEquals(foundTrainingSchedule.getDescription(), t.getDescription());
        assertEquals(foundTrainingSchedule.getDifficulty(), t.getDifficulty());
        assertEquals(foundTrainingSchedule.getIntervalLength(), t.getIntervalLength());
        assertEquals(foundTrainingSchedule.getRating(), t.getRating());
        assertEquals(foundTrainingSchedule.getCreatorId(), t.getCreatorId());
        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> tsWa = REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + ts.getBody().getId() + "/" + ts.getBody().getVersion()+ "/workouts", HttpMethod.GET, null, TrainingScheduleWorkoutDtoOut[].class);
        assertEquals(tsWa.getBody().length, trainingScheduleDto.getTrainingScheduleWorkouts().length);
        assertEquals(HttpStatus.OK, ts.getStatusCode());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void whenFindTrainingScheduleByIdAndVersion_ifTrainingScheduleNotFound_thenHttpClientErrorException(){
        REST_TEMPLATE.getForObject(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/100/100", TrainingScheduleDto.class);
    }

    @Test
    public void givenTwoTrainingSchedules_whenFindTrainingSchedulesByName_thenGetTrainingSchedules(){
        TrainingScheduleDto tsDto = new TrainingScheduleDto();
        tsDto.setName("TrainingsSchedule1_SpecialName");
        tsDto.setDescription(trainingScheduleDto.getDescription());
        tsDto.setDifficulty(trainingScheduleDto.getDifficulty());
        tsDto.setIntervalLength(trainingScheduleDto.getIntervalLength());
        tsDto.setRating(trainingScheduleDto.getRating());
        tsDto.setCreatorId(trainingScheduleDto.getCreatorId());
        tsDto.setTrainingScheduleWorkouts(trainingScheduleDto.getTrainingScheduleWorkouts());

        TrainingScheduleDto a = postTrainingSchedule(tsDto).getBody();
        postTrainingSchedule(trainingScheduleDto2).getBody();
        TrainingScheduleDto[] foundTrainingSchedules = REST_TEMPLATE.getForObject(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/1?name=" + a.getName(), TrainingScheduleDto[].class);
        for (TrainingScheduleDto tDto : foundTrainingSchedules) {
            assertEquals(tDto.getName(), a.getName());
        }
        assertEquals(1, foundTrainingSchedules.length);
    }

    @Test
    public void whenFindTrainingSchedulesByName_whenNameNotFound_thenGetEmptyArrayList(){
        ResponseEntity<List<TrainingScheduleDto>> foundTrainingSchedules = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/1?name=TestNameThatDoesntExist", HttpMethod.GET, null, new ParameterizedTypeReference<List<TrainingScheduleDto>>() {});
        assertEquals(0, foundTrainingSchedules.getBody() != null? foundTrainingSchedules.getBody().size() : 0);
    }

    @Test
    public void givenDudeAndTs_whenDudeBookmarksTs_thenTsInBookmarkedTss() {
        ResponseEntity<TrainingScheduleDto> tsResponse = postTrainingSchedule(trainingScheduleDto);
        Long tsId = tsResponse.getBody().getId();
        Long dudeId = tsResponse.getBody().getCreatorId();

        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/bookmark/" + dudeId + "/" + tsId + "/1", HttpMethod.PUT, null, Void.class);

        ResponseEntity<TrainingScheduleDto[]> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + dudeId + "/bookmarks/trainingSchedules", HttpMethod.GET, null, TrainingScheduleDto[].class);
        assertEquals(1, response.getBody().length);
        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/bookmark/" + dudeId + "/" + tsId + "/1", HttpMethod.DELETE, null, Void.class);
        trainingScheduleRepository.delete(tsId);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void givenDude_whenDudeBookmarksNonExistingTs_then400BadRequest() {
        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/bookmark/" + 1 + "/" + 0 + "/1", HttpMethod.PUT, null, Void.class);
    }

    @Test
    public void whenSaveOneActiveTs_then201CreatedAndReturnThisActiveTs() {
        ResponseEntity<ActiveTrainingScheduleDto> activeTsResponse = postTsAndActiveTs();
        assertEquals(HttpStatus.CREATED, activeTsResponse.getStatusCode());
        ActiveTrainingScheduleDto activeTsResponseBody = activeTsResponse.getBody();
        activeTsResponseBody.setId(null);
        assertEquals(activeTsDto, activeTsResponseBody);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenSaveOneInvalidActiveTs_then400BadRequest() {
        ResponseEntity<TrainingScheduleDto> tsResponse = postTrainingSchedule(trainingScheduleDto);

        ActiveTrainingScheduleDto invalidActiveTsDto = new ActiveTrainingScheduleDto();
        invalidActiveTsDto.setTrainingScheduleId(tsResponse.getBody().getId());
        invalidActiveTsDto.setTrainingScheduleVersion(tsResponse.getBody().getVersion());
        invalidActiveTsDto.setDudeId(activeTsDto.getDudeId());
        invalidActiveTsDto.setStartDate(activeTsDto.getStartDate());
        invalidActiveTsDto.setAdaptive(activeTsDto.getAdaptive());
        invalidActiveTsDto.setIntervalRepetitions(0);

        HttpEntity<ActiveTrainingScheduleDto> invalidActiveTsRequest = new HttpEntity<>(invalidActiveTsDto);
        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active", HttpMethod.POST, invalidActiveTsRequest, ActiveTrainingScheduleDto.class);
    }

    @Test
    public void givenOneActiveTS_whenDeleteThisActiveTs_then200Ok() {
        postTsAndActiveTs();
        assertEquals(HttpStatus.OK,
            REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active/" + activeTsDto.getDudeId(),
                HttpMethod.DELETE, null, Void.class).getStatusCode());
    }

    @Test
    public void givenOneActiveTS_whenGetActiveTsOfDude_then200OkAndReturnThatActiveTs() {
        postTsAndActiveTs();
        ResponseEntity<ActiveTrainingScheduleDto> activeDudeTsResponse = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsDto.getDudeId() + "/activeTrainingSchedule",
            HttpMethod.GET, null, ActiveTrainingScheduleDto.class);
        assertEquals(HttpStatus.OK, activeDudeTsResponse.getStatusCode());
        ActiveTrainingScheduleDto activeDudeTsResponseBody = activeDudeTsResponse.getBody();
        activeDudeTsResponseBody.setId(null);
        assertEquals(activeTsDto, activeDudeTsResponseBody);
    }

    @Test
    public void givenOneActiveTS_whenGetExerciseDonesOfDude_then200OkAndReturnThoseExerciseDones() {
        ResponseEntity<ActiveTrainingScheduleDto> activeTsResponse = postTsAndActiveTs();
        ResponseEntity<ExerciseDoneDto[]> exerciseDoneResponse = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsDto.getDudeId() + "/activeTrainingSchedule/done",
                HttpMethod.GET, null, ExerciseDoneDto[].class);
        assertEquals(HttpStatus.OK, exerciseDoneResponse.getStatusCode());
        ExerciseDoneDto[] exerciseDoneResponseBody = exerciseDoneResponse.getBody();
        assertEquals(8, exerciseDoneResponseBody.length);
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void whenGetNonExistingActiveTsOfDude_then404NotFound() {
        REST_TEMPLATE.exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + trainingScheduleDto.getCreatorId() + "/activeTrainingSchedule",
            HttpMethod.GET, null, ActiveTrainingScheduleDto.class);
    }
    @Test
    public void whenGetAllWorkoutsByTrainingScheduleIdAndVersion_thenGetWorkoutsAndStatusOK(){
        TrainingScheduleDto t = postTrainingSchedule(trainingScheduleDto).getBody();
        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkouts = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + t.getId() + "/" + t.getVersion() + "/workouts", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});
        assertEquals(HttpStatus.OK, foundTrainingScheduleWorkouts.getStatusCode());
        assertEquals(trainingScheduleDto.getTrainingScheduleWorkouts().length, (foundTrainingScheduleWorkouts.getBody() != null? foundTrainingScheduleWorkouts.getBody().length : 0));
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenGetAllWorkoutsByTrainingScheduleIdAndVersion_withInvalidIdAndVersion_thenStatusBAD_REQUEST(){
        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkouts = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/100/100/workouts", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});
    }

    @Test
    public void whenGetAllWorkoutsByTrainingScheduleIdAndVersionAndDay_thenGetWorkoutsAndStatusOK(){
        TrainingScheduleDto t = postTrainingSchedule(trainingScheduleDto).getBody();
        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkouts = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + t.getId() + "/" + t.getVersion() + "/workouts/1", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});
        assertEquals(1, foundTrainingScheduleWorkouts.getBody() != null? foundTrainingScheduleWorkouts.getBody().length : 0);
        assertEquals(HttpStatus.OK, foundTrainingScheduleWorkouts.getStatusCode());
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenGetAllWorkoutsByTrainingScheduleIdAndVersionAndDay_withInvalidIdAndVersion_thenStatusBAD_REQUEST(){
        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkouts = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/100/100/workouts/1", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});
    }

    @Test
    public void whenGetAllWorkoutsByTrainingScheduleIdAndVersionAndDay_withInvalidDay_thenResponseArrayEmpty(){
        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkouts = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/1/1/workouts/100", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});
        assertEquals(0, foundTrainingScheduleWorkouts.getBody().length);
    }

    @Test
    public void getActiveAdaptiveTrainingScheduleWhenChangeAdaptively_thenApplyChangeAndStatusOK(){
        ResponseEntity<TrainingScheduleDto> tsResponse = postTrainingSchedule(trainingScheduleDtoForAdaptiveChange);
        activeTsForAdaptivChangeDto.setTrainingScheduleId(tsResponse.getBody().getId());
        activeTsForAdaptivChangeDto.setTrainingScheduleVersion(tsResponse.getBody().getVersion());



        HttpEntity<ActiveTrainingScheduleDto> activeTsRequest = new HttpEntity<>(activeTsForAdaptivChangeDto);
        ResponseEntity<ActiveTrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active", HttpMethod.POST, activeTsRequest, ActiveTrainingScheduleDto.class);

        ResponseEntity<ActiveTrainingScheduleDto> adaptedSchedule = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsForAdaptivChangeDto.getDudeId() + "/activeTrainingSchedule", HttpMethod.GET, null, ActiveTrainingScheduleDto.class);
        assertNotEquals(response.getBody().getTrainingScheduleId(), adaptedSchedule.getBody().getTrainingScheduleId());
        assertEquals(HttpStatus.OK, adaptedSchedule.getStatusCode());

        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkoutsAdaptive = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + adaptedSchedule.getBody().getTrainingScheduleId() + "/1/workouts/copyTs", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});

        List<WorkoutExerciseDtoOut> foundWorkoutExercises = new ArrayList<>();
        for (TrainingScheduleWorkoutDtoOut t: foundTrainingScheduleWorkoutsAdaptive.getBody()) {
            assertNotEquals(workoutDto1.getId(), t.getId());
            assertNotEquals(workoutDto2.getId(), t.getId());
            assertNotEquals(workoutDto1.getCalorieConsumption(), t.getCalorieConsumption());
            assertNotEquals(workoutDto2.getCalorieConsumption(), t.getCalorieConsumption());
            foundWorkoutExercises.addAll(REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT + "/" + t.getId() + "/" + t.getVersion() + "/exercises", HttpMethod.GET, null, new ParameterizedTypeReference<List<WorkoutExerciseDtoOut>>(){}).getBody());
        }
        for (WorkoutExerciseDtoOut w: foundWorkoutExercises) {
            assertNotEquals((workoutExerciseDtoIn1.getRepetitions() * workoutExerciseDtoIn1.getSets()), (w.getRepetitions() * w.getSets()));
            assertNotEquals((workoutExerciseDtoIn2.getRepetitions() * workoutExerciseDtoIn2.getSets()), (w.getRepetitions() * w.getSets()));
            assertNotEquals(workoutExerciseDtoIn1.getExDuration(), w.getExDuration());
            assertNotEquals(workoutExerciseDtoIn2.getExDuration(), w.getExDuration());
        }

     }

    @Test
    public void whenMaximumNumberOfRepetitionsForWaExAndChangeAdaptivelyIncrease_thenIncreaseSetsAndNumberOfRepetitionsWontGetHigherThanMaximum(){
        ResponseEntity<TrainingScheduleDto> tsResponse = postTrainingSchedule(trainingScheduleMaxReps);
        activeTsForAdaptivChangeDto.setTrainingScheduleId(tsResponse.getBody().getId());
        activeTsForAdaptivChangeDto.setTrainingScheduleVersion(tsResponse.getBody().getVersion());

        HttpEntity<ActiveTrainingScheduleDto> activeTsRequest = new HttpEntity<>(activeTsForAdaptivChangeDto);
        ResponseEntity<ActiveTrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active", HttpMethod.POST, activeTsRequest, ActiveTrainingScheduleDto.class);

        ResponseEntity<ExerciseDoneDto[]> exerciseDoneResponse = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsForAdaptivChangeDto.getDudeId() + "/activeTrainingSchedule/done",
                HttpMethod.GET, null, ExerciseDoneDto[].class);
        HttpEntity<ExerciseDoneDto[]> exercisesDoneRequest = new HttpEntity<>(exerciseDoneResponse.getBody());

        exerciseDoneResponse.getBody()[0].setDone(true);

        ResponseEntity markExercises = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active/done",
                HttpMethod.PUT, exercisesDoneRequest, ExerciseDoneDto[].class);

        ResponseEntity<ActiveTrainingScheduleDto> adaptedSchedule = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsForAdaptivChangeDto.getDudeId() + "/activeTrainingSchedule", HttpMethod.GET, null, ActiveTrainingScheduleDto.class);

        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkoutsAdaptive = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + adaptedSchedule.getBody().getTrainingScheduleId() + "/1/workouts/copyTs", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});

        List<WorkoutExerciseDtoOut> foundWorkoutExercises = new ArrayList<>();
        for (TrainingScheduleWorkoutDtoOut t: foundTrainingScheduleWorkoutsAdaptive.getBody()) {
            assertNotEquals(workoutMaxReps.getId(), t.getId());
            assertNotEquals(workoutMaxReps.getCalorieConsumption(), t.getCalorieConsumption());
            foundWorkoutExercises.addAll(REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT + "/" + t.getId() + "/" + t.getVersion() + "/exercises", HttpMethod.GET, null, new ParameterizedTypeReference<List<WorkoutExerciseDtoOut>>(){}).getBody());
        }
        for (WorkoutExerciseDtoOut w: foundWorkoutExercises) {
            assertTrue(w.getRepetitions() <= 200);
        }
    }

    @Test
    public void whenMaximumNumberOfSetsForWaExAndChangeAdaptivelyIncrease_thenNumberOfSetsWontBeIncreased(){
        ResponseEntity<TrainingScheduleDto> tsResponse = postTrainingSchedule(trainingScheduleMaxSets);
        activeTsForAdaptivChangeDto.setTrainingScheduleId(tsResponse.getBody().getId());
        activeTsForAdaptivChangeDto.setTrainingScheduleVersion(tsResponse.getBody().getVersion());

        HttpEntity<ActiveTrainingScheduleDto> activeTsRequest = new HttpEntity<>(activeTsForAdaptivChangeDto);
        ResponseEntity<ActiveTrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active", HttpMethod.POST, activeTsRequest, ActiveTrainingScheduleDto.class);

        ResponseEntity<ExerciseDoneDto[]> exerciseDoneResponse = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsForAdaptivChangeDto.getDudeId() + "/activeTrainingSchedule/done",
                HttpMethod.GET, null, ExerciseDoneDto[].class);
        HttpEntity<ExerciseDoneDto[]> exercisesDoneRequest = new HttpEntity<>(exerciseDoneResponse.getBody());

        exerciseDoneResponse.getBody()[0].setDone(true);

        ResponseEntity markExercises = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active/done",
                HttpMethod.PUT, exercisesDoneRequest, ExerciseDoneDto[].class);

        ResponseEntity<ActiveTrainingScheduleDto> adaptedSchedule = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsForAdaptivChangeDto.getDudeId() + "/activeTrainingSchedule", HttpMethod.GET, null, ActiveTrainingScheduleDto.class);

        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkoutsAdaptive = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + adaptedSchedule.getBody().getTrainingScheduleId() + "/1/workouts/copyTs", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});

        List<WorkoutExerciseDtoOut> foundWorkoutExercises = new ArrayList<>();
        for (TrainingScheduleWorkoutDtoOut t: foundTrainingScheduleWorkoutsAdaptive.getBody()) {
            assertNotEquals(workoutMaxSets.getId(), t.getId());
            assertNotEquals(workoutMaxSets.getCalorieConsumption(), t.getCalorieConsumption());
            foundWorkoutExercises.addAll(REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT + "/" + t.getId() + "/" + t.getVersion() + "/exercises", HttpMethod.GET, null, new ParameterizedTypeReference<List<WorkoutExerciseDtoOut>>(){}).getBody());
        }
        for (WorkoutExerciseDtoOut w: foundWorkoutExercises) {
            assertTrue(w.getSets() <= 20);
        }
    }

    @Test
    public void whenMinimumNumberOfRepetitionsForWaExAndChangeAdaptivelyDecrease_thenDecreaseSetsAndNumberOfRepetitionsWontGetLowerThanMinimum(){
        ResponseEntity<TrainingScheduleDto> tsResponse = postTrainingSchedule(trainingScheduleMinReps);
        activeTsForAdaptivChangeDto.setTrainingScheduleId(tsResponse.getBody().getId());
        activeTsForAdaptivChangeDto.setTrainingScheduleVersion(tsResponse.getBody().getVersion());

        HttpEntity<ActiveTrainingScheduleDto> activeTsRequest = new HttpEntity<>(activeTsForAdaptivChangeDto);
        ResponseEntity<ActiveTrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active", HttpMethod.POST, activeTsRequest, ActiveTrainingScheduleDto.class);

        ResponseEntity<ActiveTrainingScheduleDto> adaptedSchedule = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsForAdaptivChangeDto.getDudeId() + "/activeTrainingSchedule", HttpMethod.GET, null, ActiveTrainingScheduleDto.class);

        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkoutsAdaptive = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + adaptedSchedule.getBody().getTrainingScheduleId() + "/1/workouts/copyTs", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});

        List<WorkoutExerciseDtoOut> foundWorkoutExercises = new ArrayList<>();
        for (TrainingScheduleWorkoutDtoOut t: foundTrainingScheduleWorkoutsAdaptive.getBody()) {
            assertNotEquals(workoutMinReps.getId(), t.getId());
            assertNotEquals(workoutMinReps.getCalorieConsumption(), t.getCalorieConsumption());
            foundWorkoutExercises.addAll(REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT + "/" + t.getId() + "/" + t.getVersion() + "/exercises", HttpMethod.GET, null, new ParameterizedTypeReference<List<WorkoutExerciseDtoOut>>(){}).getBody());
        }
        for (WorkoutExerciseDtoOut w: foundWorkoutExercises) {
            assertTrue(w.getRepetitions() >= 1);
        }
    }

    @Test
    public void whenMinimumNumberOfSetsForWaExAndChangeAdaptivelyDecrease_thenNumberOfSetsWontBeDecreased(){
        ResponseEntity<TrainingScheduleDto> tsResponse = postTrainingSchedule(trainingScheduleMinSets);
        activeTsForAdaptivChangeDto.setTrainingScheduleId(tsResponse.getBody().getId());
        activeTsForAdaptivChangeDto.setTrainingScheduleVersion(tsResponse.getBody().getVersion());

        HttpEntity<ActiveTrainingScheduleDto> activeTsRequest = new HttpEntity<>(activeTsForAdaptivChangeDto);
        ResponseEntity<ActiveTrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active", HttpMethod.POST, activeTsRequest, ActiveTrainingScheduleDto.class);

        ResponseEntity<ActiveTrainingScheduleDto> adaptedSchedule = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsForAdaptivChangeDto.getDudeId() + "/activeTrainingSchedule", HttpMethod.GET, null, ActiveTrainingScheduleDto.class);
        Long newTsId = adaptedSchedule.getBody().getTrainingScheduleId();

        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkoutsAdaptive = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + newTsId + "/1/workouts/copyTs", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});

        List<WorkoutExerciseDtoOut> foundWorkoutExercises = new ArrayList<>();
        for (TrainingScheduleWorkoutDtoOut t: foundTrainingScheduleWorkoutsAdaptive.getBody()) {
            assertNotEquals(workoutMinSets.getId(), t.getId());
            foundWorkoutExercises.addAll(REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT + "/" + t.getId() + "/" + t.getVersion() + "/exercises", HttpMethod.GET, null, new ParameterizedTypeReference<List<WorkoutExerciseDtoOut>>(){}).getBody());
        }
        for (WorkoutExerciseDtoOut w: foundWorkoutExercises) {
            assertTrue(w.getSets() >= 1);
        }
    }

    @Test
    public void givenDudesOneBeginnerOneAdvancedAndChangeAdaptively_thenChangeForBeginnerIsLowerThanForAdvanced(){

        ResponseEntity<TrainingScheduleDto> tsResponse = postTrainingSchedule(trainingScheduleDto);
        activeTsForAdaptivChangeDto.setTrainingScheduleId(tsResponse.getBody().getId());
        activeTsForAdaptivChangeDto.setTrainingScheduleVersion(tsResponse.getBody().getVersion());
        HttpEntity<ActiveTrainingScheduleDto> activeTsRequest = new HttpEntity<>(activeTsForAdaptivChangeDto);
        ResponseEntity<ActiveTrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active", HttpMethod.POST, activeTsRequest, ActiveTrainingScheduleDto.class);

        ResponseEntity<ExerciseDoneDto[]> exerciseDoneResponse = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsForAdaptivChangeDto.getDudeId() + "/activeTrainingSchedule/done",
                HttpMethod.GET, null, ExerciseDoneDto[].class);
        HttpEntity<ExerciseDoneDto[]> exercisesDoneRequest = new HttpEntity<>(exerciseDoneResponse.getBody());

        exerciseDoneResponse.getBody()[0].setDone(true);
        exerciseDoneResponse.getBody()[1].setDone(true);

        ResponseEntity markExercises = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active/done",
                HttpMethod.PUT, exercisesDoneRequest, ExerciseDoneDto[].class);

        ResponseEntity<ActiveTrainingScheduleDto> adaptedSchedule = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + trainingScheduleDto.getCreatorId() + "/activeTrainingSchedule", HttpMethod.GET, null, ActiveTrainingScheduleDto.class);
        assertNotEquals(response.getBody().getTrainingScheduleId(), adaptedSchedule.getBody().getTrainingScheduleId());
        assertEquals(HttpStatus.OK, adaptedSchedule.getStatusCode());

        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkoutsAdaptive = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + adaptedSchedule.getBody().getTrainingScheduleId() + "/1/workouts/copyTs", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});

        // ----------------------

        ResponseEntity<TrainingScheduleDto> tsResponse2 = postTrainingSchedule(trainingScheduleDto2);
        activeTsForAdaptivChangeDto.setTrainingScheduleId(tsResponse2.getBody().getId());
        activeTsForAdaptivChangeDto.setTrainingScheduleVersion(tsResponse2.getBody().getVersion());
        activeTsForAdaptivChangeDto.setDudeId(tsResponse2.getBody().getCreatorId());
        HttpEntity<ActiveTrainingScheduleDto> activeTsRequest2 = new HttpEntity<>(activeTsForAdaptivChangeDto);
        ResponseEntity<ActiveTrainingScheduleDto> response2 = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active", HttpMethod.POST, activeTsRequest2, ActiveTrainingScheduleDto.class);

        ResponseEntity<ExerciseDoneDto[]> exerciseDoneResponse2 = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTsForAdaptivChangeDto.getDudeId() + "/activeTrainingSchedule/done",
                HttpMethod.GET, null, ExerciseDoneDto[].class);
        HttpEntity<ExerciseDoneDto[]> exercisesDoneRequest2 = new HttpEntity<>(exerciseDoneResponse2.getBody());

        exerciseDoneResponse2.getBody()[0].setDone(true);
        exerciseDoneResponse2.getBody()[1].setDone(true);

        ResponseEntity markExercises2 = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active/done",
                HttpMethod.PUT, exercisesDoneRequest2, ExerciseDoneDto[].class);

        ResponseEntity<ActiveTrainingScheduleDto> adaptedSchedule2 = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + trainingScheduleDto2.getCreatorId() + "/activeTrainingSchedule", HttpMethod.GET, null, ActiveTrainingScheduleDto.class);
        assertNotEquals(response2.getBody().getTrainingScheduleId(), adaptedSchedule2.getBody().getTrainingScheduleId());
        assertEquals(HttpStatus.OK, adaptedSchedule2.getStatusCode());

        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> foundTrainingScheduleWorkoutsAdaptive2 = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + adaptedSchedule2.getBody().getTrainingScheduleId() + "/1/workouts/copyTs", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleWorkoutDtoOut[]>(){});

        // ----------------------

        int totalCaloriesAdvanced = 0;
        int totalRepsAdvanced = 0;
        int totalDurationAdvanced = 0;
        List<WorkoutExerciseDtoOut> foundWorkoutExercises2 = new ArrayList<>();
        for (TrainingScheduleWorkoutDtoOut t: foundTrainingScheduleWorkoutsAdaptive2.getBody()) {
            totalCaloriesAdvanced += t.getCalorieConsumption();
            foundWorkoutExercises2.addAll(REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT + "/" + t.getId() + "/" + t.getVersion() + "/exercises", HttpMethod.GET, null, new ParameterizedTypeReference<List<WorkoutExerciseDtoOut>>(){}).getBody());
        }
        for (WorkoutExerciseDtoOut w: foundWorkoutExercises2) {
            totalRepsAdvanced += (w.getRepetitions() * w.getSets());
            totalDurationAdvanced += w.getExDuration();
        }

        int totalCaloriesBeginner = 0;
        int totalRepsBeginner = 0;
        int totalDurationBeginner = 0;
        List<WorkoutExerciseDtoOut> foundWorkoutExercises = new ArrayList<>();
        for (TrainingScheduleWorkoutDtoOut t: foundTrainingScheduleWorkoutsAdaptive.getBody()) {
            totalCaloriesBeginner += t.getCalorieConsumption();
            foundWorkoutExercises.addAll(REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT + "/" + t.getId() + "/" + t.getVersion() + "/exercises", HttpMethod.GET, null, new ParameterizedTypeReference<List<WorkoutExerciseDtoOut>>(){}).getBody());
        }
        for (WorkoutExerciseDtoOut w: foundWorkoutExercises) {
            totalRepsBeginner += (w.getRepetitions() * w.getSets());
            totalDurationBeginner += w.getExDuration();
        }

        assert(totalRepsBeginner <= totalRepsAdvanced);
        assert(totalDurationBeginner <= totalDurationAdvanced);
        assert(totalCaloriesBeginner <= totalCaloriesAdvanced);
    }

    @Test
    public void givenTrainingScheduleWithMoreThan70PercentExOfCategoryStrengthAndOneWithLessThan70PercentAndChangeAdaptively_thenChangeOfStrengthFocusedIsLower(){

    }

    @Test
    public void givenExercisesOfCategoryEnduranceAndOtherWithOneRepetitionAndOneSetAndChangeAdaptively_thenExercisesWontBeChanged(){

    }

    @Test
    public void whenTrainingScheduleEnds_ThenGetCorrectlyCalculatedValuesForStatistic (){
        ActiveTrainingScheduleDto activeTrainingScheduleDto =  postTsAndActiveTs().getBody();

        ResponseEntity<ExerciseDoneDto[]> exerciseDoneResponse = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTrainingScheduleDto.getDudeId() + "/activeTrainingSchedule/done",
                HttpMethod.GET, null, ExerciseDoneDto[].class);
        HttpEntity<ExerciseDoneDto[]> exercisesDoneRequest = new HttpEntity<>(exerciseDoneResponse.getBody());

        exerciseDoneResponse.getBody()[1].setDone(true);
        exerciseDoneResponse.getBody()[2].setDone(true);
        exerciseDoneResponse.getBody()[5].setDone(true);

        ResponseEntity markExercises = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active/done",
                HttpMethod.PUT, exercisesDoneRequest, ExerciseDoneDto[].class);

        REST_TEMPLATE.delete(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/active" + "/" + activeTrainingScheduleDto.getDudeId());

        ResponseEntity<FinishedTrainingScheduleStatsDto[]> stats = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + activeTrainingScheduleDto.getDudeId() + "/statistics", HttpMethod.GET, null, new ParameterizedTypeReference<FinishedTrainingScheduleStatsDto[]>(){});
        assertEquals(  400, stats.getBody()[0].getTotalCalorieConsumption(),0.001);
        assertEquals(3,stats.getBody()[0].getTotalDays(),0.001);
        assertEquals(3.4166,stats.getBody()[0].getTotalHours(),0.001);
        assertEquals(33.3333,stats.getBody()[0].getStrengthPercent(), 0.001);
        assertEquals(66.6666,stats.getBody()[0].getEndurancePercent(), 0.001);
    }

    @Test
    public void whenUpdateOneTrainingSchedule_then200OkAndGetUpdatedTrainingSchedule() {
        HttpEntity<TrainingScheduleDto> trainingScheduleRequest1 = new HttpEntity<>(trainingScheduleDto);
        ResponseEntity<TrainingScheduleDto> trainingScheduleResponse1 = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT, HttpMethod.POST, trainingScheduleRequest1, TrainingScheduleDto.class);
        Long savedTrainingScheduleId = trainingScheduleResponse1.getBody().getId();
        HttpEntity<TrainingScheduleDto> trainingScheduleRequest2 = new HttpEntity<>(trainingScheduleDto2);
        ResponseEntity<TrainingScheduleDto> trainingScheduleResponse2 = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + savedTrainingScheduleId, HttpMethod.PUT, trainingScheduleRequest2, TrainingScheduleDto.class);
        assertEquals(HttpStatus.OK, trainingScheduleResponse2.getStatusCode());
        TrainingScheduleDto responseTrainingScheduleDto = trainingScheduleResponse2.getBody();
        assertEquals(savedTrainingScheduleId, responseTrainingScheduleDto.getId());
        assertEquals((Integer)2, responseTrainingScheduleDto.getVersion());
        responseTrainingScheduleDto.setId(trainingScheduleDto2.getId());
        responseTrainingScheduleDto.setVersion(trainingScheduleDto2.getVersion());
        responseTrainingScheduleDto.setTrainingScheduleWorkouts(trainingScheduleDto2.getTrainingScheduleWorkouts());
        responseTrainingScheduleDto.setId(null);
        assertEquals(trainingScheduleDto2, responseTrainingScheduleDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenUpdateOneTrainingScheduleWithInvalidId_then400BadRequest() {
        HttpEntity<TrainingScheduleDto> trainingScheduleRequest = new HttpEntity<>(trainingScheduleDto2);
        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/101", HttpMethod.PUT, trainingScheduleRequest, TrainingScheduleDto.class);
    }

    @Test
    public void givenFindAllTrainingSchedules_whenCreateOneMoreTrainingSchedule_thenStatus200AndGetOneMoreTrainingSchedule(){
        postTrainingSchedule(trainingScheduleDto);
        postTrainingSchedule(trainingScheduleDto2);
        ResponseEntity<TrainingScheduleDto[]> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/filtered/0", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleDto[]>() {
            });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        int foundTrainingSchedulesLength = response.getBody().length;

        postTrainingSchedule(trainingScheduleDto2);
        ResponseEntity<TrainingScheduleDto[]> responseAfter = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/filtered/0", HttpMethod.GET, null, new ParameterizedTypeReference<TrainingScheduleDto[]>() {
            });
        assertEquals(responseAfter.getBody().length, foundTrainingSchedulesLength+1);
    }

    @Test
    public void givenAllTrainingSchedules_whenDeleteOneTraningSchedule_thenGetArrayWithOneLessTraningSchedule(){
        ResponseEntity<TrainingScheduleDto> trainingSchedule1 = postTrainingSchedule(trainingScheduleDto);
        ResponseEntity<TrainingScheduleDto> trainingSchedule2 = postTrainingSchedule(trainingScheduleDto2);
        TrainingScheduleDto[] foundTrainingSchedulesInitial = REST_TEMPLATE.getForObject(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/filtered/0", TrainingScheduleDto[].class);
        int foundSize = foundTrainingSchedulesInitial.length;
        REST_TEMPLATE.delete(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + trainingSchedule1.getBody().getId());
        TrainingScheduleDto[] foundAfterOneTrainingScheduleDeleted = REST_TEMPLATE.getForObject(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/filtered/0", TrainingScheduleDto[].class);
        assertEquals(foundSize-1, foundAfterOneTrainingScheduleDeleted.length);
        REST_TEMPLATE.delete(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + trainingSchedule2.getBody().getId());
        TrainingScheduleDto[] foundAfterTwoTrainingSchedulesDeleted = REST_TEMPLATE.getForObject(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/filtered/0", TrainingScheduleDto[].class);
        assertEquals(foundSize-2, foundAfterTwoTrainingSchedulesDeleted.length);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void givenNothing_whenDeleteOneTrainingSchedule_then400BadRequest(){
        REST_TEMPLATE.delete(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/100");
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void givenInadequateWorkouts_whenCreateRandomTrainingScheduleWithSpecificParameters_then400BadRequest() {
        TrainingScheduleRandomDto randomTrainingSchedule = new TrainingScheduleRandomDto();
        randomTrainingSchedule.setName(trainingScheduleDto.getName());
        randomTrainingSchedule.setDescription(trainingScheduleDto.getDescription());
        randomTrainingSchedule.setDifficulty(trainingScheduleDto.getDifficulty());
        randomTrainingSchedule.setIntervalLength(trainingScheduleDto.getIntervalLength());
        randomTrainingSchedule.setDuration(100);
        randomTrainingSchedule.setMinTarget(1.0);
        randomTrainingSchedule.setMaxTarget(2.0);
        randomTrainingSchedule.setLowerDifficulty(false);
        randomTrainingSchedule.setCreatorId(trainingScheduleDto.getCreatorId());

        HttpEntity<TrainingScheduleRandomDto> trainingScheduleRequest = new HttpEntity<>(randomTrainingSchedule);
        ResponseEntity<TrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/random", HttpMethod.POST, trainingScheduleRequest, TrainingScheduleDto.class);
    }

    @Test
    public void givenWorkouts_whenCreateRandomTrainingSchedule_then201CreatedAndGetSavedTrainingSchedule() {
        WorkoutDto workout1 = new WorkoutDto();
        workout1.setName("Workout1");
        workout1.setDescription("Description1");
        workout1.setDifficulty(1);
        workout1.setCalorieConsumption(30.0);
        workout1.setCreatorId(workoutDto1.getCreatorId());
        workout1.setIsPrivate(false);
        workout1.setWorkoutExercises(workoutDto1.getWorkoutExercises());

        WorkoutDto workout2 = new WorkoutDto();
        workout2.setName("Workout2");
        workout2.setDescription("Description2");
        workout2.setDifficulty(2);
        workout2.setCalorieConsumption(60.0);
        workout2.setCreatorId(workoutDto1.getCreatorId());
        workout2.setIsPrivate(false);
        workout2.setWorkoutExercises(workoutDto1.getWorkoutExercises());

        HttpEntity<WorkoutDto> workoutRequest = new HttpEntity<>(workout1);
        REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest, WorkoutDto.class);
        HttpEntity<WorkoutDto> workoutRequest2 = new HttpEntity<>(workout2);
        REST_TEMPLATE.exchange(BASE_URL + port + WORKOUT_ENDPOINT, HttpMethod.POST, workoutRequest2, WorkoutDto.class);

        TrainingScheduleRandomDto randomTrainingSchedule = new TrainingScheduleRandomDto();
        randomTrainingSchedule.setName(trainingScheduleDto.getName());
        randomTrainingSchedule.setDescription(trainingScheduleDto.getDescription());
        randomTrainingSchedule.setDifficulty(3);
        randomTrainingSchedule.setIntervalLength(trainingScheduleDto.getIntervalLength());
        randomTrainingSchedule.setDuration(1000);
        randomTrainingSchedule.setMinTarget(30.0);
        randomTrainingSchedule.setMaxTarget(90.0);
        randomTrainingSchedule.setLowerDifficulty(true);
        randomTrainingSchedule.setCreatorId(trainingScheduleDto.getCreatorId());

        HttpEntity<TrainingScheduleRandomDto> trainingScheduleRequest = new HttpEntity<>(randomTrainingSchedule);
        ResponseEntity<TrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/random", HttpMethod.POST, trainingScheduleRequest, TrainingScheduleDto.class);
        assertNotNull(response.getBody().getId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        TrainingScheduleWorkoutDtoOut tsW1 = new TrainingScheduleWorkoutDtoOut();
        tsW1.setName("Workout1");
        tsW1.setDay(1);
        tsW1.setCalorieConsumption(30.0);

        TrainingScheduleWorkoutDtoOut tsW2 = new TrainingScheduleWorkoutDtoOut();
        tsW2.setName("Workout1");
        tsW2.setDay(2);
        tsW2.setCalorieConsumption(30.0);

        TrainingScheduleWorkoutDtoOut tsW3 = new TrainingScheduleWorkoutDtoOut();
        tsW3.setName("Workout2");
        tsW3.setDay(2);
        tsW3.setCalorieConsumption(60.0);

        TrainingScheduleWorkoutDtoOut tsW4 = new TrainingScheduleWorkoutDtoOut();
        tsW4.setName("Workout2");
        tsW4.setDay(3);
        tsW4.setCalorieConsumption(60.0);

        TrainingScheduleWorkoutDtoOut[] randomTrainingScheduleWorkouts = new TrainingScheduleWorkoutDtoOut[]{tsW1,tsW2,tsW3,tsW4};

        ResponseEntity<TrainingScheduleWorkoutDtoOut[]> tsWorkoutsRequest = REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + response.getBody().getId() + "/" + response.getBody().getVersion()+ "/workouts", HttpMethod.GET, null, TrainingScheduleWorkoutDtoOut[].class);
        TrainingScheduleWorkoutDtoOut[] foundTrainingScheduleWorkouts = tsWorkoutsRequest.getBody();
        assertEquals(randomTrainingScheduleWorkouts.length, foundTrainingScheduleWorkouts.length);
        for (int i = 0; i < foundTrainingScheduleWorkouts.length; i++){
            assertEquals(randomTrainingScheduleWorkouts[i].getName(), foundTrainingScheduleWorkouts[i].getName());
            assertEquals(randomTrainingScheduleWorkouts[i].getCalorieConsumption(), foundTrainingScheduleWorkouts[i].getCalorieConsumption());
            assertEquals(randomTrainingScheduleWorkouts[i].getDay(), foundTrainingScheduleWorkouts[i].getDay());
        }
    }

    @Test
    public void givenDudesAndTrainingSchedule_whenDudesRateTrainingSchedule_thenGetRatedTrainingSchedule() {
        DudeDto ratingDude1 = new DudeDto();
        ratingDude1.setName("TrainingRating1");
        ratingDude1.setPassword("123456789");
        ratingDude1.setEmail("john1@dude.com");
        ratingDude1.setDescription("Description 1");
        ratingDude1.setBirthday(LocalDate.of(1982,1,1));
        ratingDude1.setSex(Sex.Male);
        ratingDude1.setSelfAssessment(1);
        ratingDude1.setHeight(185.0);
        ratingDude1.setWeight(85.0);
        HttpEntity<DudeDto> dudeRequest1 = new HttpEntity<>(ratingDude1);
        ResponseEntity<DudeDto> responseDude1 = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest1, DudeDto.class);
        Long dudeId1 = responseDude1.getBody().getId();

        DudeDto ratingDude2 = new DudeDto();
        ratingDude2.setName("TrainingRating2");
        ratingDude2.setPassword("123456789");
        ratingDude2.setEmail("john1@dude.com");
        ratingDude2.setDescription("Description 1");
        ratingDude2.setBirthday(LocalDate.of(1982,1,1));
        ratingDude2.setSex(Sex.Male);
        ratingDude2.setSelfAssessment(1);
        ratingDude2.setHeight(185.0);
        ratingDude2.setWeight(85.0);
        HttpEntity<DudeDto> dudeRequest2 = new HttpEntity<>(ratingDude2);
        ResponseEntity<DudeDto> responseDude2 = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest2, DudeDto.class);
        Long dudeId2 = responseDude2.getBody().getId();

        HttpEntity<TrainingScheduleDto> request = new HttpEntity<>(trainingScheduleDto2);
        ResponseEntity<TrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT, HttpMethod.POST, request, TrainingScheduleDto.class);
        Long trainingScheduleId = response.getBody().getId();

        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/rating/" + dudeId1 + "/" + trainingScheduleId + "/1", HttpMethod.POST, null, Void.class);
        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/rating/" + dudeId2 + "/" + trainingScheduleId + "/4", HttpMethod.POST, null, Void.class);
        TrainingScheduleDto foundTrainingSchedule = REST_TEMPLATE.getForObject(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + trainingScheduleId + "/1", TrainingScheduleDto.class);
        Double expectedRating = 2.5;
        assertEquals(foundTrainingSchedule.getRating(), expectedRating);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenDudeRatesTrainingSchedule_ifDudeOrTrainingScheduleNotExists_then400BadRequest() {
        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/rating/" + 1 + "/" + 100 + "/3", HttpMethod.POST, null, Void.class);
    }

    @Test
    public void givenDudeRatedTrainingSchedule_whenDudeDeletesRating_thenGetRatingZero() {
        DudeDto ratingDude1 = new DudeDto();
        ratingDude1.setName("TrainingRating3");
        ratingDude1.setPassword("123456789");
        ratingDude1.setEmail("john1@dude.com");
        ratingDude1.setDescription("Description 1");
        ratingDude1.setBirthday(LocalDate.of(1982,1,1));
        ratingDude1.setSex(Sex.Male);
        ratingDude1.setSelfAssessment(1);
        ratingDude1.setHeight(185.0);
        ratingDude1.setWeight(85.0);
        HttpEntity<DudeDto> dudeRequest1 = new HttpEntity<>(ratingDude1);
        ResponseEntity<DudeDto> responseDude1 = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest1, DudeDto.class);
        Long dudeId1 = responseDude1.getBody().getId();

        HttpEntity<TrainingScheduleDto> request = new HttpEntity<>(trainingScheduleDto2);
        ResponseEntity<TrainingScheduleDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT, HttpMethod.POST, request, TrainingScheduleDto.class);
        Long trainingScheduleId = response.getBody().getId();

        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/rating/" + dudeId1 + "/" + trainingScheduleId + "/4", HttpMethod.POST, null, Void.class);
        TrainingScheduleDto foundTrainingSchedule = REST_TEMPLATE.getForObject(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + trainingScheduleId + "/1", TrainingScheduleDto.class);
        Double expectedRating = 4.0;
        assertEquals(foundTrainingSchedule.getRating(), expectedRating);

        REST_TEMPLATE.exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/rating/" + dudeId1 + "/" + trainingScheduleId, HttpMethod.DELETE, null, Void.class);
        foundTrainingSchedule = REST_TEMPLATE.getForObject(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT + "/" + trainingScheduleId + "/1", TrainingScheduleDto.class);
        Double expectedRating0 = 0.0;
        assertEquals(foundTrainingSchedule.getRating(), expectedRating0);
    }
}
