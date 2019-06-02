package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto.CourseDtoBuilder;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course.CourseBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-02T11:22:30+0200",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class ICourseMapperImpl implements ICourseMapper {

    @Override
    public Course courseDtoToCourse(CourseDto courseDto) {
        if ( courseDto == null ) {
            return null;
        }

        CourseBuilder course = Course.builder();

        course.id( courseDto.getId() );
        course.name( courseDto.getName() );
        course.description( courseDto.getDescription() );

        return course.build();
    }

    @Override
    public CourseDto courseToCourseDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDtoBuilder courseDto = CourseDto.builder();

        courseDto.id( course.getId() );
        courseDto.name( course.getName() );
        courseDto.description( course.getDescription() );

        return courseDto.build();
    }
}
