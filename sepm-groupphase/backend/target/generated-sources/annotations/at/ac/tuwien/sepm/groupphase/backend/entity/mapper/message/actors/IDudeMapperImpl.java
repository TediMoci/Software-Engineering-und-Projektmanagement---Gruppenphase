package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto.DudeDtoBuilder;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude.DudeBuilder;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-02T11:22:30+0200",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class IDudeMapperImpl implements IDudeMapper {

    @Override
    public Dude dudeDtoToDude(DudeDto dudedto) {
        if ( dudedto == null ) {
            return null;
        }

        DudeBuilder dude = Dude.builder();

        dude.id( dudedto.getId() );
        dude.name( dudedto.getName() );
        dude.password( dudedto.getPassword() );
        dude.description( dudedto.getDescription() );
        dude.email( dudedto.getEmail() );
        dude.sex( dudedto.getSex() );
        dude.selfAssessment( dudedto.getSelfAssessment() );
        dude.birthday( dudedto.getBirthday() );
        dude.height( dudedto.getHeight() );
        dude.weight( dudedto.getWeight() );
        List<String> list = dudedto.getRoles();
        if ( list != null ) {
            dude.roles( new ArrayList<String>( list ) );
        }
        Set<FitnessProvider> set = dudedto.getFitnessProviders();
        if ( set != null ) {
            dude.fitnessProviders( new ArrayList<FitnessProvider>( set ) );
        }
        Set<Course> set1 = dudedto.getCourses();
        if ( set1 != null ) {
            dude.courses( new ArrayList<Course>( set1 ) );
        }

        return dude.build();
    }

    @Override
    public DudeDto dudeToDudeDto(Dude dude) {
        if ( dude == null ) {
            return null;
        }

        DudeDtoBuilder dudeDto = DudeDto.builder();

        dudeDto.id( dude.getId() );
        dudeDto.name( dude.getName() );
        dudeDto.password( dude.getPassword() );
        dudeDto.description( dude.getDescription() );
        dudeDto.email( dude.getEmail() );
        dudeDto.sex( dude.getSex() );
        dudeDto.selfAssessment( dude.getSelfAssessment() );
        dudeDto.birthday( dude.getBirthday() );
        dudeDto.height( dude.getHeight() );
        dudeDto.weight( dude.getWeight() );
        List<String> list = dude.getRoles();
        if ( list != null ) {
            dudeDto.roles( new ArrayList<String>( list ) );
        }
        List<FitnessProvider> list1 = dude.getFitnessProviders();
        if ( list1 != null ) {
            dudeDto.fitnessProviders( new HashSet<FitnessProvider>( list1 ) );
        }
        List<Course> list2 = dude.getCourses();
        if ( list2 != null ) {
            dudeDto.courses( new HashSet<Course>( list2 ) );
        }

        return dudeDto.build();
    }
}
