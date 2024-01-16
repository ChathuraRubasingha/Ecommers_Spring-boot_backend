package com.sample.ecommers.controller;

import com.sample.ecommers.exception.UserAllredyExistException;
import com.sample.ecommers.exception.UserNotFoundException;
import com.sample.ecommers.model.User;
import com.sample.ecommers.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
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

            if (existingUser == null || !BCrypt.checkpw(loginUser.getPassword(), existingUser.getPassword())) {
                throw new UserNotFoundException();
            }
            String token = generateJWTToken(existingUser.getEmail());

            return ResponseEntity.ok(token);
        } catch (UserNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error during login");
        }
    }
    private String generateJWTToken (String email) {
        return Jwts
                .builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*10))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512))
                .compact();
    }

    @PostMapping("/create-user")
    @CrossOrigin(origins = "*")
    public User newUser(@RequestBody User newUser) {
        try {
            if (userRepository.existsByEmail(newUser.getEmail())) {
                throw new UserAllredyExistException();
            }
            String hashpw = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
            newUser.setPassword(hashpw);
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
