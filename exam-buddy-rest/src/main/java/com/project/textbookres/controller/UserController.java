package com.project.textbookres.controller;

import com.project.textbookres.dto.CreateUserRequest;
import com.project.textbookres.dto.SignInRequest;
import com.project.textbookres.dto.SignInResponse;
import com.project.textbookres.model.User;
import com.project.textbookres.respository.UserRepository;
import com.project.textbookres.service.CustomUserDetailsService;
import com.project.textbookres.service.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Locale;

@RestController
@RequestMapping("api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTService jwtService;

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

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody CreateUserRequest request, HttpServletResponse res) {
        var existedUser = userRepository.findByEmail(request.getEmail().toLowerCase(Locale.ROOT));
        if (existedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is registered");
        }
        var user = customUserDetailsService.createUser(request);
        String token = jwtService.generateToken(user.getEmail());
        SignInResponse response = new SignInResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setId(user.getId());
        response.setAccessToken(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request, HttpServletResponse res) {
        var user = userRepository.findByEmail(request.getEmail().toLowerCase(Locale.ROOT)).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());
        SignInResponse response = new SignInResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setId(user.getId());
        String token = jwtService.generateToken(user.getEmail(), user.getId(), userDetails.getAuthorities());
        response.setAccessToken(token);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);
        res.addHeader("access_token", token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        String email = principal.getName();
        System.out.println("Email: " + email);
        return userRepository.findByEmail(email).orElse(null);
    }
}