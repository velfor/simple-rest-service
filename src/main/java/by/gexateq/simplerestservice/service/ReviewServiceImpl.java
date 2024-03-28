package by.gexateq.simplerestservice.service;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getReviewsByEmployeeId(Long id) {
        List<Review> reviews = reviewRepository.findByEmployeeId(id);
        return reviews;
    }
}
