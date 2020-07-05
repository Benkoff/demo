package com.example.demo.services.impl;

import com.example.demo.models.requests.StudentRequest;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.StudentService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.models.Student;

import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ConversionService conversionService;

    public StudentServiceImpl(final StudentRepository studentRepository, final ConversionService conversionService) {
        this.studentRepository = studentRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Student findOne(final UUID uuid) {
        return this.studentRepository.getOne(uuid);
    }

    @Override
    public Page<Student> findAll(final Pageable pageable) {
        return this.studentRepository.findAll(pageable);
    }

    @Override
    public UUID saveOne(final StudentRequest request) {
        final Student student = conversionService.convert(request, Student.class);
        final Student saved = this.studentRepository.save(student);
        return saved.getUuid();
    }
}
