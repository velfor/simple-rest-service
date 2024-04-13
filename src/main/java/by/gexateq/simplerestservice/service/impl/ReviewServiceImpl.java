package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.repository.ReviewRepository;
import by.gexateq.simplerestservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Review> getReviewsByEmployeeId(Long id) {
        return reviewRepository.findByEmployeeId(id);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void checkAndUpdateReviews() {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeDaysAgo = currentDate.minusDays(3);
        var reviewsToCancel =
                reviewRepository.findByCreatedAtBeforeAndStatusNot(threeDaysAgo, ReviewStatus.CANCELLED);
        for (Review review : reviewsToCancel) {
            try {
                review.setStatus(ReviewStatus.CANCELLED);
                reviewRepository.save(review);
                Employee employee = review.getEmployee();
                if (reviewRepository.findAllByEmployeeAndStatusNot(employee, ReviewStatus.CANCELLED).isEmpty()) {
                    employee.setIsActive(false);
                    employeeRepository.save(employee);
                }
            } catch (Exception e) {
                logger.error("An error occurred while processing review with ID: " + review.getId(), e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

    }
}
