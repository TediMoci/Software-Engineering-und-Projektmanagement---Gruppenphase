package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.repository.ICourseRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
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
public class CourseIntegrationTest {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String COURSE_ENDPOINT = "/course";
    private static final String FITNESS_PROVIDER_ENDPOINT = "/fitnessProvider";
    private static final String DUDE_ENDPOINT = "/dudes";
    private static final FitnessProviderDto fitnessProviderDto = new FitnessProviderDto();
    private static final CourseDto validCourseDto1 = new CourseDto();
    private static final CourseDto validCourseDto2 = new CourseDto();
    private static final CourseDto invalidCourseDto1 = new CourseDto();

    private static boolean initialized = false;

    @LocalServerPort
    private int port;

    @Autowired
    ICourseRepository courseRepository;

    @Autowired
    IDudeRepository dudeRepository;

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
        dudeRepository.deleteAll();
    }

    private Long postCourse(CourseDto course) {
        HttpEntity<CourseDto> courseRequest = new HttpEntity<>(course);
        ResponseEntity<CourseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest, CourseDto.class);
        return response.getBody().getId();
    }

    private Long postDude() {
        DudeDto dudeDto = new DudeDto();
        dudeDto.setId(1L);
        dudeDto.setName("John");
        dudeDto.setPassword("123456789");
        dudeDto.setEmail("john1@dude.com");
        dudeDto.setBirthday(LocalDate.of(1982,1,1));
        dudeDto.setSex(Sex.Male);
        dudeDto.setSelfAssessment(1);
        dudeDto.setHeight(185.0);
        dudeDto.setWeight(85.0);
        HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dudeDto);
        ResponseEntity<DudeDto> responseDude = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
        return responseDude.getBody().getId();
    }

    private CourseDto courseDtoBuilder(CourseDto d) {
        CourseDto courseDto = new CourseDto();
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
        REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "/100", CourseDto.class);
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
        REST_TEMPLATE.delete(BASE_URL + port + COURSE_ENDPOINT + "/110");
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

    @Test
    public void givenDudeAndCourse_whenDudeBookmarksCourse_thenCourseInBookmarkedCourses() {
        Long dudeId = postDude();

        CourseDto courseDto = courseDtoBuilder(validCourseDto1);
        Long courseId = postCourse(courseDto);
        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT + "/bookmark/" + dudeId + "/" + courseId, HttpMethod.PUT, null, Void.class);

        ResponseEntity<CourseDto[]> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + dudeId + "/bookmarks/courses", HttpMethod.GET, null, CourseDto[].class);
        assertEquals(1, response.getBody().length);
        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT + "/bookmark/" + dudeId + "/" + courseId, HttpMethod.DELETE, null, Void.class);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void givenDude_whenDudeBookmarksNonExistingCourse_then400BadRequest() {
        Long dudeId = postDude();

        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT + "/bookmark/" + dudeId + "/" + 1, HttpMethod.PUT, null, Void.class);
    }

    @Test
    public void givenDudesAndCourse_whenDudesRateCourse_thenGetRatedCourse() {
        DudeDto ratingDude1 = new DudeDto();
        ratingDude1.setName("FitnessProviderRating1");
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
        ratingDude2.setName("FitnessProviderRating2");
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

        HttpEntity<CourseDto> courseRequest = new HttpEntity<>(validCourseDto1);
        ResponseEntity<CourseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest, CourseDto.class);
        Long courseId = response.getBody().getId();

        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT + "/rating/" + dudeId1 + "/" + courseId + "/5", HttpMethod.POST, null, Void.class);
        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT + "/rating/" + dudeId2 + "/" + courseId + "/4", HttpMethod.POST, null, Void.class);
        CourseDto foundCourse = REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "/" + courseId, CourseDto.class);
        Double expectedRating = 4.5;
        assertEquals(foundCourse.getRating(), expectedRating);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void whenDudeRatesCourse_ifDudeOrCourseNotExists() {
        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT + "/rating/" + 1 + "/" + 100 + "/3", HttpMethod.POST, null, Void.class);
    }

    @Test
    public void givenDudeRatesCourse_whenDudeDeletesRating_thenGetRatingZero() {
        DudeDto ratingDude1 = new DudeDto();
        ratingDude1.setName("FitnessProviderRating3");
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

        HttpEntity<CourseDto> courseRequest = new HttpEntity<>(validCourseDto1);
        ResponseEntity<CourseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + COURSE_ENDPOINT, HttpMethod.POST, courseRequest, CourseDto.class);
        Long courseId = response.getBody().getId();

        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT + "/rating/" + dudeId1 + "/" + courseId + "/5", HttpMethod.POST, null, Void.class);
        CourseDto foundCourse = REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "/" + courseId, CourseDto.class);
        Double expectedRating = 5.0;
        assertEquals(foundCourse.getRating(), expectedRating);

        REST_TEMPLATE.exchange(BASE_URL + port + COURSE_ENDPOINT + "/rating/" + dudeId1 + "/" + courseId, HttpMethod.DELETE, null, Void.class);
        foundCourse = REST_TEMPLATE.getForObject(BASE_URL + port + COURSE_ENDPOINT + "/" + courseId, CourseDto.class);
        Double expectedRating0 = 0.0;
        assertEquals(foundCourse.getRating(), expectedRating0);
    }
}
