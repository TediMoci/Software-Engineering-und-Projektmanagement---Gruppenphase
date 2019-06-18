package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.MuscleGroup;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.repository.fitnessComponents.IExerciseRepository;
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
        dudeDto.setName("DudeExercise1");
        dudeDto.setPassword("1234567890");
        dudeDto.setEmail("test@email.com");
        dudeDto.setSex(Sex.Male);
        dudeDto.setSelfAssessment(1);
        dudeDto.setBirthday(LocalDate.of(2000, 1, 1));
        dudeDto.setHeight(150.0);
        dudeDto.setWeight(50.0);

        validExerciseDto1.setName("Exercise1");
        validExerciseDto1.setDescription("Description1");
        validExerciseDto1.setEquipment("Equipment1");
        validExerciseDto1.setMuscleGroup(MuscleGroup.Other);
        validExerciseDto1.setCategory(Category.Strength);

        validExerciseDto2.setName("Exercise2");
        validExerciseDto2.setDescription("Description2");
        validExerciseDto2.setEquipment("Equipment2");
        validExerciseDto2.setMuscleGroup(MuscleGroup.Other);
        validExerciseDto2.setCategory(Category.Endurance);

        invalidExerciseDto1.setName("Exercise3");
        invalidExerciseDto1.setDescription("Description3");
        invalidExerciseDto1.setEquipment("Equipment3");
        invalidExerciseDto1.setMuscleGroup(MuscleGroup.Other);
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
        Long id = responseExerciseDto.getId();
        assertNotNull(id);
        exerciseRepository.delete(id);

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
        exerciseRepository.delete(i);
    }

    @Test(expected = HttpClientErrorException.class)
    public void givenNothing_whenFindExerciseByIdAndVersion_thenHttpClientErrorException(){
        REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "/100/100", ExerciseDto.class);
    }

    @Test
    public void givenTwoExercises_whenFindExerciseByName_thenGetExercises(){
        ExerciseDto exercise1 = exerciseDtoBuilder(validExerciseDto1);
        ExerciseDto exercise2 = exerciseDtoBuilder(validExerciseDto2);
        exercise1.setName("SpecialName1");
        exercise2.setName("SpecialName2");
        Long a = postExercise(exercise1);
        Long b = postExercise(exercise2);

        ExerciseDto[] foundExercises = REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "/1?name=SpecialName", ExerciseDto[].class);
        assertEquals(foundExercises.length,2);
        exerciseRepository.delete(a);
        exerciseRepository.delete(b);
    }

    @Test
    public void givenNothing_whenFindExerciseByName_thenGetEmptyArray(){
        ExerciseDto[] foundExercises = REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "/1?name=randomName", ExerciseDto[].class);
        assertEquals(foundExercises.length,0);
    }

    @Test
    public void givenFindAllExercises_whenCreateOneMoreExercise_thenStatus200AndGetExercises(){
        Long a = postExercise(validExerciseDto1);
        Long b = postExercise(validExerciseDto1);
        ResponseEntity<ExerciseDto[]> response = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/all/1", HttpMethod.GET, null, new ParameterizedTypeReference<ExerciseDto[]>() {
            });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        int foundExercisesLength = response.getBody().length;

        Long c = postExercise(validExerciseDto1);
        ResponseEntity<ExerciseDto[]> responseAfter = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/all/1", HttpMethod.GET, null, new ParameterizedTypeReference<ExerciseDto[]>() {
            });
        assertEquals(responseAfter.getBody().length, foundExercisesLength+1);
        exerciseRepository.delete(a);
        exerciseRepository.delete(b);
        exerciseRepository.delete(c);
    }

    @Test
    public void givenTwoExercises_whenDeleteOneExercise_thenGetArrayWithOneLessExercise(){
        Long a = postExercise(validExerciseDto1);
        Long b = postExercise(validExerciseDto2);
        ExerciseDto[] foundExercisesInitial = REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "/all/1", ExerciseDto[].class);
        int initialLength = foundExercisesInitial.length;

        REST_TEMPLATE.delete(BASE_URL + port + EXERCISE_ENDPOINT + "/" + a);

        ExerciseDto[] foundExercisesAfter = REST_TEMPLATE.getForObject(BASE_URL + port + EXERCISE_ENDPOINT + "/all/1", ExerciseDto[].class);
        assertEquals(foundExercisesAfter.length, initialLength-1);
        exerciseRepository.delete(a);
        exerciseRepository.delete(b);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void givenNothing_whenDeleteOneExercise_then400BadRequest(){
        REST_TEMPLATE.delete(BASE_URL + port + EXERCISE_ENDPOINT + "/110");
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
        exerciseRepository.delete(a);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenUpdateOneExerciseWithInvalidId_then400BadRequest() {
        HttpEntity<ExerciseDto> exerciseRequest1 = new HttpEntity<>(validExerciseDto1);
        ResponseEntity<ExerciseDto> response1 = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest1, ExerciseDto.class);
        Long savedExerciseId = response1.getBody().getId();
        HttpEntity<ExerciseDto> exerciseRequest2 = new HttpEntity<>(validExerciseDto2);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/" + savedExerciseId+1, HttpMethod.PUT, exerciseRequest2, ExerciseDto.class);
        exerciseRepository.delete(savedExerciseId);
    }

    @Test()
    public void whenSaveTwoAndFilterByCharacter2_thenGetFilteredExercise(){
        ExerciseDto e1 = exerciseDtoBuilder(validExerciseDto1);
        ExerciseDto e2 = exerciseDtoBuilder(validExerciseDto2);
        e2.setName("FilterName2");
        Long a = postExercise(e1);
        Long b = postExercise(e2);
        e1.setId(a);
        e2.setId(b);
        ResponseEntity<ExerciseDto[]> response3 = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT +"/filtered/1"+"?filter=FilterName2", HttpMethod.GET, null, ExerciseDto[].class);
        assertEquals(e2, response3.getBody() == null ? null : response3.getBody()[0]);
        exerciseRepository.delete(a);
        exerciseRepository.delete(b);
    }

    @Test
    public void givenDudeAndExercise_whenDudeBookmarksExercise_thenExerciseInBookmarkedExercises() {
        dudeDto.setName("DudeExercise2");
        HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dudeDto);
        ResponseEntity<DudeDto> dudeResponse = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
        Long dudeId = dudeResponse.getBody().getId();

        ExerciseDto exerciseDto = exerciseDtoBuilder(validExerciseDto1);
        Long exerciseId = postExercise(exerciseDto);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/bookmark/" + dudeId + "/" + exerciseId + "/1", HttpMethod.PUT, null, Void.class);

        ResponseEntity<ExerciseDto[]> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + dudeId + "/bookmarks/exercises", HttpMethod.GET, null, ExerciseDto[].class);
        assertEquals(1, response.getBody().length);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/bookmark/" + dudeId + "/" + exerciseId + "/1", HttpMethod.DELETE, null, Void.class);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void givenDude_whenDudeBookmarksNonExistingExercise_then400BadRequest() {
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/bookmark/" + 1 + "/" + 0 + "/1", HttpMethod.PUT, null, Void.class);
    }
}
