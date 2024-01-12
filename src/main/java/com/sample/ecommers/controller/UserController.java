package com.sample.ecommers.controller;

import com.sample.ecommers.exception.UserNotFoundException;
import com.sample.ecommers.model.User;
import com.sample.ecommers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create-user")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/get-users")
    List<User> getAllUsers (){
        return userRepository.findAll();
    }

    @GetMapping("/getUserById/{id}")
    User getUserById (@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));

    }
}
