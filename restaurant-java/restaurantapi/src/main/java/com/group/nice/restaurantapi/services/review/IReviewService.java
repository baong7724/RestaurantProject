package com.group.nice.restaurantapi.services.review;

import com.group.nice.restaurantapi.dtos.ReviewDTO;

public interface IReviewService {
    public ReviewDTO addReview(Long foodId, ReviewDTO reviewDTO);
    public Boolean deleteReview(Long reviewId);
}
