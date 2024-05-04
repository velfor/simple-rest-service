package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.repository.ReviewRepository;
import by.gexateq.simplerestservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ReviewServiceImplIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * @noinspection OptionalGetWithoutIsPresent
     */

    @Test
    public void testCheckAndUpdateReviews() {
        var employee = Employee.builder()
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@example.com")
                .isActive(true)
                .build();

        Review review = new Review();
        review.setStatus(ReviewStatus.DRAFT);
        review.setCreatedAt(LocalDateTime.now().minusDays(4));
        review.setEmployee(employee);
        var reviews = new ArrayList<>(List.of(review));
        employee.setReviews(reviews);

        employeeRepository.save(employee);
        reviewRepository.save(review);

        reviewService.checkAndUpdateReviews();

        var updatedReview = reviewRepository.findById(review.getId());
        assertEquals(ReviewStatus.CANCELLED, updatedReview.get().getStatus());
        var updatedEmployee = employeeRepository.findById(employee.getId());
        assertFalse(updatedEmployee.get().getIsActive());
    }
}
