package com.group.nice.restaurantapi.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDTO {
    private String username;
    private String password;
}
