package com.group.nice.restaurantapi.dtos;

import lombok.*;

@NoArgsConstructor
@Data
public class CategoryDTO {
    private long id;
    private String name;
    private String colorCode;
    private String tag;
}
