package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.repository.ReviewRepository;
import by.gexateq.simplerestservice.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewServiceHelper helper;
    private final ReviewRepository reviewRepository;

    @Scheduled(cron = "${cron.job.check-update-reviews}")
    @Override
    public void checkAndUpdateReviews() {
        log.debug("checkAndUpdateReviews started");
        helper.checkAndUpdateReviewsAndEmployees();
        log.debug("checkAndUpdateReviews finished");
    }
}