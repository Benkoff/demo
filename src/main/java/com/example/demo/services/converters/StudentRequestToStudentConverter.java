package com.example.demo.services.converters;

import com.example.demo.models.Student;
import com.example.demo.models.StudyClass;
import com.example.demo.models.StudyGroup;
import com.example.demo.models.Subject;
import com.example.demo.models.enumerations.GradeLevel;
import com.example.demo.models.requests.StudentRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class StudentRequestToStudentConverter implements Converter<StudentRequest, Student> {

    @Override
    public Student convert(final StudentRequest source) {
        return Student.newBuilder()
                .gradeLevel(GradeLevel.valueOf(source.getGradeLevel() != null ? source.getGradeLevel() : 0))
                .studyClass(source.getStudyClass() != null
                            ? StudyClass.newBuilder().id(source.getStudyClass()).build()
                            : null)
                .studyGroup(source.getStudyGroup() != null
                            ? StudyGroup.newBuilder() .id(source.getStudyGroup()).build()
                            : null)
                .subjects(source.getSubjects() != null && !source.getSubjects().isEmpty()
                          ? source.getSubjects().stream().map(s -> Subject.newBuilder().id(s.getId()).build()).collect(Collectors.toList())
                          : new ArrayList<>())
                .build();
    }
}
