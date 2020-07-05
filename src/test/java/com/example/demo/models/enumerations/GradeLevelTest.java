package com.example.demo.models.enumerations;

import org.junit.jupiter.api.Test;

import static com.example.demo.constants.Beans.GRADE_LEVEL;
import static com.example.demo.constants.Beans.UNDEFINED;
import static org.assertj.core.api.Assertions.assertThat;

class GradeLevelTest {

    @Test
    void toString_shouldReturnString() {
        GradeLevel level10 = GradeLevel.LEVEL_10;

        final String result = level10.toString();

        assertThat(result).isEqualTo(GRADE_LEVEL + " " + 10);
    }

    @Test
    void name() {
        GradeLevel level10 = GradeLevel.valueOf(3);

        final String result = level10.name();

        assertThat(result).isEqualTo("LEVEL_" + 3);
    }

    @Test
    void name_undefined() {
        GradeLevel level10 = GradeLevel.valueOf(0);

        final String result = level10.name();

        assertThat(result).isEqualTo("LEVEL_" + UNDEFINED);
    }
}