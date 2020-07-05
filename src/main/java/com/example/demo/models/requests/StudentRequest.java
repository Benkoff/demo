package com.example.demo.models.requests;

import java.io.Serializable;
import java.util.List;

public class StudentRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer gradeLevel;
    private List<IdRequest> subjects;
    private Long studyClass;
    private Long studyGroup;

    public StudentRequest() {
        // empty
    }

    public StudentRequest(final Integer gradeLevel, final List<IdRequest> subjects, final Long studyClass, final Long studyGroup) {
        this.gradeLevel = gradeLevel;
        this.subjects = subjects;
        this.studyClass = studyClass;
        this.studyGroup = studyGroup;
    }

    private StudentRequest(final Builder builder) {
        gradeLevel = builder.gradeLevel;
        subjects = builder.subjects;
        studyClass = builder.studyClass;
        studyGroup = builder.studyGroup;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getGradeLevel() {
        return this.gradeLevel;
    }

    public List<IdRequest> getSubjects() {
        return this.subjects;
    }

    public Long getStudyClass() {
        return this.studyClass;
    }

    public Long getStudyGroup() {
        return this.studyGroup;
    }

    public static final class Builder {
        private Integer gradeLevel;
        private List<IdRequest> subjects;
        private Long studyClass;
        private Long studyGroup;

        private Builder() {
        }

        public Builder gradeLevel(final Integer val) {
            gradeLevel = val;
            return this;
        }

        public Builder subjects(final List<IdRequest> val) {
            subjects = val;
            return this;
        }

        public Builder studyClass(final Long val) {
            studyClass = val;
            return this;
        }

        public Builder studyGroup(final Long val) {
            studyGroup = val;
            return this;
        }

        public StudentRequest build() {
            return new StudentRequest(this);
        }
    }
}
