package com.group.nice.restaurantapi.dtos;

import lombok.*;

@Data
@NoArgsConstructor
public class ReviewDTO {
    private long id;
    private FoodDTO food;
    private UserBaseDTO user;
    private int rating;
    private String comment;
}
