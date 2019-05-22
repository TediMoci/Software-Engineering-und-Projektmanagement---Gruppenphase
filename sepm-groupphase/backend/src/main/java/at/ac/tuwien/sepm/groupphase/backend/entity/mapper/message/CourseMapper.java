package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IDudeMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors.IFitnessProviderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        FitnessProvider.FitnessProviderBuilder fitnessProviderBuilder = new FitnessProvider.FitnessProviderBuilder();
        fitnessProviderBuilder.id(courseDto.getCreatorId());
        FitnessProvider fitnessProvider = fitnessProviderBuilder.build();
        builder.creator(fitnessProvider);

        return builder.build();
    }

    @Override
    public CourseDto courseToCourseDto(Course course) {
        CourseDto.CourseDtoBuilder builder = new CourseDto.CourseDtoBuilder();

        builder.id(course.getId());
        builder.name(course.getName());
        builder.description(course.getDescription());
        builder.creatorId(course.getCreator().getId());

        return builder.build();
    }
}
