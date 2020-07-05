package com.example.demo.services.converters;

import com.example.demo.models.Student;
import com.example.demo.models.StudyClass;
import com.example.demo.models.StudyGroup;
import com.example.demo.models.Subject;
import com.example.demo.models.enumerations.GradeLevel;
import com.example.demo.models.requests.IdRequest;
import com.example.demo.models.requests.StudentRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class StudentRequestToStudentConverterTest {
    private StudentRequestToStudentConverter converter;

    @BeforeEach
    void setUp() {
        converter = new StudentRequestToStudentConverter();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void convert_withAllFieldsSet() {
        final GradeLevel level10 = GradeLevel.LEVEL_10;
        final StudyClass studyClass = StudyClass.newBuilder().id(100499L).title("Test Class").build();
        final StudyGroup studyGroup = StudyGroup.newBuilder().id(100500L).build();
        final Subject testSubject = Subject.newBuilder().id(100501L).title("Test Subject").build();
        final StudentRequest request = StudentRequest.newBuilder()
                .gradeLevel(level10.getValue())
                .studyClass(studyClass.getId())
                .studyGroup(studyGroup.getId())
                .subjects(Collections.singletonList(new IdRequest(testSubject.getId())))
                .build();

        final Student result = converter.convert(request);

        assertThat(result)
                .hasFieldOrPropertyWithValue("gradeLevel", level10)
                .hasFieldOrProperty("studyClass")
                .hasFieldOrProperty("studyGroup")
                .hasFieldOrProperty("subjects");
        assertThat(result.getStudyClass().getId()).isEqualTo(studyClass.getId());
        assertThat(result.getStudyGroup().getId()).isEqualTo(studyGroup.getId());
        assertThat(result.getSubjects().get(0).getId()).isEqualTo(testSubject.getId());
    }

    @Test
    void convert_minimalSet() {
        final StudentRequest request = StudentRequest.newBuilder()
                .build();
        final Student student = Student.newBuilder()
                .gradeLevel(GradeLevel.LEVEL_NOT_DEFINED)
                .build();

        final Student result = converter.convert(request);

        assertThat(result).isEqualToIgnoringNullFields(student);
    }
}