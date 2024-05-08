package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.repository.ReviewRepository;
import by.gexateq.simplerestservice.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewServiceHelper helper;
    private final ReviewRepository reviewRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Review> getReviewsByEmployeeId(Long id) {
        return reviewRepository.findByEmployeeId(id);
    }

    @Scheduled(cron = "${cron.job.check-update-reviews}")
    @Override
    public void checkAndUpdateReviews() {
        log.debug("checkAndUpdateReviews started");

        // transactional functional.
        // todo find the problem, fix and provide a test.

        helper.checkAndUpdateReviewsAndEmployees();

        // other non-transactional functional
        // ....

        log.debug("checkAndUpdateReviews finished");
    }

    // A slow functional, do not require transaction.
    // try to speed it up (do in parallel?).
    @Scheduled(cron = "${cron.job.reviews-processing}")
    @Override
    public void reviewsProcessing() {

        // a slow logic....
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime threeDaysAgo = currentDate.minusDays(3);
        var reviews = reviewRepository.
                findByCreatedAtBeforeAndStatusNot(threeDaysAgo, ReviewStatus.CANCELLED);
        log.debug("reviewsProcessing: Found {} reviews", reviews.size());
        reviews.forEach(r -> {
            if (r.getCreatedAt().isBefore(LocalDateTime.now().minusDays(1))) {
                log.info("Review id={} is a day before", r.getId());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    log.error("InterruptedException: ", ie);
                }
            }
        });
        // ....

        // another slow logic....
        var employees = employeeRepository.findEmployeesWithAllReviewsCancelled();
        log.debug("reviewsProcessing: Found {} employees to be transferred to inactive status",
                employees.size());
        employees.forEach(e -> {
            if (e.getIsActive()) {
                log.info("Employee id={} is Active!", e.getId());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    log.error("InterruptedException: ", ie);
                }
            }
        });
        // ....

    }
}
