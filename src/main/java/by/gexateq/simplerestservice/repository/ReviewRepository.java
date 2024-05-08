package by.gexateq.simplerestservice.repository;

import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByEmployeeId(Long employeeId);

    List<Review> findByCreatedAtBeforeAndStatusNot(LocalDateTime date, ReviewStatus status);
}
