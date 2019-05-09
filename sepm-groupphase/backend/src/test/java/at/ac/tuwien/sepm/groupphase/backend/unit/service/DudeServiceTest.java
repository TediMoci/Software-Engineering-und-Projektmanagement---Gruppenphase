package at.ac.tuwien.sepm.groupphase.backend.unit.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "integration-test")
public class DudeServiceTest {
    private final Dude Dude1 = new Dude();
    private final Dude Dude2 = new Dude();


    @Autowired
    private IDudeService dudeService;

    @Before
    public void beforeEach() throws ServiceException {
        Dude1.setName("John");
        Dude1.setPassword("123456789");
        Dude1.setEmail("john1@dude.com");
        Dude1.setBirthday(LocalDate.of(1982,4,1));
        Dude1.setHeight(185.0);
        Dude1.setWeight(85.0);

        Dude2.setName("Linda");
        Dude2.setPassword("987654321");
        Dude2.setEmail("linda1@dude.com");
        Dude2.setBirthday(LocalDate.of(1995,5,7));
        Dude2.setHeight(165.0);
        Dude2.setWeight(60.0);

    }

    @Test
    public void bmi_calculatorTest(){
        double d1height = Dude1.getHeight(); //185.0
        double d1weight = Dude1.getWeight(); //85.0
        double calcBMI = 24.84;
        assertEquals(dudeService.calculateBMI(d1height,d1weight),calcBMI,0.0);

    }
}
