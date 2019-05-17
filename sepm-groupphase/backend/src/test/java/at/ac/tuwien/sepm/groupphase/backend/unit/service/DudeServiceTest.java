package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class DudeServiceTest {
    private final static Dude dude1 = new Dude();
    private final static Dude dude2 = new Dude();

    @Autowired
    IDudeService dudeService;

    @MockBean
    IDudeRepository dudeRepository;

    @BeforeClass
    public static void beforeEach() {
        dude1.setId(1L);
        dude1.setName("John");
        dude1.setPassword("123456789");
        dude1.setEmail("john1@dude.com");
        dude1.setDescription("Description 1");
        dude1.setBirthday(LocalDate.of(1982,1,1));
        dude1.setSex(Sex.Female);
        dude1.setStatus(1);
        dude1.setSelfAssessment(1);
        dude1.setHeight(185.0);
        dude1.setWeight(85.0);

        dude2.setId(2L);
        dude2.setName("Linda");
        dude2.setPassword("987654321");
        dude2.setEmail("linda1@dude.com");
        dude2.setDescription("Description 2");
        dude2.setBirthday(LocalDate.of(1995,5,7));
        dude2.setSex(Sex.Male);
        dude2.setStatus(3);
        dude2.setSelfAssessment(3);
        dude2.setHeight(165.0);
        dude2.setWeight(60.0);
    }

    private Dude dudeBuilder(){
        Dude dude = new Dude();
        dude.setId(3L);
        dude.setName("Harry");
        dude.setPassword("randompassword");
        dude.setEmail("harry@dude.com");
        dude.setDescription("Description 3");
        dude.setBirthday(LocalDate.of(1997,1,1));
        dude.setSex(Sex.Other);
        dude.setStatus(1);
        dude.setSelfAssessment(2);
        dude.setHeight(185.0);
        dude.setWeight(85.0);
        return dude;
    }

    @Test
    public void TestSaveDude() throws ServiceException {
        Mockito.when(dudeRepository.save(dude1)).thenReturn(dude1);
        assertEquals(dudeService.save(dude1),dude1);
    }

    @Test(expected = ServiceException.class)
    public void TestSaveDude_invalidName() throws ServiceException {
        Dude dude = dudeBuilder();
        dude.setName("");
        Mockito.when(dudeRepository.save(dude)).thenReturn(dude);
        dudeService.save(dude);
    }

    @Test(expected = ServiceException.class)
    public void TestSaveDude_invalidPassword() throws ServiceException {
        Dude dude = dudeBuilder();
        dude.setPassword("");
        Mockito.when(dudeRepository.save(dude)).thenReturn(dude);
        dudeService.save(dude);
    }

    @Test
    public void TestFindByName() throws ServiceException{
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(dude1);
        assertEquals(dudeService.findByName("name of Dude1"),dude1);
    }

    @Test
    public void TestFindByName_ifNameNotFound_thenNull() throws ServiceException{
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(null);
        assertEquals(dudeService.findByName("name not in database"),null);
    }

    @Test
    public void TestUpdate() throws ServiceException {
        Mockito.when(dudeRepository.findByName("John")).thenReturn(dude1);
        Mockito.when(dudeRepository.findByName("Linda")).thenReturn(null);
        Mockito.when(dudeRepository.save(anyObject())).thenReturn(dude2);

        Dude updatedDude = dudeService.update("John",dude2);
        assertEquals(updatedDude,dude2);
    }

    @Test(expected = ServiceException.class)
    public void TestUpdate_oldUserNotFound() throws ServiceException {
        Mockito.when(dudeRepository.findByName("John")).thenReturn(null);
        dudeService.update("John",dude2);
    }

    @Test
    public void TestUpdate_newUserInvalidHeightandWeight_noChangeForDude1() throws ServiceException {
        Dude dude = dudeBuilder();
        dude.setHeight(9000.0);
        dude.setWeight(9000.0);
        Mockito.when(dudeRepository.findByName("John")).thenReturn(dude1);
        Mockito.when(dudeRepository.findByName(dude.getName())).thenReturn(null);
        Mockito.when(dudeRepository.save(anyObject())).thenReturn(dude);

        Dude updatedDude = dudeService.update("John",dude);
        assertNotEquals(updatedDude.getWeight(),dude1.getWeight());;
        assertNotEquals(updatedDude.getHeight(),dude1.getHeight());
    }

    @Test
    public void findAll() throws ServiceException {
        List<Dude> dudes = new ArrayList<>();
        dudes.add(dude1);
        dudes.add(dude2);
        Mockito.when(dudeRepository.findAll()).thenReturn(dudes);

        assertEquals(dudeService.findAll(),dudeRepository.findAll());
    }

    @Test
    public void findAll_afterUpdate_sameSize() throws ServiceException {
        List<Dude> dudes = new ArrayList<>();
        dudes.add(dude1);
        dudes.add(dude2);

        Mockito.when(dudeRepository.findAll()).thenReturn(dudes);
        Mockito.when(dudeRepository.findByName("John")).thenReturn(dude1);
        Mockito.when(dudeRepository.findByName("Linda")).thenReturn(null);
        Mockito.when(dudeRepository.save(anyObject())).thenReturn(dude2);

        dudeService.update("John",dude2);
        assertEquals(dudeService.findAll(),dudeRepository.findAll());
    }

    @Test
    public void TestBMI(){
        Dude dude = dudeBuilder();
        double dudeHeight = dude.getHeight(); //185.0
        double dudeWeight = dude.getWeight(); //85.0
        double calcBMI = 24.84;
        assertEquals(dudeService.calculateBMI(dudeHeight,dudeWeight),calcBMI,0.0);
    }

    @Test
    public void TestAge(){
        Dude dude = dudeBuilder();
        assertEquals(dudeService.calculateAge(dude.getBirthday()),2019-1997);
    }
}
