package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DudeMapper.class)
public interface IDudeMapper {

    /**
     * @param dudedto to be mapped to an entity
     * @return Dude-entity mapped from given Dude-DTO
     */
    Dude dudeDtoToDude(DudeDto dudedto);

    /**
     * @param dude to be mapped to a DTO
     * @return Dude-DTO mapped from given Dude-entity
     */
    DudeDto dudeToDudeDto(Dude dude);

}
