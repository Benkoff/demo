package com.example.demo.services.converters;

import com.example.demo.models.Student;
import com.example.demo.models.requests.StudentRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentRequestToStudentConverter implements Converter<StudentRequest, Student> {

    @Override
    public Student convert(final StudentRequest source) {

        return null;
    }
}
