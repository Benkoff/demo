package com.example.demo.models.responses;

import java.io.Serializable;
import java.util.List;

public class StudentResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String gradeLevel;
    private List<String> subjects;
    private String studyClass;
    private String studyGroup;

    public StudentResponse() {
        // empty
    }

    private StudentResponse(final Builder builder) {
        id = builder.id;
        gradeLevel = builder.gradeLevel;
        subjects = builder.subjects;
        studyClass = builder.studyClass;
        studyGroup = builder.studyGroup;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return this.id;
    }

    public String getGradeLevel() {
        return this.gradeLevel;
    }

    public List<String> getSubjects() {
        return this.subjects;
    }

    public String getStudyClass() {
        return this.studyClass;
    }

    public String getStudyGroup() {
        return this.studyGroup;
    }

    public static final class Builder {
        private Long id;
        private String gradeLevel;
        private List<String> subjects;
        private String studyClass;
        private String studyGroup;

        private Builder() {
        }

        public Builder id(final Long val) {
            id = val;
            return this;
        }

        public Builder gradeLevel(final String val) {
            gradeLevel = val;
            return this;
        }

        public Builder subjects(final List<String> val) {
            subjects = val;
            return this;
        }

        public Builder studyClass(final String val) {
            studyClass = val;
            return this;
        }

        public Builder studyGroup(final String val) {
            studyGroup = val;
            return this;
        }

        public StudentResponse build() {
            return new StudentResponse(this);
        }
    }
}
