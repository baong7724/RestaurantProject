package com.group.nice.restaurantapi.services.user;

import com.group.nice.restaurantapi.dtos.AuthUserDTO;
import com.group.nice.restaurantapi.dtos.UserBaseDTO;
import com.group.nice.restaurantapi.dtos.UserDTO;

public interface IUserService {
    public UserBaseDTO signin(AuthUserDTO user);
    public UserBaseDTO signup(UserDTO user);
    //public UserBaseDTO update(UserBaseDTO user);
    //public UserBaseDTO delete(UserBaseDTO user);
}
