package com.example.users.service;

import com.example.users.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();
    User createUser(User user);

    User getUserById(Long id);

    boolean deleteUserById(Long id);

    boolean updateUser(Long id, User updatedUser);

    User findByUsername(String userName);

    Optional<User> findByUserNameAndPassword(String userName, String Password);
}
