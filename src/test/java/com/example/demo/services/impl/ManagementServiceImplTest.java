package com.example.demo.services.impl;

import com.example.demo.exceptions.DemoServiceException;
import com.example.demo.models.Student;
import com.example.demo.models.StudyGroup;
import com.example.demo.models.Subject;
import com.example.demo.models.enumerations.GradeLevel;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.StudyGroupRepository;
import com.example.demo.services.ManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManagementServiceImplTest {
    private StudyGroupRepository studyGroupRepository;
    private StudentRepository studentRepository;
    private ManagementService managementService;

    private List<Student> students;
    private List<StudyGroup> groups;

    @BeforeEach
    void setUp() {
        this.studyGroupRepository = mock(StudyGroupRepository.class);
        this.studentRepository = mock(StudentRepository.class);
        this.managementService = new ManagementServiceImpl(studyGroupRepository, studentRepository);
        this.populateTestData();
        when(studentRepository.findAllById(anyList())).thenReturn(students);
        when(studyGroupRepository.findAllById(anyList())).thenReturn(groups);
    }

    @Test
    void populateStudyGroupsWithStudents_shouldReturnEmptyList() {
        final List<Long> studentIds = students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
        final List<Long> studyGroupIds = groups.stream()
                .map(StudyGroup::getId)
                .collect(Collectors.toList());

        final List<Student> unplaced = managementService.populateStudyGroupsWithStudents(studyGroupIds, studentIds);

        assertThat(unplaced).containsExactlyInAnyOrder(students.get(0), students.get(3), students.get(5));
        assertThat(groups.get(0).getStudents()).containsExactlyInAnyOrder(students.get(6));
        assertThat(groups.get(1).getStudents()).containsExactlyInAnyOrder(students.get(1), students.get(2));
        assertThat(groups.get(2).getStudents()).containsExactlyInAnyOrder(students.get(4));
        assertThat(groups.get(3).getStudents()).isEmpty();
    }

    @Test
    void populateStudyGroupsWithStudents_shouldThrowException_whenGroupsNotFound() {
        final List<Long> studentIds = students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
        final List<Long> studyGroupIds = groups.stream()
                .map(StudyGroup::getId)
                .collect(Collectors.toList());
        studyGroupIds.add(100500L);

        DemoServiceException thrown = assertThrows(DemoServiceException.class,
                () -> managementService.populateStudyGroupsWithStudents(studyGroupIds, studentIds));

        assertThat(thrown).hasMessage("Request contains invalid group id(s)");
    }

    @Test
    void populateStudyGroupsWithStudents_shouldThrowException_whenStudentsNotFound() {
        final List<Long> studentIds = students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
        final List<Long> studyGroupIds = groups.stream()
                .map(StudyGroup::getId)
                .collect(Collectors.toList());
        studentIds.add(100500L);

        DemoServiceException thrown = assertThrows(DemoServiceException.class,
                () -> managementService.populateStudyGroupsWithStudents(studyGroupIds, studentIds));

        assertThat(thrown).hasMessage("Request contains invalid student id(s)");
    }

    private void populateTestData() {
        final Subject math = Subject.newBuilder().title("Math").build();
        final Subject english = Subject.newBuilder().title("English").build();
        final Subject swedish = Subject.newBuilder().title("Swedish").build();
        final Subject french = Subject.newBuilder().title("French").build();
        final Student student0 = Student.newBuilder()
                .id(800L)
                .gradeLevel(GradeLevel.LEVEL_1)
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        final Student student1 = Student.newBuilder()
                .id(801L)
                .gradeLevel(GradeLevel.LEVEL_8)
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        final Student student2 = Student.newBuilder()
                .id(802L)
                .gradeLevel(GradeLevel.LEVEL_8)
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        final Student student3 = Student.newBuilder()
                .id(803L)
                .gradeLevel(GradeLevel.LEVEL_8)
                .subjects(Arrays.asList(math, french, swedish))
                .build();
        final Student student4 = Student.newBuilder()
                .id(804L)
                .gradeLevel(GradeLevel.LEVEL_8)
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        final Student student5 = Student.newBuilder()
                .id(805L)
                .gradeLevel(GradeLevel.LEVEL_8)
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        final Student student6 = Student.newBuilder()
                .id(706L)
                .gradeLevel(GradeLevel.LEVEL_7)
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        students = Arrays.asList(student0, student1, student2, student3, student4, student5, student6);
        final StudyGroup studyGroup0 = StudyGroup.newBuilder()
                .id(167L)
                .minStudents(1)
                .maxStudents(3)
                .gradeLevels(Arrays.asList(GradeLevel.LEVEL_6, GradeLevel.LEVEL_7))
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        final StudyGroup studyGroup1 = StudyGroup.newBuilder()
                .id(1891L)
                .minStudents(1)
                .maxStudents(2)
                .gradeLevels(Arrays.asList(GradeLevel.LEVEL_8, GradeLevel.LEVEL_9))
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        final StudyGroup studyGroup2 = StudyGroup.newBuilder()
                .id(1892L)
                .minStudents(1)
                .maxStudents(1)
                .gradeLevels(Arrays.asList(GradeLevel.LEVEL_8, GradeLevel.LEVEL_9))
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        final StudyGroup studyGroup3 = StudyGroup.newBuilder()
                .id(289L)
                .minStudents(2)
                .maxStudents(3)
                .gradeLevels(Arrays.asList(GradeLevel.LEVEL_8, GradeLevel.LEVEL_9))
                .subjects(Arrays.asList(math, english, swedish))
                .build();
        groups = Arrays.asList(studyGroup0, studyGroup1, studyGroup2, studyGroup3);
    }
}
