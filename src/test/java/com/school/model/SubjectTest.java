package com.school.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {
    private static Validator validator;
    private Subject subject;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        subject = new Subject();
        subject.setId(1L);
        subject.setName("Mathematics");
        subject.setDescription("Advanced Mathematics Course");
    }

    @Test
    void validSubjectShouldHaveNoViolations() {
        var violations = validator.validate(subject);
        assertTrue(violations.isEmpty(), "Valid subject should not have any violations");
    }

    @Test
    void whenNameIsBlank_shouldHaveViolation() {
        subject.setName("");
        var violations = validator.validate(subject);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Subject name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenDescriptionIsBlank_shouldHaveViolation() {
        subject.setDescription("");
        var violations = validator.validate(subject);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Description cannot be blank", violations.iterator().next().getMessage());
    }

    @Test 
    void whenNameIsNull_shouldHaveViolation() {
        subject.setName(null);
        var violations = validator.validate(subject);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Subject name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenDescriptionIsNull_shouldHaveViolation() {
        subject.setDescription(null);
        var violations = validator.validate(subject);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Description cannot be blank", violations.iterator().next().getMessage());
    }
}