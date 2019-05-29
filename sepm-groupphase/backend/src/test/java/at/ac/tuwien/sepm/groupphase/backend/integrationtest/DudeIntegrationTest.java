package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public class DudeIntegrationTest  {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String DUDE_ENDPOINT = "/dudes";
    private static final DudeDto testDude1 = new DudeDto();
    private static final DudeDto testDude2 = new DudeDto();

    @LocalServerPort
    private int port;

    @Autowired
    IDudeRepository dudeRepository;

    @BeforeClass
    public static void setUp(){
        testDude1.setId(1L);
        testDude1.setName("John");
        testDude1.setPassword("123456789");
        testDude1.setEmail("john1@dude.com");
        testDude1.setDescription("Description 1");
        testDude1.setBirthday(LocalDate.of(1982,1,1));
        testDude1.setSex(Sex.Male);
        testDude1.setSelfAssessment(1);
        testDude1.setHeight(185.0);
        testDude1.setWeight(85.0);

        testDude2.setId(2L);
        testDude2.setName("Linda");
        testDude2.setPassword("987654321");
        testDude2.setEmail("linda@dude.com");
        testDude2.setDescription("Description 2");
        testDude2.setBirthday(LocalDate.of(1988,10,10));
        testDude2.setSex(Sex.Female);
        testDude2.setSelfAssessment(2);
        testDude2.setHeight(172.0);
        testDude2.setWeight(68.0);
    }


    private DudeDto dudeDtoBuilder(DudeDto d){
        DudeDto dude = new DudeDto();
        dude.setId(d.getId());
        dude.setName(d.getName());
        dude.setPassword(d.getPassword());
        dude.setEmail(d.getEmail());
        dude.setDescription(d.getDescription());
        dude.setBirthday(d.getBirthday());
        dude.setSex(d.getSex());
        dude.setSelfAssessment(d.getSelfAssessment());
        dude.setHeight(d.getHeight());
        dude.setWeight(d.getWeight());
        return dude;
    }

    //posts dude in repository
    private Long postDude(DudeDto dude) {
        HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dude);
        ResponseEntity<DudeDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
        return response.getBody().getId();
    }

    @After
    public void deleteEntitiesAfterTest(){
        dudeRepository.deleteAll();
    }

    @Test
    public void whenSaveDude_then201CreatedAndGetSavedDude(){
        DudeDto dude = dudeDtoBuilder(testDude1);
        HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dude);

        ResponseEntity<DudeDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        DudeDto dudeResponse = response.getBody();
        dude.setId(dudeResponse.getId());
        dude.setPassword(dudeResponse.getPassword());
        assertEquals(dude,dudeResponse);
    }

    @Test(expected = HttpClientErrorException.class)
    public void whenSaveDude_ifInvalidDude_thenHttpClientErrorException(){
        DudeDto dude = dudeDtoBuilder(testDude1);
        dude.setName("");
        HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dude);

        ResponseEntity<DudeDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = HttpClientErrorException.class)
    public void whenSaveDude_ifNameExists_thenHttpClientErrorException(){
        postDude(testDude1);
        DudeDto dude = dudeDtoBuilder(testDude1);
        HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dude);

        ResponseEntity<DudeDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT, HttpMethod.POST, dudeRequest, DudeDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenTwoDudes_whenFindAllDudes_thenStatus200AndGetListWithTwoDudes(){
        DudeDto dude1 = dudeDtoBuilder(testDude1);
        DudeDto dude2 = dudeDtoBuilder(testDude2);
        dude1.setId(null);
        dude2.setId(null);
        postDude(dude1);
        postDude(dude2);

        ResponseEntity<List<DudeDto>> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<DudeDto>>() {
            });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().size(),2);
    }


    @Test
    public void givenDude_whenFindDudeById_thenGetDude(){
        Long i = postDude(testDude1);
        DudeDto foundDude = REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "/" + i, DudeDto.class);
        assertEquals(foundDude.getName(), testDude1.getName());
        assertEquals(foundDude.getDescription(), testDude1.getDescription());
        assertEquals(foundDude.getBirthday(), testDude1.getBirthday());
        assertEquals(foundDude.getSex(), testDude1.getSex());
    }

    @Test(expected = HttpClientErrorException.class)
    public void givenNothing_whenFindDudeById_thenHttpClientErrorException(){
        REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "/1", DudeDto.class);
    }

    @Test
    public void givenOneDude_whenFindDudeByName_thenGetDude(){
        postDude(testDude2);
        DudeDto foundDude = REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "?name=" + testDude2.getName(), DudeDto.class);
        assertEquals(foundDude.getName(), testDude2.getName());
        assertEquals(foundDude.getHeight(), testDude2.getHeight());
        assertEquals(foundDude.getWeight(), testDude2.getWeight());
        assertEquals(foundDude.getSex(), testDude2.getSex());
    }

    @Test(expected = HttpClientErrorException.class)
    public void whenFindDudeByName_ifInvalidName_thenHttpClientErrorException(){
        postDude(testDude2);
        REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "?name=  ", DudeDto.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void whenFindDudeByName_ifDudeNotFound_thenHttpClientErrorException(){
        postDude(testDude2);
        REST_TEMPLATE.getForObject(BASE_URL + port + DUDE_ENDPOINT + "?name=random" + testDude2.getName(), DudeDto.class);
    }

    @Test
    public void givenDude_whenUpdateDude_thenGetUpdatedDude(){
        postDude(testDude1);
        DudeDto dude = dudeDtoBuilder(testDude2);
        dude.setId(null);
        dude.setName("randomName");

        HttpEntity<DudeDto> dudeRequest = new HttpEntity<>(dude);
        ResponseEntity<DudeDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + DUDE_ENDPOINT + "/" + testDude1.getName(), HttpMethod.PUT,
                dudeRequest, new ParameterizedTypeReference<DudeDto>() {});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        DudeDto dudeResponse = response.getBody();
        assertEquals(dude.getName(), dudeResponse.getName());
        assertEquals(dude.getDescription(), dudeResponse.getDescription());
        assertEquals(dude.getBirthday(), dudeResponse.getBirthday());
        assertEquals(dude.getSex(), dudeResponse.getSex());
    }

}
