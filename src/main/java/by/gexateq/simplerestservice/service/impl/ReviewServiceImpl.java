package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.repository.ReviewRepository;
import by.gexateq.simplerestservice.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewServiceHelper helper;
    private final ReviewRepository reviewRepository;

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


}
