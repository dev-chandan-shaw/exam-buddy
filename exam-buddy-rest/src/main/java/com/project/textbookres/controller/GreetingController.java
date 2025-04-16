package com.project.textbookres.controller;

import com.project.textbookres.model.Greeting;
import com.project.textbookres.model.Hello;
import com.project.textbookres.respository.GreetingRepository;
import com.project.textbookres.respository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    @Autowired
    private GreetingRepository greetingRepository;

    @Autowired
    private HelloRepository helloRepository;

    @GetMapping
    public ResponseEntity<?> getAllGreetings() {
        return ResponseEntity.ok(greetingRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> addGreeting() {
        Greeting greeting = new Greeting();
        List<Hello> hellos = new ArrayList<>();
        for (int i=0; i<100; i++) {
            Hello hello = new Hello();
            hello.setName("Hello " + i);
            greeting.getHellos().add(hello);
            hellos.add(hello);
        }
        helloRepository.saveAll(hellos);
        greetingRepository.save(greeting);
        return ResponseEntity.ok(greeting);
    }

}
