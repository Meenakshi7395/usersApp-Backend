package com.example.users.controllers;

import com.example.users.Impl.JwtUtil;
import com.example.users.Impl.UserDetails;
import com.example.users.dto.LoginRequest;
import com.example.users.dto.LoginResponse;
import com.example.users.service.UserService;
import com.example.users.models.User;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping
public class UserController {

    private final UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // @RequestMapping(value = {"/users"}, method = RequestMethod.POST)
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User addedUser = null;
        try {
              addedUser = userService.createUser(user);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
//      Company c = job.getCompany();
        return new ResponseEntity<>(addedUser, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUserById(id);
        if (deleted)
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Authenticate the user (e.g., using Spring Security's authentication manager)
        // If authentication is successful, generate a JWT
        LoginResponse loginResponse = new LoginResponse();
        // logic to check if user exists
        Optional<User> user = userService.findByUserNameAndPassword(request.getUserName(),request.getPassword());
        if(user.isPresent())
        {
            UserDetails userDetails = new UserDetails();
            userDetails.setUserName(user.get().getUserName());
            userDetails.setRole(user.get().getRole());
            String token = jwtUtil.generateToken(userDetails);

            loginResponse.setMessage("Authentication Success");
            loginResponse.setSuccess(true);
            loginResponse.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(false, "User not found", ""));
        }





        //


    }

    // used for delete when want to return json response data
//    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
//        boolean deleted = userService.deleteUserById(id);
//        Map<String, Object> response = new HashMap<>();

//        if (deleted) {
//            response.put("message", "User deleted successfully");
//            response.put("status", HttpStatus.OK.value());
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            response.put("message", "User not found");
//            response.put("status", HttpStatus.NOT_FOUND.value());
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Map<String, Object> response = new HashMap<>();
        boolean updated = userService.updateUser(id, updatedUser);

        if (updated) {
            response.put("message", "User updated successfully");
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "User not found");
            response.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}



