package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class DudeServiceTest {
    private final Dude Dude1 = new Dude();
    private final Dude Dude2 = new Dude();

    @Autowired
    IDudeService dudeService;

    @MockBean
    IDudeRepository dudeRepository;

    @Before
    public void beforeEach() {
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

        Dude2.setName("Linda");
        Dude2.setPassword("987654321");
        Dude2.setEmail("linda1@dude.com");
        Dude2.setDescription("Description 2");
        Dude2.setBirthday(LocalDate.of(1995,5,7));
        Dude2.setSex('M');
        Dude2.setStatus(3);
        Dude2.setSelfAssessment(3);
        Dude2.setHeight(165.0);
        Dude2.setWeight(60.0);

    }

    @Test
    public void TestSaveDude() throws ServiceException {
        Mockito.when(dudeRepository.save(Dude1)).thenReturn(Dude1);
        assertEquals(dudeService.save(Dude1),Dude1);
    }

    @Test(expected = ServiceException.class)
    public void TestSaveDude_invalidName() throws ServiceException {
        Dude1.setName("");
        dudeService.save(Dude1);
    }

    //TODO: Validator for password needs to be written
    @Test(expected = ServiceException.class)
    public void TestSaveDude_invalidPassword_returnsNull() throws ServiceException {
        Dude1.setPassword("");
        dudeService.save(Dude1);
    }

    @Test
    public void TestFindByName() throws ServiceException{
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(Dude1);
        assertEquals(dudeService.findByName("John"),Dude1);
    }

    @Test
    public void TestFindByNameAndPassword() throws ServiceException{
        Dude testDude = new Dude();
        testDude.setName(Dude1.getName());
        testDude.setPassword(Dude1.getPassword());
        testDude.setEmail(Dude1.getEmail());
        testDude.setDescription(Dude1.getDescription());
        testDude.setBirthday(Dude1.getBirthday());
        testDude.setSex(Dude1.getSex());
        testDude.setStatus(Dude1.getStatus());
        testDude.setSelfAssessment(Dude1.getSelfAssessment());
        testDude.setHeight(Dude1.getHeight());
        testDude.setWeight(Dude1.getWeight());

        Mockito.when(dudeRepository.findByNameAndPassword(anyString(),anyString())).thenReturn(testDude);
        Dude dude = dudeService.findByNameAndPassword("John","John's Password");
        assertEquals(dude.getName(),Dude1.getName());
        assertNotEquals(dude.getPassword(),Dude1.getPassword());
        assertEquals(dude.getPassword(),"XXXXXXXX");
    }

    @Test
    public void TestBMI(){
        double d1height = Dude1.getHeight(); //185.0
        double d1weight = Dude1.getWeight(); //85.0
        double calcBMI = 24.84;
        assertEquals(dudeService.calculateBMI(d1height,d1weight),calcBMI,0.0);

    }

    @Test
    public void TestAge(){
        assertEquals(dudeService.calculateAge(Dude1.getBirthday()),2019-1982);

    }
}
