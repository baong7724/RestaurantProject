package com.group.nice.restaurantapi.services.review;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.nice.restaurantapi.dtos.ReviewDTO;
import com.group.nice.restaurantapi.models.Food;
import com.group.nice.restaurantapi.models.Review;
import com.group.nice.restaurantapi.models.User;
import com.group.nice.restaurantapi.repositories.FoodRepository;
import com.group.nice.restaurantapi.repositories.ReviewRepository;
import com.group.nice.restaurantapi.repositories.UserRepository;

@Service
public class ReviewServiceImpl implements IReviewService{
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public ReviewDTO addReview(Long foodId, ReviewDTO reviewDTO) {
        Food food = foodRepository.findById(foodId).get();
        if (food == null) {
            throw new RuntimeException("Food not found");
        }
        User user = userRepository.findById(reviewDTO.getUser().getId()).get();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Review review = new Review();
        review.setFood(food);
        food.getReviews().add(review);
        review.setUser(user);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        reviewRepository.save(review);
        foodRepository.save(food);
        reviewDTO.setId(review.getId());
        return reviewDTO;
    }
    @Override
    public Boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();
        if (review == null) {
            throw new RuntimeException("Review not found");
        }
        review.setDeletedAt(new Date());
        reviewRepository.save(review);
        return true;
    }
}
