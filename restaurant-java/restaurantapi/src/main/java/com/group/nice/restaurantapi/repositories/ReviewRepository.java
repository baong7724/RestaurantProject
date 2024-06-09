package com.group.nice.restaurantapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.nice.restaurantapi.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    
}
