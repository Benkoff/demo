package com.example.demo.services;

import com.example.demo.models.Student;

import java.util.List;

public interface ManagementService {
    /**
     * Populates existing groups with students based on the subjects they are supposed to read and what classes they belong to.
     *     • Supports minimum and maximum number of students in a group.
     *     • Returns unplaced students.
     *
     * @param studyGroupIds - identifiers of groups to be populated
     * @param studentIds - identifiers of students to populate
     * @return List of unplaced students
     */
    List<Student> populateStudyGroupsWithStudents(List<Long> studyGroupIds, List<Long> studentIds);
}
