package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.FileStorageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.FollowFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @MockBean
    IFitnessProviderRepository fitnessProviderRepository;

    @MockBean
    FollowFitnessProviderRepository followFitnessProviderRepository;

    @MockBean
    FileStorageRepository fileStorageRepository;

    @BeforeClass
    public static void beforeClass() {
        dude1.setId(1L);
        dude1.setName("John");
        dude1.setPassword("123456789");
        dude1.setEmail("john1@dude.com");
        dude1.setDescription("Description 1");
        dude1.setBirthday(LocalDate.of(1982,1,1));
        dude1.setSex(Sex.Female);
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
        dude.setSelfAssessment(2);
        dude.setHeight(185.0);
        dude.setWeight(85.0);
        return dude;
    }

    @Test
    public void TestSaveDude() throws ServiceException {
        Optional<Dude> optionalDude = Optional.of(dude1);
        Mockito.when(dudeRepository.save(anyObject())).thenReturn(dude1);
        Mockito.when(dudeRepository.findById(dude1.getId())).thenReturn(optionalDude);
        Mockito.when(fileStorageRepository.loadMultipartFile(anyString())).thenReturn(anyObject());
        assertEquals(dudeService.save(dude1),dude1);
    }

    @Test(expected = ServiceException.class)
    public void TestSaveDude_invalidInput_thenServiceException() throws ServiceException {
        Mockito.when(dudeRepository.save(anyObject())).thenThrow(Mockito.mock(DataAccessException.class));
        dudeService.save(dude1);
    }

    @Test
    public void TestFindByName() throws ServiceException{
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(dude1);
        assertEquals(dudeService.findByName("name of Dude1"),dude1);
    }

    @Test(expected = ServiceException.class)
    public void TestFindByName_ifNameNotFound_thenServiceException() throws ServiceException{
        Mockito.when(dudeRepository.findByName(anyString())).thenReturn(null);
        dudeService.findByName("name not in database");
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

    @Test(expected = ServiceException.class)
    public void TestUpdate_newUserInvalid() throws ServiceException {
        Dude invalidDude = dudeBuilder();
        Dude validDude = dudeBuilder();
        invalidDude.setHeight(9000.0);
        invalidDude.setWeight(9000.0);
        Mockito.when(dudeRepository.findByName("Valid Name")).thenReturn(validDude);
        Mockito.when(dudeRepository.findByName(invalidDude.getName())).thenReturn(null);
        dudeService.update("Valid Name",invalidDude);
    }

    @Test
    public void TestFindAll() throws ServiceException {
        List<Dude> dudes = new ArrayList<>();
        dudes.add(dude1);
        dudes.add(dude2);
        Mockito.when(dudeRepository.findAll()).thenReturn(dudes);

        assertEquals(dudeService.findAll(),dudeRepository.findAll());
    }

    @Test
    public void TestFindAll_afterUpdate_sameSize() throws ServiceException {
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

    @Test
    public void TestFitnessProviderFollowing() throws ServiceException {
        Optional<Dude> optionalDude1 = Optional.of(dude1);
        FitnessProvider fitnessProvider = new FitnessProvider();
        fitnessProvider.setId(1L);
        Optional<FitnessProvider> optionalFitnessProvider = Optional.of(fitnessProvider);
        Mockito.when(dudeRepository.findById(1L)).thenReturn(optionalDude1);
        Mockito.when(fitnessProviderRepository.findById(1L)).thenReturn(optionalFitnessProvider);
        Mockito.when(followFitnessProviderRepository.checkFollowedFitnessProvider(1L, 1L)).thenReturn(0);
        dudeService.followFitnessProvider(1L, 1L);
    }

    @Test(expected = ServiceException.class)
    public void TestFitnessProviderFollowing_ifAlreadyFollowing_thenServiceException() throws ServiceException {
        Optional<Dude> optionalDude1 = Optional.of(dude1);
        FitnessProvider fitnessProvider = new FitnessProvider();
        fitnessProvider.setId(1L);
        Optional<FitnessProvider> optionalFitnessProvider = Optional.of(fitnessProvider);
        Mockito.when(dudeRepository.findById(1L)).thenReturn(optionalDude1);
        Mockito.when(fitnessProviderRepository.findById(1L)).thenReturn(optionalFitnessProvider);
        Mockito.when(followFitnessProviderRepository.checkFollowedFitnessProvider(1L, 1L)).thenReturn(1);
        dudeService.followFitnessProvider(1L, 1L);
    }
}
