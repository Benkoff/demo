package com.example.demo.services.impl;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.Student;
import com.example.demo.models.requests.StudentRequest;
import com.example.demo.models.responses.StudentResponse;
import com.example.demo.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static com.example.demo.constants.Beans.STUDENTS_SERVICE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {
    private StudentRepository studentRepository;
    private ConversionService conversionService;

    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        conversionService = mock(ConversionService.class);
        studentService = new StudentServiceImpl(studentRepository, conversionService);
    }

    @Test
    void findOne() {
        when(studentRepository.getOne(anyLong())).thenReturn(Student.newBuilder().id(100L).build());
        when(conversionService.convert(any(), eq(StudentResponse.class))).thenReturn(StudentResponse.newBuilder().id(100L).build());

        studentService.findOne(100L);

        verify(studentRepository, times(1)).getOne(anyLong());
        verify(conversionService, times(1)).convert(any(), eq(StudentResponse.class));
    }

    @Test
    void findOne_shouldThrowException() {
        when(studentRepository.getOne(anyLong())).thenReturn(null);
        final String message = "Student Not Found 404";

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> studentService.findOne(100L));

        assertThat(thrown.getIdentifier()).isEqualTo(STUDENTS_SERVICE);
        assertThat(thrown.getMessage()).isEqualTo(message);
        verify(studentRepository, times(1)).getOne(anyLong());
        verify(conversionService, never()).convert(any(), eq(StudentResponse.class));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void findAll() {
        final PageImpl<Student> students = new PageImpl<>(Collections.singletonList(Student.newBuilder().id(100L).build()));
        when(studentRepository.findAll(Pageable.unpaged())).thenReturn(students);

        final Page<Student> page = studentService.findAll(Pageable.unpaged());

        assertThat(page.get().findFirst().get().getId()).isEqualTo(100L);
    }

    @Test
    void saveOne() {
        final Student student = Student.newBuilder().id(100L).build();
        when(conversionService.convert(any(), eq(Student.class))).thenReturn(student);
        when(this.studentRepository.save(any())).thenReturn(student);

        final Long one = studentService.saveOne(StudentRequest.newBuilder().build());

        assertThat(one).isEqualTo(student.getId());
        verify(conversionService, times(1)).convert(any(), eq(Student.class));
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    void saveOne_shouldThrowException() {
        when(conversionService.convert(any(), eq(Student.class))).thenReturn(null);
        final String message = "Error converting request";

        BadRequestException thrown = assertThrows(BadRequestException.class,
                () -> studentService.saveOne(StudentRequest.newBuilder().build()));

        assertThat(thrown.getIdentifier()).isEqualTo(STUDENTS_SERVICE);
        assertThat(thrown.getMessage()).isEqualTo(message);
        verify(conversionService, times(1)).convert(any(), eq(Student.class));
        verify(studentRepository, never()).save(any());
    }
}