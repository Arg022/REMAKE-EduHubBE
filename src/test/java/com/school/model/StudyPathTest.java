package com.school.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudyPathTest {
    private static Validator validator;
    private StudyPath studyPath;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        studyPath = new StudyPath();
        studyPath.setId(1L);
        studyPath.setName("Computer Science");
        studyPath.setDescription("Bachelor's degree in Computer Science");
    }

    @Test
    void validStudyPathShouldHaveNoViolations() {
        var violations = validator.validate(studyPath);
        assertTrue(violations.isEmpty(), "Valid study path should not have any violations");
    }

    @Test
    void whenNameIsBlank_shouldHaveViolation() {
        studyPath.setName("");
        var violations = validator.validate(studyPath);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Study path name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenDescriptionIsBlank_shouldHaveViolation() {
        studyPath.setDescription("");
        var violations = validator.validate(studyPath);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Description cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenNameIsNull_shouldHaveViolation() {
        studyPath.setName(null);
        var violations = validator.validate(studyPath);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Study path name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenDescriptionIsNull_shouldHaveViolation() {
        studyPath.setDescription(null);
        var violations = validator.validate(studyPath);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Description cannot be blank", violations.iterator().next().getMessage());
    }
}