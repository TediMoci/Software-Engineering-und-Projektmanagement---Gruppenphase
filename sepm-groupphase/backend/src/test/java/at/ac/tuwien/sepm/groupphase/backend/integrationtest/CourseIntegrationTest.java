package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.repository.ICourseRepository;
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

    @Autowired
    ICourseRepository courseRepository;

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

    @After
    public void deleteEntitiesAfterTest(){
        courseRepository.deleteAll();
    }

    private Long postCourse(CourseDto course) {
        HttpEntity<CourseDto> courseRequest = new HttpEntity<>(course);
        ResponseEntity<CourseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest, CourseDto.class);
        return response.getBody().getId();
    }

    private CourseDto courseDtoBuilder(CourseDto d){
        CourseDto courseDto = new CourseDto();
        courseDto.setId(d.getId());
        courseDto.setName(d.getName());
        courseDto.setDescription(d.getDescription());
        courseDto.setCreatorId(d.getCreatorId());
        return courseDto;
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
    public void givenCourse_whenFindCourseById_thenGetCourse(){
        Long i = postCourse(validCourseDto1);
        CourseDto foundCourse = REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "/" + i, CourseDto.class);
        assertEquals(foundCourse.getName(), validCourseDto1.getName());
        assertEquals(foundCourse.getDescription(), validCourseDto1.getDescription());
        assertEquals(foundCourse.getCreatorId(), validCourseDto1.getCreatorId());
    }

    @Test(expected = HttpClientErrorException.class)
    public void givenNothing_whenFindCourseById_thenHttpClientErrorException(){
        REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "/1", CourseDto.class);
    }

    @Test
    public void givenTwoCourses_whenFindCourseByName_thenGetCourses(){
        postCourse(validCourseDto1);
        postCourse(validCourseDto1);
        CourseDto[] foundCourses = REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "?name=" + validCourseDto1.getName(), CourseDto[].class);
        assertEquals(foundCourses.length,2);
    }

    @Test
    public void givenNothing_whenFindCourseByName_thenGetEmptyArray(){
        CourseDto[] foundCourses = REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "?name=" + validCourseDto1.getName(), CourseDto[].class);
        assertEquals(foundCourses.length,0);
    }

    @Test
    public void givenTwoCourses_whenFindAll_thenStatus200AndGetCourses(){
        postCourse(validCourseDto1);
        postCourse(validCourseDto1);
        ResponseEntity<CourseDto[]> response = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT + "/all", HttpMethod.GET, null, new ParameterizedTypeReference<CourseDto[]>() {
            });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().length, 2);
    }

    @Test
    public void givenTwoCourses_whenDeleteOneCourse_thenGetArrayWithOneCourse(){
        Long a = postCourse(validCourseDto1);
        Long b = postCourse(validCourseDto1);
        CourseDto[] foundCoursesInitial = REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "/all", CourseDto[].class);
        assertEquals(foundCoursesInitial.length, 2);

        REST_TEMPLATE.delete(BASE_URL + port + COURSE_ENDPOINT + "/" + a);

        CourseDto[] foundCoursesAfter = REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "/all", CourseDto[].class);
        assertEquals(foundCoursesAfter.length, 1);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void givenNothing_whenDeleteOneCourse_then400BadRequest(){
        REST_TEMPLATE.delete(BASE_URL + port + COURSE_ENDPOINT + "/11");
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
