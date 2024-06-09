package com.group.nice.restaurantapi.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.nice.restaurantapi.dtos.AuthUserDTO;
import com.group.nice.restaurantapi.dtos.UserBaseDTO;
import com.group.nice.restaurantapi.dtos.UserDTO;
import com.group.nice.restaurantapi.models.User;
import com.group.nice.restaurantapi.repositories.UserRepository;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserBaseDTO signin(AuthUserDTO user) {
        UserBaseDTO userBaseDTO = new UserBaseDTO();
        User userEntity = userRepository.findByUsername(user.getUsername());
        if(userEntity != null && userEntity.getPassword().equals(user.getPassword())) {
            userBaseDTO.setId(userEntity.getId());
            userBaseDTO.setUsername(userEntity.getUsername());
            userBaseDTO.setEmail(userEntity.getEmail());
            userBaseDTO.setRole(userEntity.getRole());
        }else{
            throw new RuntimeException("Invalid username or password");
        }
        return userBaseDTO;
    }
    @Override
    public UserBaseDTO signup(UserDTO user) {
        User userEntity = userRepository.findByUsername(user.getUsername());
        if(userEntity != null) {
            throw new RuntimeException("Username already exists");
        }
        userEntity = userRepository.findByEmail(user.getEmail());
        if(userEntity != null) {
            throw new RuntimeException("Email already exists");
        }
        userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        userRepository.save(userEntity);
        UserBaseDTO userBaseDTO = new UserBaseDTO();
        userBaseDTO.setId(userEntity.getId());
        userBaseDTO.setUsername(userEntity.getUsername());
        userBaseDTO.setEmail(userEntity.getEmail());
        userBaseDTO.setRole(userEntity.getRole());
        return userBaseDTO;
    }
}
