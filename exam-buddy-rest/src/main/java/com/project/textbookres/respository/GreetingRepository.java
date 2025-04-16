package com.project.textbookres.respository;

import com.project.textbookres.model.Greeting;
import com.project.textbookres.model.Hello;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, Long> {
}

