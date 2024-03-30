package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    @Test
    public void shouldMap() {
        var mapper = Mappers.getMapper(MapstructEmployeeMapper.class);;

        var entity = new Employee();
        entity.setId(1L);
        entity.setEmail("email");
        entity.setActive(true);
        entity.setFirstName("first");
        entity.setLastName("last");
        Review review = new Review();
        review.setId(1L);
        review.setStatus(ReviewStatus.DONE);
        review.setCreatedAt(LocalDateTime.now());
        entity.setReviews(List.of(review));

        var result = mapper.toDto(entity);

        assertEquals(result.getFirstName(), entity.getFirstName());
        assertEquals(result.getSurname(), entity.getLastName());
    }
}