package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.repository.ReviewRepository;
import by.gexateq.simplerestservice.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Review> getReviewsByEmployeeId(Long id) {
        return reviewRepository.findByEmployeeId(id);
    }

    @Transactional
    @Scheduled(cron = "${cron.job.check-update-reviews}")
    @Override
    public void checkAndUpdateReviews() {
        log.debug("checkAndUpdateReviews started");
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime threeDaysAgo = currentDate.minusDays(3);
        var reviewsToCancel = reviewRepository.
                findByCreatedAtBeforeAndStatusNot(threeDaysAgo, ReviewStatus.CANCELLED);
        log.debug("checkAndUpdateReviews: reviewsToCancel.size={}", reviewsToCancel.size());

        for (Review review : reviewsToCancel) {
            try {
                review.setStatus(ReviewStatus.CANCELLED);
                reviewRepository.save(review);
                log.debug("checkAndUpdateReviews: review.id={} set to CANCELLED", review.getId());
                Employee employee = review.getEmployee();

                if (reviewRepository.findAllByEmployeeAndStatusNot(
                        employee, ReviewStatus.CANCELLED).isEmpty()) {
                    employee.setIsActive(false);
                    employeeRepository.save(employee);
                    log.debug("checkAndUpdateReviews: employee.id={} set to Active=false",
                            employee.getId());
                }
            } catch (Exception e) {
                log.error("An error occurred while processing review with ID: " + review.getId(), e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

    }
}
