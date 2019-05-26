package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public class CourseIntegrationTest {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String COURSE_ENDPOINT = "/course";
    private static final String FITNESS_PROVIDER_ENDPOINT = "/fitnessProvider";
    private static final FitnessProviderDto fitnessProviderDto = new FitnessProviderDto();
    private static final CourseDto validCourseDto1 = new CourseDto();
    private static final CourseDto validCourseDto2 = new CourseDto();
    private static final CourseDto invalidCourseDto1 = new CourseDto();

    private static boolean initialized = false;

    @LocalServerPort
    private int port;

    @BeforeClass
    public static void beforeClass() {
        fitnessProviderDto.setName("FitnessProvider1");
        fitnessProviderDto.setPassword("qqqqqqqq");
        fitnessProviderDto.setAddress("Test Street 1");
        fitnessProviderDto.setEmail("test@email.com");

        validCourseDto1.setName("Course1");
        validCourseDto1.setDescription("Description1");

        validCourseDto2.setName("Course2");
        validCourseDto2.setDescription("Description2");

        invalidCourseDto1.setName("");
        invalidCourseDto1.setDescription("Description3");
    }

    @Before
    public void before() {
        if (!initialized) {
            HttpEntity<FitnessProviderDto> fitnessProviderRequest = new HttpEntity<>(fitnessProviderDto);
            ResponseEntity<FitnessProviderDto> response = REST_TEMPLATE
                .exchange(BASE_URL + port + FITNESS_PROVIDER_ENDPOINT, HttpMethod.POST, fitnessProviderRequest, FitnessProviderDto.class);
            Long fitnessProviderId = response.getBody().getId();
            validCourseDto1.setCreatorId(fitnessProviderId);
            validCourseDto2.setCreatorId(fitnessProviderId);
            invalidCourseDto1.setCreatorId(fitnessProviderId);

            initialized = true;
        }

    }

    @Test
    public void whenSaveOneCourse_then201CreatedAndGetSavedCourse() {
        HttpEntity<CourseDto> courseRequest = new HttpEntity<>(validCourseDto1);
        ResponseEntity<CourseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest, CourseDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CourseDto responseCourseDto = response.getBody();
        assertNotNull(responseCourseDto.getId());
        responseCourseDto.setId(1L);
        assertEquals(validCourseDto1, responseCourseDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenSaveOneCourseWithBlankName_then400BadRequest() {
        HttpEntity<CourseDto> courseRequest = new HttpEntity<>(invalidCourseDto1);
        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest, CourseDto.class);
    }

    @Test
    public void whenUpdateOneCourse_then200OkAndGetUpdatedCourse() {
        HttpEntity<CourseDto> courseRequest1 = new HttpEntity<>(validCourseDto1);
        ResponseEntity<CourseDto> response1 = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest1, CourseDto.class);
        Long savedCourseId = response1.getBody().getId();
        HttpEntity<CourseDto> courseRequest2 = new HttpEntity<>(validCourseDto2);
        ResponseEntity<CourseDto> response2 = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT + "/" + savedCourseId, HttpMethod.PUT, courseRequest2, CourseDto.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        CourseDto responseCourseDto = response2.getBody();
        assertEquals(savedCourseId, responseCourseDto.getId());
        responseCourseDto.setId(2L);
        assertEquals(validCourseDto2, responseCourseDto);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenUpdateOneCourseWithInvalidId_then400BadRequest() {
        HttpEntity<CourseDto> courseRequest1 = new HttpEntity<>(validCourseDto1);
        ResponseEntity<CourseDto> response1 = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest1, CourseDto.class);
        Long savedCourseId = response1.getBody().getId();
        HttpEntity<CourseDto> courseRequest2 = new HttpEntity<>(validCourseDto2);
        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT + "/" + savedCourseId+1, HttpMethod.PUT, courseRequest2, CourseDto.class);
    }

    @Test()
    public void whenSaveTwoAndFilterByCharacter2_thenGetFilteredCourse(){
        validCourseDto1.setId(1L);
        validCourseDto2.setId(2L);
        HttpEntity<CourseDto> courseRequest1 = new HttpEntity<>(validCourseDto1);
        HttpEntity<CourseDto> courseRequest2 = new HttpEntity<>(validCourseDto2);
        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest1, CourseDto.class);
        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest2, CourseDto.class);
        ResponseEntity<CourseDto[]> response3 = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT +"/filtered"+"?filter=2", HttpMethod.GET, null, CourseDto[].class);
        assertEquals(validCourseDto2, response3.getBody() == null ? null : response3.getBody()[0]);
    }

}
