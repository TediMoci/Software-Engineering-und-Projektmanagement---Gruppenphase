package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DudeMapper.class)
public interface IDudeMapper {

    Dude dudeDtoToDude(DudeDto dudedto);

    DudeDto dudeToDudeDto(Dude dude);

}
