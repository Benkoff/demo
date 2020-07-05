package com.example.demo.models;

import com.example.demo.models.enumerations.GradeLevel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "student")
public class Student extends Person {
    @NotNull
    @Column(name = "grade_level")
    @Enumerated(EnumType.ORDINAL)
    private GradeLevel gradeLevel;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private final List<Subject> subjects = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_class")
    private StudyClass studyClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group")
    private StudyGroup studyGroup;

    private Student() {
        // empty
    }

    private Student(final Builder builder) {
        id = builder.id;
        setGradeLevel(builder.gradeLevel);
        if (builder.studyClass == null) {
            this.removeStudyClass();
        } else {
            this.addStudyClass(builder.studyClass);
        }
        if (builder.studyGroup == null) {
            this.removeStudyGroup();
        } else {
            this.addStudyGroup(builder.studyGroup);
        }
        builder.subjects.forEach(subject -> Optional.ofNullable(subject).ifPresent(this::addSubject));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return super.id;
    }

    public GradeLevel getGradeLevel() {
        return this.gradeLevel;
    }

    public void setGradeLevel(final GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public List<Subject> getSubjects() {
        return this.subjects;
    }

    public StudyClass getStudyClass() {
        return this.studyClass;
    }

    public StudyGroup getStudyGroup() {
        return this.studyGroup;
    }

    public Student addSubject(Subject subject) {
        if (subject != null) {
            this.subjects.add(subject);
            subject.getStudents().add(this);
        }
        return this;
    }

    public Student removeSubject(final Subject subject) {
        if (subject != null) {
            this.subjects.remove(subject);
            subject.getStudents().remove(this);
        }
        return this;
    }

    public Student addStudyClass(StudyClass studyClass) {
        if (studyClass != null) {
            this.studyClass = studyClass;
            studyClass.getStudents().add(this);
        }
        return this;
    }

    public Student removeStudyClass() {
        if (this.studyClass != null) {
            this.studyClass.getStudents().remove(this);
            this.studyClass = null;
        }
        return this;
    }

    public Student addStudyGroup(StudyGroup studyGroup) {
        if (studyGroup != null) {
            this.studyGroup = studyGroup;
            studyGroup.getStudents().add(this);
        }
        return this;
    }

    public Student removeStudyGroup() {
        if (this.studyGroup != null) {
            this.studyGroup.getStudents().remove(this);
            this.studyGroup = null;
        }
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return gradeLevel == student.gradeLevel &&
                Objects.equals(subjects, student.subjects) &&
                Objects.equals(studyClass, student.studyClass) &&
                Objects.equals(studyGroup, student.studyGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gradeLevel, subjects, studyClass, studyGroup);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", gradeLevel=" + gradeLevel +
                ", subjects={" + subjects.stream().map(Subject::getTitle).map(String::valueOf).collect(Collectors.joining(", ")) + "}" +
                ", studyClass=" + studyClass +
                ", studyGroup=" + studyGroup +
                '}';
    }

    public static final class Builder {
        private Long id;
        private GradeLevel gradeLevel;
        private StudyClass studyClass;
        private StudyGroup studyGroup;
        private List<Subject> subjects = new ArrayList<>();

        private Builder() {
        }

        public Builder id(final Long val) {
            id = val;
            return this;
        }

        public Builder gradeLevel(final GradeLevel val) {
            gradeLevel = val != null ? val : GradeLevel.LEVEL_NOT_DEFINED;
            return this;
        }

        public Builder studyClass(final StudyClass val) {
            studyClass = val;
            return this;
        }

        public Builder studyGroup(final StudyGroup val) {
            studyGroup = val;
            return this;
        }

        public Builder subjects(final List<Subject> val) {
            subjects = val != null ? val : new ArrayList<>();
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
