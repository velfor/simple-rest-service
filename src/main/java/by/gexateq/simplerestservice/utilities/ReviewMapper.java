package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.dto.ReviewDto;
import by.gexateq.simplerestservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "created", source = "createdAt")
    ReviewDto toDto(Review entity);

    @Mapping(target = "createdAt", source = "created")
    Review toEntity(ReviewDto reviewDto);
}
