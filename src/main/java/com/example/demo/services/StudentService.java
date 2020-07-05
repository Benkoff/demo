package com.example.demo.services;

import com.example.demo.models.Student;
import com.example.demo.models.requests.StudentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StudentService {
    Student findOne(UUID uuid);
    Page<Student> findAll(Pageable pageable);

    UUID saveOne(StudentRequest request);
}
