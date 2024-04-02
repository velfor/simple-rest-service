package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.dto.ReviewDto;
import by.gexateq.simplerestservice.entity.Review;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = MapstructReviewMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MapstructReviewListMapper {
    List<Review> toEntityList(List<ReviewDto> dtoList);

    List<ReviewDto> toDtoList(List<Review> entityList);
}
