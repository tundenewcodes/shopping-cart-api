package com.tundecodex.shopping_cart.service.user;

import com.tundecodex.shopping_cart.dto.UserDto;
import com.tundecodex.shopping_cart.model.User;
import com.tundecodex.shopping_cart.request.CreateUserRequest;
import com.tundecodex.shopping_cart.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
