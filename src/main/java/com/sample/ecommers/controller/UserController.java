package com.sample.ecommers.controller;

import com.sample.ecommers.exception.UserAllredyExistException;
import com.sample.ecommers.exception.UserNotFoundException;
import com.sample.ecommers.model.User;
import com.sample.ecommers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.util.Elements;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> loginUser(@RequestBody User loginUser) {
        try {
            User existingUser = userRepository.findByEmail(loginUser.getEmail());

            if (existingUser == null || !existingUser.getPassword().equals(loginUser.getPassword())) {
                throw new UserNotFoundException();
            }

            return ResponseEntity.ok("Authentication successful!");

        } catch (UserNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error during login");
        }
    }



    @PostMapping("/create-user")
    @CrossOrigin(origins = "*")
    public User newUser(@RequestBody User newUser) {
        try {
            if (userRepository.existsByEmail(newUser.getEmail())) {
                throw new UserAllredyExistException();
            }
            return userRepository.save(newUser);
        } catch (UserNotFoundException e) {
            throw e;
        }catch (Exception ex) {
            throw new RuntimeException("Error creating user", ex);
        }
    }

    @GetMapping("/get-users")
    List<User> getAllUsers (){
        return userRepository.findAll();
    }

    @GetMapping("/getUserById/{id}")
    User getUserById (@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException());

    }
}
