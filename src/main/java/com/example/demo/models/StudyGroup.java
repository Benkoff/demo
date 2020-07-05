package com.example.demo.models;

import com.example.demo.models.enumerations.GradeLevel;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "study_group")
public class StudyGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ElementCollection(targetClass = GradeLevel.class)
    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name = "study_group_grade_level")
    @Column(name = "grade_level")
    private List<GradeLevel> gradeLevels;

    @Column(name = "min_students")
    private Integer minStudents;

    @Column(name = "max_students")
    private Integer maxStudents;

    @OneToMany(mappedBy = "studyGroup")
    private final List<Student> students = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(name = "study_group_subject",
            joinColumns = @JoinColumn(name = "study_group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private final List<Subject> subjects = new ArrayList<>();

    private StudyGroup() {
        // empty
    }

    private StudyGroup(final Builder builder) {
        id = builder.id;
        gradeLevels = builder.gradeLevels;
        minStudents = builder.minStudents;
        maxStudents = builder.maxStudents;
        builder.subjects.forEach(subject -> Optional.ofNullable(subject).ifPresent(this::addSubject));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return this.id;
    }

    public List<GradeLevel> getGradeLevels() {
        return this.gradeLevels;
    }

    public Integer getMinStudents() {
        return this.minStudents;
    }

    public Integer getMaxStudents() {
        return this.maxStudents;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public List<Subject> getSubjects() {
        return this.subjects;
    }

    public StudyGroup addSubject(Subject subject) {
        if (subject != null) {
            this.subjects.add(subject);
            subject.getStudyGroups().add(this);
        }
        return this;
    }

    public StudyGroup removeSubject(final Subject subject) {
        if (subject != null) {
            this.subjects.remove(subject);
            subject.getStudyGroups().remove(this);
        }
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyGroup studyGroup = (StudyGroup) o;
        return Objects.equals(id, studyGroup.id) &&
                Objects.equals(gradeLevels, studyGroup.gradeLevels) &&
                Objects.equals(minStudents, studyGroup.minStudents) &&
                Objects.equals(maxStudents, studyGroup.maxStudents) &&
                Objects.equals(students, studyGroup.students)&&
                Objects.equals(subjects, studyGroup.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gradeLevels, minStudents, maxStudents, students, subjects);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", gradeLevels=" + gradeLevels +
                ", minStudents=" + minStudents +
                ", maxStudents=" + maxStudents +
                ", students={" + students.stream().map(Student::getId).map(String::valueOf).collect(Collectors.joining(", ")) + "}" +
                ", subjects={" + subjects.stream().map(Subject::getTitle).map(String::valueOf).collect(Collectors.joining(", ")) + "}" +
                '}';
    }

    public static final class Builder {
        private Long id;
        private List<GradeLevel> gradeLevels;
        private Integer minStudents;
        private Integer maxStudents;
        private List<Subject> subjects = new ArrayList<>();

        private Builder() {
        }

        public Builder id(final Long val) {
            id = val;
            return this;
        }

        public Builder gradeLevels(final List<GradeLevel> val) {
            gradeLevels = val;
            return this;
        }

        public Builder minStudents(final Integer val) {
            minStudents = val;
            return this;
        }

        public Builder maxStudents(final Integer val) {
            maxStudents = val;
            return this;
        }

        public Builder subjects(final List<Subject> val) {
            subjects = val != null ? val : new ArrayList<>();
            return this;
        }

        public StudyGroup build() {
            return new StudyGroup(this);
        }
    }
}
