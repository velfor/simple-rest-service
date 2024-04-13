package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeMapperTest {
    @Autowired
    EmployeeMapper mapper;

    @Test
    public void shouldMap() {

        var entity = new Employee();
        entity.setId(1L);
        entity.setFirstName("first");
        entity.setLastName("last");
        entity.setEmail("email@email.com");
        entity.setIsActive(true);

        Review review = new Review();
        review.setId(1L);
        review.setStatus(ReviewStatus.DONE);
        review.setCreatedAt(LocalDateTime.now());
        review.setEmployee(entity);

        entity.setReviews(List.of(review));

        var result = mapper.toDto(entity);

        assertEquals(result.getFirstName(), entity.getFirstName());
        assertEquals(result.getSurname(), entity.getLastName());
    }
}