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
import static org.mockito.ArgumentMatchers.anyObject;
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

        Dude2.setId(2L);
        Dude2.setName("Linda");
        Dude2.setPassword("987654321");
        Dude2.setEmail("linda1@dude.com");
        Dude2.setDescription("Description 2");
        Dude2.setBirthday(LocalDate.of(1995,5,7));
        Dude2.setSex(1);
        Dude2.setStatus(3);
        Dude2.setSelfAssessment(3);
        Dude2.setHeight(165.0);
        Dude2.setWeight(60.0);

    }

    @Test
    public void TestSaveDude() throws ServiceException {
        Mockito.when(dudeRepository.save(Dude1)).thenReturn(Dude2);
        assertEquals(dudeService.save(Dude1),Dude2);
    }

    @Test
    public void TestFindByName() throws ServiceException{
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(Dude1);
        assertEquals(dudeService.findByName("hfhkfdh"),Dude1);
    }

    @Test
    public void TestFindByNameAndPassword() throws ServiceException{
        Mockito.when(dudeRepository.findByNameAndPassword(anyString(),anyString())).thenReturn(Dude1);
        assertEquals(dudeService.findByNameAndPassword("John","John's Password"),Dude1);
    }

    @Test
    public void TestUpdate() throws ServiceException {
        Mockito.when(dudeRepository.findByName("John")).thenReturn(Dude1);
        Mockito.when(dudeRepository.findByName("Linda")).thenReturn(null);

        Mockito.when(dudeRepository.save(anyObject())).thenReturn(Dude2);
        Dude updatedDude = dudeService.update("John",Dude2);
        assertEquals(updatedDude,Dude2);

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
