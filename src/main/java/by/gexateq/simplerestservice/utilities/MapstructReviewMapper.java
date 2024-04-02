package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.dto.ReviewDto;
import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MapstructReviewMapper {
    @Mapping(target = "created", source = "createdAt")
    ReviewDto toDto(Review entity);

    @Mapping(target = "createdAt", source = "created")
    Review toEntity(ReviewDto reviewDto);
}
