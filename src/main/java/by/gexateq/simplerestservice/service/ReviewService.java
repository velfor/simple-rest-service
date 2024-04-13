package by.gexateq.simplerestservice.service;

import by.gexateq.simplerestservice.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewsByEmployeeId(Long id);

    void checkAndUpdateReviews();

}
