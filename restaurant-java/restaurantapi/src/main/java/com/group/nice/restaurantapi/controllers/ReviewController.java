package com.group.nice.restaurantapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.group.nice.restaurantapi.dtos.ReviewDTO;
import com.group.nice.restaurantapi.services.review.IReviewService;

@RestController
@CrossOrigin
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private IReviewService reviewService;
    @PostMapping("/{foodId}")
    public ReviewDTO addReview(@PathVariable Long foodId, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.addReview(foodId, reviewDTO);
    }
    @DeleteMapping("/{reviewId}")
    public Boolean deleteReview(@PathVariable Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}
