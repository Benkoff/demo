package com.example.demo.models;

import com.example.demo.models.enumerations.GradeLevel;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "study_class")
public class StudyClass implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String title;

    @ElementCollection(targetClass = GradeLevel.class)
    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name = "study_class_grade_level")
    @Column(name = "grade_level")
    private List<GradeLevel> gradeLevels;

    @OneToMany(mappedBy = "studyClass")
    private final List<Student> students = new ArrayList<>();

    private StudyClass() {
        // empty
    }

    private StudyClass(final Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setGradeLevels(builder.gradeLevels);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public List<GradeLevel> getGradeLevels() {
        return this.gradeLevels;
    }

    public void setGradeLevels(final List<GradeLevel> gradeLevels) {
        this.gradeLevels = gradeLevels;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyClass that = (StudyClass) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(gradeLevels, that.gradeLevels) &&
                Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, gradeLevels, students);
    }

    @Override
    public String toString() {
        return "StudyClass{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", gradeLevels=" + gradeLevels +
                ", students={" + students.stream().map(Student::getId).map(String::valueOf).collect(Collectors.joining(", ")) + "}" +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String title;
        private List<GradeLevel> gradeLevels;

        private Builder() {
        }

        public Builder id(final Long val) {
            id = val;
            return this;
        }

        public Builder title(final String val) {
            title = val;
            return this;
        }

        public Builder gradeLevels(final List<GradeLevel> val) {
            gradeLevels = val;
            return this;
        }

        public StudyClass build() {
            return new StudyClass(this);
        }
    }
}
