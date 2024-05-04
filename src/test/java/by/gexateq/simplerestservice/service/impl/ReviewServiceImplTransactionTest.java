package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.repository.ReviewRepository;
import by.gexateq.simplerestservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ReviewServiceImplTransactionTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @MockBean
    private EmployeeRepository employeeRepository;


    /**
     * @noinspection OptionalGetWithoutIsPresent
     */
    @Test
    @Sql({"/SQL/insertEmployee.sql", "/SQL/insertReview.sql"})
    /*
    INSERT INTO employees (first_name, last_name, email, is_active) VALUES ('John', 'Smith', 'john.smith@example.com', true);
    INSERT INTO reviews (status, created_at, employee_id) VALUES ('DRAFT', '2023-01-01 00:00:00', 1);
    * */
    public void testCheckAndUpdateReviews() {

        doThrow(new RuntimeException("Custom exception in EmployeeRepository for test"))
                .when(employeeRepository).findEmployeesWithAllReviewsCancelled();

        try {
            reviewService.checkAndUpdateReviews();
        } catch (Exception ex) {
            var review = reviewRepository.findById(1L);
            assertNotEquals(ReviewStatus.CANCELLED, review.get().getStatus());
        }
    }
}

