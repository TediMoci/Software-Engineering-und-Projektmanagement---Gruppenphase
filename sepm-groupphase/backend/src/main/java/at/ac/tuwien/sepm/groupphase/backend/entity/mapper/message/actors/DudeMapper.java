package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import org.springframework.stereotype.Component;

@Component
public class DudeMapper {

    /**
     *
     * @param dudedto
     * @return
     */
    public Dude dudeDtoToDude(DudeDto dudedto){
        return new Dude();
    }

    /**
     *
     * @param dude
     * @return
     */
    public DudeDto dudeToDudeDto(Dude dude){
        return new DudeDto();
    }
}
