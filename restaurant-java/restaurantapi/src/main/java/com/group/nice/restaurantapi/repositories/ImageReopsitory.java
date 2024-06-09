package com.group.nice.restaurantapi.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group.nice.restaurantapi.models.Image;

@Repository
public interface ImageReopsitory extends JpaRepository<Image, Long>{
}
