package at.ac.tuwien.sepm.groupphase.backend.integrationtest;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.actors.DudeEndpoint;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.implementation.actors.DudeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
public class  DudeIntegrationTest {
    private static DudeDto testDude = new DudeDto();

    @MockBean
    DudeService dudeService;

    @Autowired
    DudeEndpoint dudeEndpoint;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
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
    public void testGetUser() throws ServiceException {
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

    }
}
