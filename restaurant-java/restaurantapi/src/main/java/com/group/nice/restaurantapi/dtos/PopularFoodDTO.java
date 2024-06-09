package com.group.nice.restaurantapi.dtos;

import lombok.*;

@Data
@NoArgsConstructor
public class PopularFoodDTO {
    private long foodId;
    private String foodName;
    private String foodDescription;
    private String foodImage;
    private double foodRating;
    private double foodPrice;
}
