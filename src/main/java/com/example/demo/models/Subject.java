package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subject")
public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "subjects")
    private List<Student> students = new ArrayList<>();

    @ManyToMany(mappedBy = "subjects")
    private List<StudyGroup> studyGroups = new ArrayList<>();

    public Subject() {
        // empty
    }

    private Subject(final Builder builder) {
        id = builder.id;
        title = builder.title;
        students = builder.students;
        studyGroups = builder.studyGroups;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public List<StudyGroup> getStudyGroups() {
        return this.studyGroups;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) &&
                Objects.equals(title, subject.title) &&
                Objects.equals(students, subject.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, students);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", students=" + students +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String title;
        private List<Student> students = new ArrayList<>();
        private final List<StudyGroup> studyGroups = new ArrayList<>();

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

        public Builder students(final List<Student> val) {
            students = val != null ? val : new ArrayList<>();
            return this;
        }

        public Subject build() {
            return new Subject(this);
        }
    }
}
