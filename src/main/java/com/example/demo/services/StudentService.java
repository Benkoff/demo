package com.example.demo.services;

import com.example.demo.models.Student;
import com.example.demo.models.requests.StudentRequest;
import com.example.demo.models.responses.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    StudentResponse findOne(Long uuid);

    Page<Student> findAll(Pageable pageable);

    Long saveOne(StudentRequest request);
}
