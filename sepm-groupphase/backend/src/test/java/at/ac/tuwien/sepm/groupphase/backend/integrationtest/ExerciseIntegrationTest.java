package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public class ExerciseIntegrationTest {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String EXERCISE_ENDPOINT = "/exercise";
    private static final String DUDE_ENDPOINT = "/dudes";
    private static final DudeDto dudeDto = new DudeDto();
    private static final ExerciseDto validExerciseDto1 = new ExerciseDto();
    private static final ExerciseDto validExerciseDto2 = new ExerciseDto();
    private static final ExerciseDto invalidExerciseDto1 = new ExerciseDto();

    private static boolean initialized = false;

    @LocalServerPort
    private int port;

    @Autowired
    IExerciseRepository exerciseRepository;

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

        validExerciseDto1.setName("Exercise1");
        validExerciseDto1.setDescription("Description1");
        validExerciseDto1.setEquipment("Equipment1");
        validExerciseDto1.setMuscleGroup("MuscleGroup1");
        validExerciseDto1.setCategory(Category.Strength);

        validExerciseDto2.setName("Exercise2");
        validExerciseDto2.setDescription("Description2");
        validExerciseDto2.setEquipment("Equipment2");
        validExerciseDto2.setMuscleGroup("MuscleGroup2");
        validExerciseDto2.setCategory(Category.Endurance);

        invalidExerciseDto1.setName("Exercise3");
        invalidExerciseDto1.setDescription("Description3");
        invalidExerciseDto1.setEquipment("Equipment3");
        invalidExerciseDto1.setMuscleGroup("MuscleGroup3");
        invalidExerciseDto1.setCategory(null);
    }

    @Before
    public void before() {
        if (!initialized) {
            HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dudeDto);
            ResponseEntity<DudeDto> response = REST_TEMPLATE
                .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
            Long dudeId = response.getBody().getId();
            validExerciseDto1.setCreatorId(dudeId);
            validExerciseDto2.setCreatorId(dudeId);
            invalidExerciseDto1.setCreatorId(dudeId);

            initialized = true;
        }
    }

    @After
    public void deleteEntitiesAfterTest(){
        exerciseRepository.deleteAll();
    }

    private Long postExercise(ExerciseDto exercise) {
        HttpEntity<ExerciseDto> exerciseRequest = new HttpEntity<>(exercise);
        ResponseEntity<ExerciseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest, ExerciseDto.class);
        return response.getBody().getId();
    }

    private ExerciseDto exerciseDtoBuilder(ExerciseDto e){
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setName(e.getName());
        exerciseDto.setDescription(e.getDescription());
        exerciseDto.setCategory(e.getCategory());
        exerciseDto.setEquipment(e.getEquipment());
        exerciseDto.setMuscleGroup(e.getMuscleGroup());
        exerciseDto.setCreatorId(e.getCreatorId());
        return exerciseDto;
    }

    @Test
    public void whenSaveOneExercise_then201CreatedAndGetSavedExercise() {
        HttpEntity<ExerciseDto> exerciseRequest = new HttpEntity<>(validExerciseDto1);
        ResponseEntity<ExerciseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest, ExerciseDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ExerciseDto responseExerciseDto = response.getBody();
        assertNotNull(responseExerciseDto.getId());
        responseExerciseDto.setId(validExerciseDto1.getId());
        assertEquals(validExerciseDto1, responseExerciseDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenSaveOneExerciseWithoutCategory_then400BadRequest() {
        HttpEntity<ExerciseDto> exerciseRequest = new HttpEntity<>(invalidExerciseDto1);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest, ExerciseDto.class);
    }

    @Test
    public void givenExercise_whenFindExerciseByIdAndVersion_thenGetExercise(){
        Long i = postExercise(validExerciseDto1);
        ExerciseDto foundExercise = REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "/" + i + "/1", ExerciseDto.class);
        assertEquals(foundExercise.getName(), validExerciseDto1.getName());
        assertEquals(foundExercise.getDescription(), validExerciseDto1.getDescription());
        assertEquals(foundExercise.getCreatorId(), validExerciseDto1.getCreatorId());
    }

    @Test(expected = HttpClientErrorException.class)
    public void givenNothing_whenFindExerciseByIdAndVersion_thenHttpClientErrorException(){
        REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "/1/1", ExerciseDto.class);
    }

    @Test
    public void givenTwoExercises_whenFindExerciseByName_thenGetExercises(){
        postExercise(validExerciseDto1);
        postExercise(validExerciseDto1);
        ExerciseDto[] foundExercises = REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "?name=" + validExerciseDto1.getName(), ExerciseDto[].class);
        assertEquals(foundExercises.length,2);
    }

    @Test
    public void givenNothing_whenFindExerciseByName_thenGetEmptyArray(){
        ExerciseDto[] foundExercises = REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "?name=" + validExerciseDto1.getName(), ExerciseDto[].class);
        assertEquals(foundExercises.length,0);
    }

    @Test
    public void givenTwoExercises_whenFindAll_thenStatus200AndGetExercises(){
        postExercise(validExerciseDto1);
        postExercise(validExerciseDto1);
        ResponseEntity<ExerciseDto[]> response = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/all", HttpMethod.GET, null, new ParameterizedTypeReference<ExerciseDto[]>() {
            });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().length, 2);
    }

    @Test
    public void givenTwoExercises_whenDeleteOneExercise_thenGetArrayWithOneExercise(){
        Long a = postExercise(validExerciseDto1);
        Long b = postExercise(validExerciseDto2);
        ExerciseDto[] foundExercisesInitial = REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "/all", ExerciseDto[].class);
        assertEquals(foundExercisesInitial.length, 2);

        REST_TEMPLATE.delete(BASE_URL + port + EXERCISE_ENDPOINT + "/" + a);

        ExerciseDto[] foundExercisesAfter = REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "/all", ExerciseDto[].class);
        assertEquals(foundExercisesAfter.length, 1);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void givenNothing_whenDeleteOneExercise_then400BadRequest(){
        REST_TEMPLATE.delete(BASE_URL + port + EXERCISE_ENDPOINT + "/11");
    }

    @Test
    public void whenUpdateOneExercise_then200OkAndGetUpdatedExercise() {
        Long a = postExercise(validExerciseDto1);

        HttpEntity<ExerciseDto> exerciseRequest2 = new HttpEntity<>(validExerciseDto2);
        ResponseEntity<ExerciseDto> response2 = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/" + a, HttpMethod.PUT, exerciseRequest2, ExerciseDto.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        ExerciseDto responseExerciseDto = response2.getBody();
        assertEquals(a, responseExerciseDto.getId());
        assertEquals((Integer)2, responseExerciseDto.getVersion());
        responseExerciseDto.setId(validExerciseDto2.getId());
        responseExerciseDto.setVersion(validExerciseDto2.getVersion());
        assertEquals(validExerciseDto2, responseExerciseDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenUpdateOneExerciseWithInvalidId_then400BadRequest() {
        HttpEntity<ExerciseDto> exerciseRequest1 = new HttpEntity<>(validExerciseDto1);
        ResponseEntity<ExerciseDto> response1 = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest1, ExerciseDto.class);
        Long savedExerciseId = response1.getBody().getId();
        HttpEntity<ExerciseDto> exerciseRequest2 = new HttpEntity<>(validExerciseDto2);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/" + savedExerciseId+1, HttpMethod.PUT, exerciseRequest2, ExerciseDto.class);
    }

    @Test()
    public void whenSaveTwoAndFilterByCharacter2_thenGetFilteredExercise(){
        ExerciseDto e1 = exerciseDtoBuilder(validExerciseDto1);
        ExerciseDto e2 = exerciseDtoBuilder(validExerciseDto2);
        Long a = postExercise(e1);
        Long b = postExercise(e2);
        e1.setId(a);
        e2.setId(b);
        ResponseEntity<ExerciseDto[]> response3 = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT +"/filtered"+"?filter=2", HttpMethod.GET, null, ExerciseDto[].class);
        assertEquals(e2, response3.getBody() == null ? null : response3.getBody()[0]);
    }

    @Test
    public void givenDudeAndExercise_whenDudeBookmarksExercise_thenExerciseInBookmarkedExercises() {
        ExerciseDto exerciseDto = exerciseDtoBuilder(validExerciseDto1);
        Long exerciseId = postExercise(exerciseDto);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/bookmark/" + 1 + "/" + exerciseId + "/1", HttpMethod.PUT, null, Void.class);

        ResponseEntity<ExerciseDto[]> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + 1 + "/bookmarks/exercises", HttpMethod.GET, null, ExerciseDto[].class);
        assertEquals(1, response.getBody().length);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/bookmark/" + 1 + "/" + exerciseId + "/1", HttpMethod.DELETE, null, Void.class);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void givenDude_whenDudeBookmarksNonExistingExercise_then400BadRequest() {
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/bookmark/" + 1 + "/" + 1 + "/1", HttpMethod.PUT, null, Void.class);
    }
}
