package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ReviewServiceHelper {

    private final ReviewRepository reviewRepository;

    private final EmployeeRepository employeeRepository;

    @Transactional
    public void checkAndUpdateReviewsAndEmployees() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime threeDaysAgo = currentDate.minusDays(3);
        var reviewsToCancel = reviewRepository.
                findByCreatedAtBeforeAndStatusNot(threeDaysAgo, ReviewStatus.CANCELLED);
        log.debug("checkAndUpdateReviews: Found {} reviews to cancel", reviewsToCancel.size());

        List<Review> reviewsToUpdate = reviewsToCancel.stream()
                .peek(review -> review.setStatus(ReviewStatus.CANCELLED))
                .toList();
        if (!reviewsToUpdate.isEmpty()) {
            reviewRepository.saveAll(reviewsToUpdate);
            log.debug("checkAndUpdateReviews: Updated {} reviews", reviewsToUpdate.size());
        }

        var employeesWithAllReviewsCancelled = employeeRepository.findEmployeesWithAllReviewsCancelled();
        log.debug("checkAndUpdateReviews: Found {} employees to be transferred to inactive status",
                employeesWithAllReviewsCancelled.size());

        var employeesToUpdate = employeesWithAllReviewsCancelled.stream()
                .peek(employee -> employee.setIsActive(false))
                .toList();
        if (!employeesToUpdate.isEmpty()) {
            employeeRepository.saveAll(employeesToUpdate);
            log.debug("checkAndUpdateReviews: Updated {} employees", employeesToUpdate.size());
        }
    }
}
