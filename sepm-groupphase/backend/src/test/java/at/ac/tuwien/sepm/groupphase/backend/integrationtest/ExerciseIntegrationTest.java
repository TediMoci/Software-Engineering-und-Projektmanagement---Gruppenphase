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

        invalidExerciseDto1.setName("Exercise2");
        invalidExerciseDto1.setDescription("Description2");
        invalidExerciseDto1.setEquipment("Equipment2");
        invalidExerciseDto1.setMuscleGroup("MuscleGroup2");
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
        responseExerciseDto.setId(null);
        assertEquals(validExerciseDto1, responseExerciseDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenSaveOneExerciseWithoutCategory_then400BadRequest() {
        HttpEntity<ExerciseDto> exerciseRequest = new HttpEntity<>(invalidExerciseDto1);
        REST_TEMPLATE.exchange(BASE_URL + port + EXERCISE_ENDPOINT, HttpMethod.POST, exerciseRequest, ExerciseDto.class);
    }

}
