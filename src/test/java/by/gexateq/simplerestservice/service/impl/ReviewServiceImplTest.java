package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.repository.ReviewRepository;
import by.gexateq.simplerestservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void testReviewStatusUpdateFailure() {
        Review review = new Review();
        review.setCreatedAt(LocalDateTime.now().minusDays(2));
        review.setStatus(ReviewStatus.DRAFT);

        when(reviewRepository.findByCreatedAtBeforeAndStatusNot(any(), eq(ReviewStatus.CANCELLED))).thenReturn(
                Collections.singletonList(review));
        doThrow(new RuntimeException("Failed to update review status")).when(reviewRepository).save(any());

        reviewService.checkAndUpdateReviews();

        verify(employeeRepository, never()).save(any());
    }
}