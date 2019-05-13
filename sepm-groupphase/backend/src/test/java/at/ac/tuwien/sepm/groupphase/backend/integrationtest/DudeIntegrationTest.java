package at.ac.tuwien.sepm.groupphase.backend.integrationtest;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public class  DudeIntegrationTest extends BaseIntegrationTest {
    private static final String DUDE_ENDPOINT = "/dudes";
    private static DudeDto testDude = new DudeDto();

    @MockBean
    IDudeRepository dudeRepository;

    @Before
    public void setUp(){
        testDude.setId(1L);
        testDude.setName("John");
        testDude.setPassword("123456789");
        testDude.setEmail("john1@dude.com");
        testDude.setDescription("Description 1");
        testDude.setBirthday(LocalDate.of(1982,1,1));
        testDude.setSex("Male");
        testDude.setStatus(1);
        testDude.setSelfAssessment(1);
        testDude.setHeight(185.0);
        testDude.setWeight(85.0);
    }

    @Test
    public void TestFindAll(){
        Dude Dude1 = new Dude();
        Dude1.setId(1L);
        Dude1.setName("John");
        Dude1.setPassword("123456789");
        Dude1.setEmail("john1@dude.com");
        Dude1.setDescription("Description 1");
        Dude1.setBirthday(LocalDate.of(1982,1,1));
        Dude1.setSex(1);
        Dude1.setStatus(1);
        Dude1.setSelfAssessment(1);
        Dude1.setHeight(185.0);
        Dude1.setWeight(85.0);

        List<Dude> dudes = new ArrayList<>();
        dudes.add(Dude1);
        dudes.add(Dude1);

        List<DudeDto> dudesDto = new ArrayList<>();
        dudesDto.add(testDude);
        dudesDto.add(testDude);

        Mockito.when(dudeRepository.findAll()).thenReturn(dudes);

        Response response = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when().get(DUDE_ENDPOINT+"/all")
            .then().extract().response();

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));

        List<DudeDto> dudesDto2 = Arrays.asList(response.as(DudeDto[].class));
        Assert.assertThat(dudesDto2, is(dudesDto));
    }

/**
    @Test
    public void TestFindByNameAndPassword() throws ServiceException {
        Dude Dude1 = new Dude();
        Dude1.setName("John");
        Dude1.setPassword("123456789");
        Dude1.setEmail("john1@dude.com");
        Dude1.setDescription("Description 1");
        Dude1.setBirthday(LocalDate.of(1982,1,1));
        Dude1.setSex('M');
        Dude1.setStatus(1);
        Dude1.setSelfAssessment(1);
        Dude1.setHeight(185.0);
        Dude1.setWeight(85.0);

        Mockito.when(dudeService.findByNameAndPassword(anyString(),anyString())).thenReturn(Dude1);
        assertEquals(dudeEndpoint.findByNameAndPassword("John","John's Password"), testDude);

    } **/
}
