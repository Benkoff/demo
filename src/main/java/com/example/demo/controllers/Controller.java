package com.example.demo.controllers;

import com.example.demo.models.Student;
import com.example.demo.models.requests.StudentRequest;
import com.example.demo.models.responses.StudentResponse;
import com.example.demo.services.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class Controller {
    private final StudentService studentService;

    public Controller(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "/students/{studentUuid}")
    public ResponseEntity<StudentResponse> getOne(@PathVariable(value = "studentUuid") final Long studentUuid) {
        return ResponseEntity.ok(studentService.findOne(studentUuid));
    }

    @GetMapping(path = "/students")
    public ResponseEntity<Page<Student>> getOne(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok(studentService.findAll(pageable));
    }

    @PostMapping(path = "/students")
    public ResponseEntity<Long> addOne(@RequestBody final StudentRequest request) {
        return ResponseEntity.ok(studentService.saveOne(request));
    }

}
