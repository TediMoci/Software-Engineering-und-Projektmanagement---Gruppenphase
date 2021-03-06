package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.repository.FileStorageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.actors.IFitnessProviderRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class FitnessProviderServiceTest {
    private final static FitnessProvider fitnessProvider1 = new FitnessProvider();
    private final static FitnessProvider fitnessProvider2 = new FitnessProvider();

    @MockBean
    IFitnessProviderRepository fpRepository;

    @MockBean
    FileStorageRepository fileStorageRepository;

    @Autowired
    IFitnessProviderService fpService;

    @BeforeClass
    public static void beforeEach() {
        fitnessProvider1.setId(1L);
        fitnessProvider1.setName("FitPro1");
        fitnessProvider1.setPassword("123456789");
        fitnessProvider1.setEmail("fitpro@dude.com");
        fitnessProvider1.setDescription("Description 1");
        fitnessProvider1.setAddress("Address 1");
        fitnessProvider1.setPhoneNumber("Number 1");
        fitnessProvider1.setWebsite("Website 1");

        fitnessProvider2.setId(2L);
        fitnessProvider2.setName("FitPro2");
        fitnessProvider2.setPassword("123456789");
        fitnessProvider2.setEmail("fitpro@dude.com");
        fitnessProvider2.setDescription("Description 2");
        fitnessProvider2.setAddress("Address 2");
        fitnessProvider2.setPhoneNumber("Number 2");
        fitnessProvider2.setWebsite("Website 2");
    }

    private FitnessProvider fitnessProviderBuilder(){
        FitnessProvider fitnessProvider = new FitnessProvider();
        fitnessProvider.setId(3L);
        fitnessProvider.setName("FitPro3");
        fitnessProvider.setPassword("123456789");
        fitnessProvider.setEmail("fitpro3@dude.com");
        fitnessProvider.setDescription("Description 3");
        fitnessProvider.setAddress("Address 3");
        fitnessProvider.setPhoneNumber("Number 3");
        fitnessProvider.setWebsite("Website 3");
        return fitnessProvider;
    }

    @Test
    public void TestSaveFitnessProvider() throws ServiceException {
        Optional<FitnessProvider> optionalFP = Optional.of(fitnessProvider1);
        Mockito.when(fpRepository.save(fitnessProvider1)).thenReturn(fitnessProvider1);
        Mockito.when(fpRepository.findById(fitnessProvider1.getId())).thenReturn(optionalFP);
        Mockito.when(fileStorageRepository.loadMultipartFile(anyString())).thenReturn(anyObject());
        assertEquals(fpService.save(fitnessProvider1),fitnessProvider1);
    }

    @Test(expected = ServiceException.class)
    public void TestSaveFitnessProvider_invalidInput_thenServiceException() throws ServiceException {
        Mockito.when(fpRepository.save(anyObject())).thenThrow(Mockito.mock(DataAccessException.class));
        fpService.save(fitnessProvider1);
    }

    @Test
    public void TestFindByName() throws ServiceException{
        Mockito.when(fpRepository.findByName(anyString())).thenReturn(fitnessProvider1);
        assertEquals(fpService.findByName("name of FP11"),fitnessProvider1);
    }

    @Test(expected = ServiceException.class)
    public void TestFindByName_ifNameNotFound_thenServiceException() throws ServiceException{
        Mockito.when(fpRepository.findByName(anyString())).thenReturn(null);
        fpService.findByName("name not in database");
    }

    @Test
    public void TestUpdate() throws ServiceException {
        Mockito.when(fpRepository.findByName("FitPro1")).thenReturn(fitnessProvider1);
        Mockito.when(fpRepository.findByName("FitPro2")).thenReturn(null);
        Mockito.when(fpRepository.save(anyObject())).thenReturn(fitnessProvider2);

        FitnessProvider updatedFitnessProvider = fpService.update("FitPro1",fitnessProvider2);
        assertEquals(updatedFitnessProvider,fitnessProvider2);
    }

    @Test(expected = ServiceException.class)
    public void TestUpdate_oldFitnessProviderNotFound() throws ServiceException {
        Mockito.when(fpRepository.findByName("FitPro1")).thenReturn(null);
        fpService.update("FitPro1",fitnessProvider1);
    }

    @Test(expected = ServiceException.class)
    public void TestUpdate_newFitnessProviderInvalid() throws ServiceException {
        FitnessProvider invalidFP = fitnessProviderBuilder();
        FitnessProvider validFP = fitnessProviderBuilder();
        invalidFP.setName("");
        Mockito.when(fpRepository.findByName("Valid Name")).thenReturn(validFP);
        Mockito.when(fpRepository.findByName(invalidFP.getName())).thenReturn(null);
        fpService.update("Valid Name",invalidFP);
    }

    @Test
    public void findAll() throws ServiceException {
        List<FitnessProvider> fitnessProviders = new ArrayList<>();
        fitnessProviders.add(fitnessProvider1);
        fitnessProviders.add(fitnessProvider2);
        Mockito.when(fpRepository.findAll()).thenReturn(fitnessProviders);

        assertEquals(fpService.findAll(),fpRepository.findAll());
    }

    @Test
    public void findAll_afterUpdate_sameSize() throws ServiceException {
        List<FitnessProvider> fitnessProviders = new ArrayList<>();
        fitnessProviders.add(fitnessProvider1);
        fitnessProviders.add(fitnessProvider2);

        Mockito.when(fpRepository.findAll()).thenReturn(fitnessProviders);
        Mockito.when(fpRepository.findByName("FitPro1")).thenReturn(fitnessProvider1);
        Mockito.when(fpRepository.findByName("FitPro2")).thenReturn(null);
        Mockito.when(fpRepository.save(anyObject())).thenReturn(fitnessProvider2);

        fpService.update("FitPro1",fitnessProvider2);
        assertEquals(fpService.findAll(),fpRepository.findAll());
    }

}
