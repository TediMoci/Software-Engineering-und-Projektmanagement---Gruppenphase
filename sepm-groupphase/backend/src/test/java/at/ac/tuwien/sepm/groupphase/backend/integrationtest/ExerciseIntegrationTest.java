package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.fitnessComponents.ExerciseDto;
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

    @Test
    public void whenSaveOneExercise_then201CreatedAndGetSavedExercise() {
        HttpEntity<ExerciseDto> exerciseRequest = new HttpEntity<>(validExerciseDto1);
        ResponseEntity<ExerciseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest, ExerciseDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ExerciseDto responseExerciseDto = response.getBody();
        assertNotNull(responseExerciseDto.getId());
        responseExerciseDto.setId(1L);
        assertEquals(validExerciseDto1, responseExerciseDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenSaveOneExerciseWithoutCategory_then400BadRequest() {
        HttpEntity<ExerciseDto> exerciseRequest = new HttpEntity<>(invalidExerciseDto1);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest, ExerciseDto.class);
    }

    @Test
    public void whenUpdateOneExercise_then200OkAndGetUpdatedExercise() {
        HttpEntity<ExerciseDto> exerciseRequest1 = new HttpEntity<>(validExerciseDto1);
        ResponseEntity<ExerciseDto> response1 = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest1, ExerciseDto.class);
        Long savedExerciseId = response1.getBody().getId();
        HttpEntity<ExerciseDto> exerciseRequest2 = new HttpEntity<>(validExerciseDto2);
        ResponseEntity<ExerciseDto> response2 = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT + "/" + savedExerciseId, HttpMethod.PUT, exerciseRequest2, ExerciseDto.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        ExerciseDto responseExerciseDto = response2.getBody();
        assertEquals(savedExerciseId, responseExerciseDto.getId());
        assertEquals((Integer)2, responseExerciseDto.getVersion());
        responseExerciseDto.setId(2L);
        responseExerciseDto.setVersion(1);
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
        validExerciseDto1.setId(1L);
        validExerciseDto2.setId(2L);
        HttpEntity<ExerciseDto> courseRequest1 = new HttpEntity<>(validExerciseDto1);
        HttpEntity<ExerciseDto> courseRequest2 = new HttpEntity<>(validExerciseDto2);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, courseRequest1, ExerciseDto.class);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, courseRequest2, ExerciseDto.class);
        ResponseEntity<ExerciseDto[]> response3 = REST_TEMPLATE
            .exchange(BASE_URL + port + EXERCISE_ENDPOINT +"/filtered"+"?filter=2", HttpMethod.GET, null, ExerciseDto[].class);
        assertEquals(validExerciseDto2, response3.getBody() == null ? null : response3.getBody()[0]);
    }
}
