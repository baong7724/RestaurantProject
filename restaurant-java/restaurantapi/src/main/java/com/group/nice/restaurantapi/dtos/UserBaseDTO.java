package com.group.nice.restaurantapi.dtos;

import lombok.*;

@Data
@NoArgsConstructor
public class UserBaseDTO {
    private long id;
    private String username;
    private String email;
    private String role;
}
