package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.DudeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IDudeMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IFitnessProviderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CourseMapper implements ICourseMapper {

    private final IFitnessProviderMapper fitnessProviderMapper;
    private final IDudeMapper dudeMapper;

    @Autowired
    public CourseMapper(IFitnessProviderMapper fitnessProviderMapper, IDudeMapper dudeMapper) {
        this.fitnessProviderMapper = fitnessProviderMapper;
        this.dudeMapper = dudeMapper;
    }

    @Override
    public Course courseDtoToCourse(CourseDto courseDto) {
        Course.CourseBuilder builder = new Course.CourseBuilder();

        builder.id(courseDto.getId());
        builder.name(courseDto.getName());
        builder.description(courseDto.getDescription());
        // TODO: fix mapping
        builder.fitnessProvider(null);
        builder.dudes(null);
        /*
        builder.fitnessProvider(fitnessProviderMapper.fitnessProviderDtoToFitnessProvider(courseDto.getFitnessProviderDto()));
        Set<Dude> dudes = new HashSet<>();
        for (DudeDto dudeDto : courseDto.getDudeDtos()) {
            dudes.add(dudeMapper.dudeDtoToDude(dudeDto));
        }
        builder.dudes(dudes);
         */

        return builder.build();
    }

    @Override
    public CourseDto courseToCourseDto(Course course) {
        CourseDto.CourseDtoBuilder builder = new CourseDto.CourseDtoBuilder();

        builder.id(course.getId());
        builder.name(course.getName());
        builder.description(course.getDescription());
        // TODO: fix mapping
        builder.fitnessProviderDto(null);
        builder.dudeDtos(null);
        /*
        builder.fitnessProviderDto(fitnessProviderMapper.fitnessProviderToFitnessProviderDto(course.getFitnessProvider()));
        Set<DudeDto> dudeDtos = new HashSet<>();
        for (Dude dude : course.getDudes()) {
            dudeDtos.add(dudeMapper.dudeToDudeDto(dude));
        }
        builder.dudeDtos(dudeDtos);
         */

        return builder.build();
    }
}
