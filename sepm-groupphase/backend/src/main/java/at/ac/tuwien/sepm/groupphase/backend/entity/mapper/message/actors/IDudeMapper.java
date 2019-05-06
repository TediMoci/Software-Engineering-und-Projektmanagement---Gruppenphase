package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DudeMapper.class)
public interface IDudeMapper {

    @Mapping(source = "dudeDto", target = "dude", qualifiedBy = DudeMapper.DudeMapping.class)
    Dude dudeDtoToDude(DudeDto dudedto);

    @Mapping(source = "dude", target = "dudeDto", qualifiedBy = DudeMapper.DudeMapping.class)
    DudeDto dudeToDudeDto(Dude dude);

}
