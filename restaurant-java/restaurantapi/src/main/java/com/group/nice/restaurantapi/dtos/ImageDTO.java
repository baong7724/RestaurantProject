package com.group.nice.restaurantapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ImageDTO {
    private long id;
    private String url;
    @JsonProperty("isMain")
    private boolean isMain;
}
