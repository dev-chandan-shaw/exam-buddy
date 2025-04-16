package com.project.textbookres.respository;

import com.project.textbookres.model.Hello;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloRepository extends JpaRepository<Hello, Long> {}
