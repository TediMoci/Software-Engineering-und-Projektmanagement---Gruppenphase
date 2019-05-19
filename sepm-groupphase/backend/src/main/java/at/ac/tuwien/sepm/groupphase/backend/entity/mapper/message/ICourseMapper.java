package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface ICourseMapper {

    Course courseDtoToCourse(CourseDto courseDto);

    CourseDto courseToCourseDto(Course course);

}
