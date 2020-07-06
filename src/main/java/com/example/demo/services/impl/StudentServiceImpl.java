package com.example.demo.services.impl;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.Student;
import com.example.demo.models.requests.StudentRequest;
import com.example.demo.models.responses.StudentResponse;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.StudentService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo.constants.Beans.STUDENTS_SERVICE;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ConversionService conversionService;

    public StudentServiceImpl(final StudentRepository studentRepository, final ConversionService conversionService) {
        this.studentRepository = studentRepository;
        this.conversionService = conversionService;
    }

    @Override
    public StudentResponse findOne(final Long id) {
        final Student student = Optional.of(this.studentRepository.getOne(id))
                .orElseThrow(() -> new BadRequestException(STUDENTS_SERVICE, "Student Not Found 404"));
        return this.conversionService.convert(student, StudentResponse.class);
    }

    @Override
    public Page<Student> findAll(final Pageable pageable) {
        return this.studentRepository.findAll(pageable);
    }

    @Override
    public Long saveOne(final StudentRequest request) {
        final Student student = Optional.ofNullable(conversionService.convert(request, Student.class))
                .orElseThrow(() -> new BadRequestException(STUDENTS_SERVICE, "Error converting request"));
        // TODO find & assign StudyClass, StudyGroup and Subjects in repositories, throw Exception if not found
        final Student saved = this.studentRepository.save(student);
        return saved.getId();
    }
}
