package com.example.demo.services.converters;

import com.example.demo.models.Student;
import com.example.demo.models.Subject;
import com.example.demo.models.responses.StudentResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StudentToStudentResponseConverter implements Converter<Student, StudentResponse> {

    @Override
    public StudentResponse convert(final Student source) {
        return StudentResponse.newBuilder()
                .id(source.getId())
                .gradeLevel(source.getGradeLevel().toString())
                .studyClass(source.getStudyClass().getTitle())
                .studyGroup(String.valueOf(source.getStudyGroup().getId()))
                .subjects(source.getSubjects().stream().map(Subject::getTitle).collect(Collectors.toList()))
                .build();
    }
}
