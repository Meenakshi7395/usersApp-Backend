package com.example.users.usersRepository;

import com.example.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserNameAndPassword(String userName,String Password);
}
