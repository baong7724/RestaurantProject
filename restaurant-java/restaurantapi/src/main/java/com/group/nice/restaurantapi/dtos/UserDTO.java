package com.group.nice.restaurantapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String role;
    @JsonProperty("authId")
    private String authId;
}
