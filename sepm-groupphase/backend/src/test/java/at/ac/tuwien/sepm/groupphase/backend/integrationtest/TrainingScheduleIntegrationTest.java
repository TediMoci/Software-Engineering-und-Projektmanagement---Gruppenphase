package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.TrainingSchedule;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.FinishedTrainingScheduleStats;
import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.TrainingScheduleWorkout;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.MuscleGroup;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.*;
import org.apache.http.HttpResponse;
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
    private static final TrainingScheduleDto trainingScheduleDto = new TrainingScheduleDto();
    private static final TrainingScheduleDto trainingScheduleDto2 = new TrainingScheduleDto();
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
    private static final ActiveTrainingScheduleDto activeTsDto = new ActiveTrainingScheduleDto();

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

        trainingScheduleDto2.setName("TrainingsSchedule2");
        trainingScheduleDto2.setDescription("Description2");
        trainingScheduleDto2.setDifficulty(2);
        trainingScheduleDto2.setIntervalLength(2);
        trainingScheduleDto2.setRating(4.7);

        activeTsDto.setStartDate(LocalDate.now());
        activeTsDto.setIntervalRepetitions(2);
        activeTsDto.setAdaptive(false);

        invalidTrainingScheduleDto.setName("TrainingScheduleInvalid");
    }


    @Before
    public void before() {
        if (!initialized) {
            HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dudeDto);
            ResponseEntity<DudeDto> response = REST_TEMPLATE
                .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
            Long dudeId = response.getBody().getId();

            System.out.println("Created Dude: " + response.getBody());

            exerciseDto1.setCreatorId(dudeId);
            exerciseDto2.setCreatorId(dudeId);
            trainingScheduleDto.setCreatorId(dudeId);
            trainingScheduleDto2.setCreatorId(dudeId);

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

            System.out.println("Created exerciseResponse1: " + exerciseResponse1.getBody());
            System.out.println("Created exerciseResponse2: " + exerciseResponse2.getBody());


            workoutExerciseDtoIn1.setExerciseId(exercise1Id);
            workoutExerciseDtoIn1.setExerciseVersion(exercise1Version);
            workoutExerciseDtoIn2.setExerciseId(exercise2Id);
            workoutExerciseDtoIn2.setExerciseVersion(exercise2Version);

            WorkoutExerciseDtoIn[] workoutExerciseDtoIns = new WorkoutExerciseDtoIn[]{workoutExerciseDtoIn1, workoutExerciseDtoIn2};
            workoutDto1.setWorkoutExercises(workoutExerciseDtoIns);
            workoutDto2.setWorkoutExercises(workoutExerciseDtoIns);
            workoutDto1.setCreatorId(dudeId);
            workoutDto2.setCreatorId(dudeId);

            System.out.println("Creating workoutDto1: " + workoutDto1);
            System.out.println("Creating workoutDto2: " + workoutDto2);

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
            TrainingScheduleWorkoutDtoIn[] trainingScheduleWorkouts = new TrainingScheduleWorkoutDtoIn[]{trainingScheduleWorkoutDtoIn1, trainingScheduleWorkoutDtoIn2};
            trainingScheduleDto.setTrainingScheduleWorkouts(trainingScheduleWorkouts);
            trainingScheduleDto2.setTrainingScheduleWorkouts(new TrainingScheduleWorkoutDtoIn[]{trainingScheduleWorkoutDtoIn1});

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

    // TODO: uncomment when findByFilter for TrainingSchedules has been implemented
    /*
     @Test()
    public void whenSaveTwoTrainingSchedulesOfDifferentDifficultyAndFilterByDifficulty2_thenGetOneResult(){
        TrainingScheduleDto a = postTrainingSchedule(trainingScheduleDto).getBody();
        TrainingScheduleDto b = postTrainingSchedule(trainingScheduleDto2).getBody();
        ResponseEntity<TrainingScheduleDto[]> response = REST_TEMPLATE
            .exchange(BASE_URL + port + TRAININGSCHEDULE_ENDPOINT +"/filtered"+"?filter=2", HttpMethod.GET, null, TrainingScheduleDto[].class);
        assertEquals(1, response.getBody() == null ? 0 : response.getBody().length);
        clearRepository();
    }
     */

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
}
