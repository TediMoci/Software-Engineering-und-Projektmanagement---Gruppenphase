package at.ac.tuwien.sepm.groupphase.backend.endpoint.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IDudeMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.ServiceException;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IDudeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/dudes")
@Api(value = "dudes")
public class DudeEndpoint {

    private final IDudeService iDudeService;
    private final IDudeMapper dudeMapper;

    @Autowired
    public DudeEndpoint(IDudeService iDudeService, IDudeMapper dudeMapper) {
        this.iDudeService = iDudeService;
        this.dudeMapper = dudeMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Save a new Dude", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto saveDude(@RequestBody DudeDto dudeDto) {
        Dude dude = dudeMapper.dudeDtoToDude(dudeDto);
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.save(dude));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during saving Dude: " + e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get a Dude by name and password", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto findByNameAndPassword(@RequestBody String name, String password) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.findByNameAndPassword(name, password));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during reading Dude: " + e.getMessage(), e);
        }
    }
}
