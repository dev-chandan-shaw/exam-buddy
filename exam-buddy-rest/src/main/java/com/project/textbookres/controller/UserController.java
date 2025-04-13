package com.project.textbookres.controller;

import com.project.textbookres.model.User;
import com.project.textbookres.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        User user = new User();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> saySomeThing(@PathVariable String username) {
        User user = new User();
        user.setFirstName(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody User body) {
        var existedUser = userRepository.findByEmail(body.getEmail().toLowerCase(Locale.ROOT));
        if (existedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is registered");
        }
        User user = new User();
        user = body;
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}