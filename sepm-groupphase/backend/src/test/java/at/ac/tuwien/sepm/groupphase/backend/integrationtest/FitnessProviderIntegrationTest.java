package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public class FitnessProviderIntegrationTest {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String FITNESSPROVIDER_ENDPOINT = "/fitnessProvider";
    private static final FitnessProviderDto testFitnessProvider1 = new FitnessProviderDto();
    private static final FitnessProviderDto testFitnessProvider2 = new FitnessProviderDto();

    @LocalServerPort
    private int port;

    @BeforeClass
    public static void setUp(){
        testFitnessProvider1.setId(1L);
        testFitnessProvider1.setName("FitPro1");
        testFitnessProvider1.setPassword("123456789");
        testFitnessProvider1.setEmail("fitpro@dude.com");
        testFitnessProvider1.setDescription("Description 1");
        testFitnessProvider1.setAddress("Address 1");
        testFitnessProvider1.setPhoneNumber("Number 1");
        testFitnessProvider1.setWebsite("Website 1");

        testFitnessProvider2.setId(2L);
        testFitnessProvider2.setName("FitPro2");
        testFitnessProvider2.setPassword("123456789");
        testFitnessProvider2.setEmail("fitpro@dude.com");
        testFitnessProvider2.setDescription("Description 2");
        testFitnessProvider2.setAddress("Address 2");
        testFitnessProvider2.setPhoneNumber("Number 2");
        testFitnessProvider2.setWebsite("Website 2");
    }

    private FitnessProviderDto fitnessProviderDtoBuilder(FitnessProviderDto fp){
        FitnessProviderDto fitnessProvider = new FitnessProviderDto();
        fitnessProvider.setId(fp.getId());
        fitnessProvider.setName(fp.getName());
        fitnessProvider.setPassword(fp.getPassword());
        fitnessProvider.setEmail(fp.getEmail());
        fitnessProvider.setDescription(fp.getDescription());
        fitnessProvider.setAddress(fp.getAddress());
        fitnessProvider.setPhoneNumber(fp.getPhoneNumber());
        fitnessProvider.setWebsite(fp.getWebsite());
        return fitnessProvider;
    }

    private void postFitnessProvider(FitnessProviderDto fitnessProvider) {
        REST_TEMPLATE.postForObject(BASE_URL + port + FITNESSPROVIDER_ENDPOINT, new HttpEntity<>(fitnessProvider), FitnessProviderDto.class);
    }

    @Test
    public void whenSaveFitnessProvider_then201CreatedAndGetSavedFitnessProvider(){
        FitnessProviderDto fitnessProvider = fitnessProviderDtoBuilder(testFitnessProvider1);
        HttpEntity<FitnessProviderDto> fitnessProviderRequest = new HttpEntity<>(fitnessProvider);

        ResponseEntity<FitnessProviderDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + FITNESSPROVIDER_ENDPOINT, HttpMethod.POST, fitnessProviderRequest, FitnessProviderDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        FitnessProviderDto fitnessProviderResponse = response.getBody();
        fitnessProvider.setId(fitnessProviderResponse.getId());
        fitnessProvider.setPassword(fitnessProviderResponse.getPassword());
        assertEquals(fitnessProvider,fitnessProviderResponse);
    }

    @Test(expected = HttpClientErrorException.class)
    public void whenSaveFitnessProvider_ifInvalidFitnessProvider_thenHttpClientErrorException(){
        FitnessProviderDto fitnessProvider = fitnessProviderDtoBuilder(testFitnessProvider1);
        fitnessProvider.setName("");
        HttpEntity<FitnessProviderDto> fitnessProviderRequest = new HttpEntity<>(fitnessProvider);

        ResponseEntity<FitnessProviderDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + FITNESSPROVIDER_ENDPOINT, HttpMethod.POST, fitnessProviderRequest, FitnessProviderDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = HttpClientErrorException.class)
    public void whenSaveFitnessProvider_ifNameExists_thenHttpClientErrorException(){
        postFitnessProvider(testFitnessProvider1);
        FitnessProviderDto fitnessProvider = fitnessProviderDtoBuilder(testFitnessProvider1);
        HttpEntity<FitnessProviderDto> fitnessProviderRequest = new HttpEntity<>(fitnessProvider);

        ResponseEntity<FitnessProviderDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + FITNESSPROVIDER_ENDPOINT, HttpMethod.POST, fitnessProviderRequest, FitnessProviderDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenTwoFitnessProviders_whenFindAllFitnessProviders_thenStatus200AndGetListWithTwoFitnessProviders(){
        postFitnessProvider(testFitnessProvider1);
        postFitnessProvider(testFitnessProvider2);
        ResponseEntity<List<FitnessProviderDto>> response = REST_TEMPLATE
            .exchange(BASE_URL + port + FITNESSPROVIDER_ENDPOINT + "/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<FitnessProviderDto>>() {
            });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().size(),2);
    }


    @Test
    public void givenDude_whenFindDudeById_thenGetDude(){
        postFitnessProvider(testFitnessProvider1);
        FitnessProviderDto foundFitnessProvider = REST_TEMPLATE.getForObject(BASE_URL + port + FITNESSPROVIDER_ENDPOINT + "/1",
            FitnessProviderDto.class);
        assertEquals(foundFitnessProvider.getName(), testFitnessProvider1.getName());
        assertEquals(foundFitnessProvider.getDescription(), testFitnessProvider1.getDescription());
        assertEquals(foundFitnessProvider.getEmail(), testFitnessProvider1.getEmail());
        assertEquals(foundFitnessProvider.getPhoneNumber(), testFitnessProvider1.getPhoneNumber());
    }

    @Test(expected = HttpClientErrorException.class)
    public void givenNothing_whenFindFitnessProviderById_thenHttpClientErrorException(){
        REST_TEMPLATE.getForObject(BASE_URL + port + FITNESSPROVIDER_ENDPOINT + "/1", FitnessProviderDto.class);
    }

    @Test
    public void givenOneFitnessProvider_whenFindDudeByName_thenGetDude(){
        postFitnessProvider(testFitnessProvider2);
        FitnessProviderDto foundFitnessProvider = REST_TEMPLATE.getForObject(BASE_URL + port + FITNESSPROVIDER_ENDPOINT +
            "?name=" + testFitnessProvider2.getName(), FitnessProviderDto.class);
        assertEquals(foundFitnessProvider.getName(), testFitnessProvider2.getName());
        assertEquals(foundFitnessProvider.getName(), testFitnessProvider1.getName());
        assertEquals(foundFitnessProvider.getDescription(), testFitnessProvider1.getDescription());
        assertEquals(foundFitnessProvider.getEmail(), testFitnessProvider1.getEmail());
        assertEquals(foundFitnessProvider.getPhoneNumber(), testFitnessProvider1.getPhoneNumber());
    }

    @Test(expected = HttpClientErrorException.class)
    public void whenFindFitnessProviderByName_ifInvalidName_thenHttpClientErrorException(){
        postFitnessProvider(testFitnessProvider2);
        REST_TEMPLATE.getForObject(BASE_URL + port + FITNESSPROVIDER_ENDPOINT + "?name=  ", FitnessProviderDto.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void whenFindFitnessProviderByName_ifFitnessProviderNotFound_thenHttpClientErrorException(){
        postFitnessProvider(testFitnessProvider2);
        REST_TEMPLATE.getForObject(BASE_URL + port + FITNESSPROVIDER_ENDPOINT + "?name=random" + testFitnessProvider2.getName(),
            FitnessProviderDto.class);
    }

}
