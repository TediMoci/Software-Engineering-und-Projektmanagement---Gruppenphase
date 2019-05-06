package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import org.mapstruct.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
public class DudeMapper {

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface DudeMapping {
    }

    /**
     *
     * @param dudedto
     * @return
     */
    @DudeMapping
    public Dude dudeDtoToDude(DudeDto dudedto){

        Dude.DudeBuilder builder = new Dude.DudeBuilder();
        builder.id(dudedto.getId());
        builder.name(dudedto.getName());
        builder.description(dudedto.getDescription());
        builder.email(dudedto.getEmail());
        if (dudedto.getSex().equals("Male")){
            builder.sex('M');
        } else if (dudedto.getSex().equals("Female")){
            builder.sex('F');
        } else {
            builder.sex('O');
        }
        builder.status(dudedto.getStatus());
        builder.selfAssessment(dudedto.getSelfAssessment());
        builder.birthday(dudedto.getBirthday());
        builder.height(dudedto.getHeight());
        builder.weight(dudedto.getWeight());
        builder.fitnessProviders(null);
        builder.courses(null);

        return builder.build();
    }

    /**
     *
     * @param dude
     * @return
     */
    @DudeMapping
    public DudeDto dudeToDudeDto(Dude dude){

        DudeDto.DudeDtoBuilder builder = new DudeDto.DudeDtoBuilder();
        builder.id(dude.getId());
        builder.name(dude.getName());
        builder.description(dude.getDescription());
        builder.email(dude.getEmail());
        if (dude.getSex().equals('M')){
            builder.sex("Male");
        } else if (dude.getSex().equals('F')){
            builder.sex("Female");
        } else {
            builder.sex("Other");
        }
        builder.status(dude.getStatus());
        builder.selfAssessment(dude.getSelfAssessment());
        builder.birthday(dude.getBirthday());
        builder.height(dude.getHeight());
        builder.weight(dude.getWeight());
        builder.fitnessProviders(null);
        builder.courses(null);

        return builder.build();
    }
}
