package com.group.nice.restaurantapi.dtos;

import lombok.*;

@NoArgsConstructor
@Data
public class ImageDTO {
    private long id;
    private String url;
    private boolean isMain;
}
