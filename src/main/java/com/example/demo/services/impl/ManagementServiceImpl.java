package com.example.demo.services.impl;

import com.example.demo.exceptions.DemoServiceException;
import com.example.demo.models.Student;
import com.example.demo.models.StudyGroup;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.StudyGroupRepository;
import com.example.demo.services.ManagementService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.constants.Beans.MANAGEMENT_SERVICE_ID;

@Service
public class ManagementServiceImpl implements ManagementService {
    private final StudyGroupRepository studyGroupRepository;
    private final StudentRepository studentRepository;

    public ManagementServiceImpl(final StudyGroupRepository studyGroupRepository, final StudentRepository studentRepository) {
        this.studyGroupRepository = studyGroupRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> populateStudyGroupsWithStudents(final List<Long> studyGroupIds, final List<Long> studentIds) {
        final List<StudyGroup> studyGroups = this.retrieveGroups(studyGroupIds);
        final List<Student> students = this.retrieveStudents(studentIds);
        final List<Student> unplaced = new ArrayList<>();
        final List<StudyGroup> studyGroupsPopulated = new ArrayList<>();

        for(final Student student : students) {
            final Optional<StudyGroup> selected = studyGroups.stream()
                    .filter(studyGroup -> studyGroup.getGradeLevels().contains(student.getGradeLevel()))
                    .filter(studyGroup -> studyGroup.getStudents().size() < studyGroup.getMaxStudents())
                    .filter(studyGroup -> studyGroup.getSubjects().containsAll(student.getSubjects()))
                    .findFirst();
            selected
                    .map(studyGroup -> {
                        student.addStudyGroup(studyGroup);
                        studyGroupsPopulated.add(studyGroup);
                        return studyGroup;
                    })
                    .orElseGet(() -> {
                        unplaced.add(student);
                        return null;
                    });
        }
        studyGroupsPopulated.stream()
                .filter(studyGroup -> studyGroup.getMinStudents() > studyGroup.getStudents().size())
                .forEach(studyGroup -> unplaced.addAll(studyGroup.getStudents()));
        unplaced.forEach(Student::removeStudyGroup);

        return unplaced;
    }

    private List<StudyGroup> retrieveGroups(final List<Long> studyGroupIds) {
        final List<StudyGroup> groups = this.studyGroupRepository.findAllById(studyGroupIds);
        if (groups.size() != studyGroupIds.size()) {
            throw new DemoServiceException(MANAGEMENT_SERVICE_ID, "Request contains invalid group id(s)");
        }
        return groups;
    }

    private List<Student> retrieveStudents(final List<Long> studentIds) {
        final List<Student> students = this.studentRepository.findAllById(studentIds);
        if (students.size() != studentIds.size()) {
            throw new DemoServiceException(MANAGEMENT_SERVICE_ID, "Request contains invalid student id(s)");
        }
        return students;
    }
}
