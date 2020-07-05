package com.example.demo.models.enumerations;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.constants.Beans.GRADE_LEVEL;

public enum GradeLevel {
    LEVEL_NOT_DEFINED(0){
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_1(1) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_2(2) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_3(3) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_4(4) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_5(5) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_6(6) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_7(7) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_8(8) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_9(9) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_10(10) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    }, LEVEL_11(11) {
        public String toString() {
            return GradeLevel.getString(this);
        }
    };

    private int value;
    private static final Map<Integer, GradeLevel> map = Arrays.stream(GradeLevel.values())
            .collect(Collectors.toMap(level -> level.value, level -> level));
    private static String getString(GradeLevel level) {
        return String.join(" ", GRADE_LEVEL, String.valueOf(level.getValue()));
    }

    GradeLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GradeLevel valueOf(int i) {
        return map.get(i);
    }
}
