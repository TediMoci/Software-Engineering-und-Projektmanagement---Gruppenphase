package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CourseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface ICourseMapper {

    /**
     * @param courseDto to be mapped to an entity
     * @return Course-entity mapped from given Course-DTO
     */
    Course courseDtoToCourse(CourseDto courseDto);

    /**
     * @param course to be mapped to a DTO
     * @return Course-DTO mapped from given Course-entity
     */
    CourseDto courseToCourseDto(Course course);

}
