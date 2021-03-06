package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import org.springframework.stereotype.Component;

@Component
public class DudeMapper implements IDudeMapper{

    /**
     *
     * @param dudedto
     * @return
     */
    @Override
    public Dude dudeDtoToDude(DudeDto dudedto){

        Dude.DudeBuilder builder = new Dude.DudeBuilder();
        builder.id(dudedto.getId());
        builder.name(dudedto.getName());
        builder.password(dudedto.getPassword());
        builder.description(dudedto.getDescription());
        builder.email(dudedto.getEmail());
        builder.sex(dudedto.getSex());
        builder.selfAssessment(dudedto.getSelfAssessment());
        builder.birthday(dudedto.getBirthday());
        builder.height(dudedto.getHeight());
        builder.weight(dudedto.getWeight());
        builder.imagePath(dudedto.getImagePath());
        builder.isPrivate(dudedto.getIsPrivate());
        builder.roles(dudedto.getRoles());

        return builder.build();
    }

    /**
     *
     * @param dude
     * @return
     */
    @Override
    public DudeDto dudeToDudeDto(Dude dude){

        DudeDto.DudeDtoBuilder builder = new DudeDto.DudeDtoBuilder();
        builder.id(dude.getId());
        builder.name(dude.getName());
        builder.password(dude.getPassword());
        builder.description(dude.getDescription());
        builder.email(dude.getEmail());
        builder.sex(dude.getSex());
        builder.selfAssessment(dude.getSelfAssessment());
        builder.birthday(dude.getBirthday());
        builder.height(dude.getHeight());
        builder.weight(dude.getWeight());
        builder.imagePath(dude.getImagePath());
        builder.isPrivate(dude.getIsPrivate());
        builder.roles(dude.getRoles());

        return builder.build();
    }
}
