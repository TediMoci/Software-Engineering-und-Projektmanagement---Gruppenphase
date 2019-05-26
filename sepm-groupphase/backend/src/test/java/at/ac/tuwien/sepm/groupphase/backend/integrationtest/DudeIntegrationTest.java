package at.ac.tuwien.sepm.groupphase.backend.integrationtest;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.integrationtest.base.BaseIntegrationTest;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public class  DudeIntegrationTest  {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String DUDE_ENDPOINT = "/dudes";
    private static final DudeDto testDude = new DudeDto();

    @MockBean
    IDudeRepository dudeRepository;

    @LocalServerPort
    private int port;

    @BeforeClass
    public static void setUp(){
        testDude.setId(1L);
        testDude.setName("John");
        testDude.setPassword("123456789");
        testDude.setEmail("john1@dude.com");
        testDude.setDescription("Description 1");
        testDude.setBirthday(LocalDate.of(1982,1,1));
        testDude.setSex(Sex.Male);
        testDude.setStatus(1);
        testDude.setSelfAssessment(1);
        testDude.setHeight(185.0);
        testDude.setWeight(85.0);
    }

    private Dude dudeBuilder(){
        Dude dude = new Dude();
        dude.setId(1L);
        dude.setName("John");
        dude.setPassword("123456789");
        dude.setEmail("john1@dude.com");
        dude.setDescription("Description 1");
        dude.setBirthday(LocalDate.of(1982,1,1));
        dude.setSex(Sex.Male);
        dude.setStatus(1);
        dude.setSelfAssessment(1);
        dude.setHeight(185.0);
        dude.setWeight(85.0);
        return dude;
    }

    private DudeDto dudeDtoBuilder(){
        DudeDto dude = new DudeDto();
        dude.setId(1L);
        dude.setName("John");
        dude.setPassword("123456789");
        dude.setEmail("john1@dude.com");
        dude.setDescription("Description 1");
        dude.setBirthday(LocalDate.of(1982,1,1));
        dude.setSex(Sex.Male);
        dude.setStatus(1);
        dude.setSelfAssessment(1);
        dude.setHeight(185.0);
        dude.setWeight(85.0);
        return dude;
    }

    @Test
    public void TestFindAll(){
        Dude dude = dudeBuilder();
        List<Dude> dudes = new ArrayList<>();
        dudes.add(dude);
        dudes.add(dude);
        List<DudeDto> dudesDto = new ArrayList<>();
        dudesDto.add(testDude);
        dudesDto.add(testDude);
        Mockito.when(dudeRepository.findAll()).thenReturn(dudes);

        ResponseEntity<List<DudeDto>> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<DudeDto>>() {
            });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        List<DudeDto> dudesDto2 = response.getBody();
        Assert.assertThat(dudesDto2, is(dudesDto));
    }

    @Test
    public void TestSaveDude(){
        Dude dude = dudeBuilder();
        HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(testDude);
        Mockito.when(dudeRepository.save(anyObject())).thenReturn(dude);
        ResponseEntity<DudeDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        DudeDto dudeResponse = response.getBody();
        assertEquals(testDude,dudeResponse);
    }

    @Test(expected = HttpClientErrorException.class)
    public void TestSaveDude_invalidDude(){
        DudeDto dude = dudeDtoBuilder();
        dude.setName("");
        HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dude);

        ResponseEntity<DudeDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void TestFindDudeById(){
        Dude dude = dudeBuilder();
        Mockito.when(dudeRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dude));
        DudeDto foundDude = REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "/" + dude.getId(), DudeDto.class);

        assertEquals(foundDude.getId(), dude.getId());
        assertEquals(foundDude.getName(), dude.getName());
        assertEquals(foundDude.getHeight(), dude.getHeight());
    }

    @Test(expected = HttpClientErrorException.class)
    public void TestFindDudeById_NotFound(){
        Dude dude = dudeBuilder();
        Mockito.when(dudeRepository.findById(anyLong())).thenThrow(NoSuchElementException.class);
        REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "/" + dude.getId(), DudeDto.class);
    }

    @Test
    public void TestFindDudeByName(){
        Dude dude = dudeBuilder();
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(dude);
        DudeDto foundDude = REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "?name=" + dude.getName(), DudeDto.class);

        assertEquals(foundDude.getId(), dude.getId());
        assertEquals(foundDude.getName(), dude.getName());
        assertEquals(foundDude.getHeight(), dude.getHeight());
    }

    @Test(expected = HttpClientErrorException.class)
    public void TestFindDudeByName_invalidName(){
        Dude dude = dudeBuilder();
        dude.setName("");
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(dude);
        REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "?name=" + dude.getName(), DudeDto.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void TestFindDudeByName_NotFound(){
        Dude dude = dudeBuilder();
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(null);
        REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "?name=" + dude.getName(), DudeDto.class);
    }

    /**
    @Test
    public void TestFindAll2(){
        Dude dude = dudeBuilder();

        List<Dude> dudes = new ArrayList<>();
        dudes.add(dude);
        dudes.add(dude);

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
    } **/

    /**
    @Test
    public void TestFindByName(){
        Dude dude = dudeBuilder();
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(dude);

        Response response = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .header(HttpHeaders.AUTHORIZATION, validUserTokenWithPrefix)
            .when().get(DUDE_ENDPOINT + "/" + dude.getId())
            .then().extract().response();

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK.value()));
        DudeDto dudeDto = response.as(DudeDto.class);
        Assert.assertThat(dudeDto, is(testDude));
    } **/


}
