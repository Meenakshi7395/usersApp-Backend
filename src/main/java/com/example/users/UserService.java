package com.example.users;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User createUser(User user);

    User getUserById(Long id);

    boolean deleteUserById(Long id);

    boolean updateUser(Long id, User updatedUser);

}
