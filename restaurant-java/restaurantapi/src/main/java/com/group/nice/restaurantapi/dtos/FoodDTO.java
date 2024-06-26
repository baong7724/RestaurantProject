package com.group.nice.restaurantapi.dtos;


import java.util.Collection;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {
    private long id;
    private String name;
    private String description;
    private double price;
    private Collection<CategoryDTO> categories;
    private Collection<ImageDTO> images;
    private double rating;
    private CategoryDTO origin;
    private Collection<CategoryDTO> ingredients;
    private Collection<ReviewDTO> reviews;
}
