package com.project.textbookres.controller;

import com.project.textbookres.service.AttemptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attemptInfo")
public class AttemptInfoController {
    @Autowired
    private AttemptInfoService attemptInfoService;

    @GetMapping()
    public ResponseEntity<?> getAttemptInfo(@RequestParam long testId) {
        return ResponseEntity.ok(attemptInfoService.getAttemptInfoByTestId(testId));
    }
}
