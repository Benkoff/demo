package com.example.demo.services.converters;

import com.example.demo.models.Student;
import com.example.demo.models.StudyClass;
import com.example.demo.models.StudyGroup;
import com.example.demo.models.Subject;
import com.example.demo.models.enumerations.GradeLevel;
import com.example.demo.models.responses.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class StudentToStudentResponseConverterTest {
    private StudentToStudentResponseConverter converter;

    @BeforeEach
    void setUp() {
        converter = new StudentToStudentResponseConverter();
    }

    @Test
    void convert() {
        final GradeLevel level10 = GradeLevel.LEVEL_10;
        final StudyClass studyClass = StudyClass.newBuilder().title("Test Class").build();
        final StudyGroup studyGroup = StudyGroup.newBuilder().id(100500L).build();
        final Subject testSubject = Subject.newBuilder().id(100501L).title("Test Subject").build();
        final StudentResponse response = StudentResponse.newBuilder()
                .id(1L)
                .gradeLevel(level10.toString())
                .studyClass(studyClass.getTitle())
                .studyGroup(String.valueOf(studyGroup.getId()))
                .subjects(Collections.singletonList(testSubject.getTitle()))
                .build();
        final Student student = Student.newBuilder()
                .id(1L)
                .gradeLevel(level10)
                .studyClass(studyClass)
                .studyGroup(studyGroup)
                .subjects(Collections.singletonList(testSubject))
                .build();

        final StudentResponse result = converter.convert(student);

        assertThat(result).isEqualToIgnoringNullFields(response);
    }
}