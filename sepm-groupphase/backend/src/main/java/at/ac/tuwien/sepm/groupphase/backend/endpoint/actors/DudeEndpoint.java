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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get a Dude by name and password", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto findByNameAndPassword(String name, String password) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.findByNameAndPassword(name, password));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "Get all dudes", authorizations = {@Authorization(value = "apiKey")})
    public List<DudeDto> findAll() {
        List<DudeDto> dudeListDTO = new ArrayList<>();
        try {
            for (int i=0; i< iDudeService.findAll().size(); i++){
                dudeListDTO.add(dudeMapper.dudeToDudeDto(iDudeService.findAll().get(i)));
            }
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return dudeListDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a Dude by id", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto findDudeById(@PathVariable("id") Long id) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.findDudeById(id));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update a Dude", authorizations = {@Authorization(value = "apiKey")})
    public DudeDto updateDude(@PathVariable("name") String name, @RequestBody DudeDto dude) {
        try {
            return dudeMapper.dudeToDudeDto(iDudeService.update(name, dudeMapper.dudeDtoToDude(dude)));
        } catch (ServiceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during updating Dude: " + e.getMessage(), e);
        }
    }
}

