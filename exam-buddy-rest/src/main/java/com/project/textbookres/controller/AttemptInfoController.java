package com.project.textbookres.controller;

import com.project.textbookres.respository.UserRepository;
import com.project.textbookres.service.AttemptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/attemptInfo")
public class AttemptInfoController {
    @Autowired
    private AttemptInfoService attemptInfoService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<?> getAttemptInfo(@RequestParam long testId, Principal principal) {
        long userId = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found")).getId();
        return ResponseEntity.ok(attemptInfoService.getAttemptInfoByTestId(testId, userId));
    }
}
