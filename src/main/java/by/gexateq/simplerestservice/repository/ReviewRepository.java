package by.gexateq.simplerestservice.repository;

import by.gexateq.simplerestservice.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByEmployeeId(Long employeeId);
}
