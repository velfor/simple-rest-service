package by.gexateq.simplerestservice.repository;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByEmployeeId(Long employeeId);

    List<Review> findByCreatedAtBeforeAndStatusNot(LocalDate date, ReviewStatus status);

    List<Review> findAllByEmployeeAndStatusNot(Employee employee, ReviewStatus status);
}
