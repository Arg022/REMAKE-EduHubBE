package com.school.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClassroomTest {
    private static Validator validator;
    private Classroom classroom;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        classroom = new Classroom();
        classroom.setId(1L);
        classroom.setName("Room A101");
        classroom.setCapacity(30);
        classroom.setLocation("Building A, First Floor");
    }

    @Test
    void validClassroomShouldHaveNoViolations() {
        var violations = validator.validate(classroom);
        assertTrue(violations.isEmpty(), "Valid classroom should not have any violations");
    }

    @Test
    void whenNameIsBlank_shouldHaveViolation() {
        classroom.setName("");
        var violations = validator.validate(classroom);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Classroom name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenCapacityIsNull_shouldHaveViolation() {
        classroom.setCapacity(null);
        var violations = validator.validate(classroom);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Capacity is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenCapacityIsLessThanOne_shouldHaveViolation() {
        classroom.setCapacity(0);
        var violations = validator.validate(classroom);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Capacity must be at least 1", violations.iterator().next().getMessage());
    }

    @Test
    void whenLocationIsBlank_shouldHaveViolation() {
        classroom.setLocation("");
        var violations = validator.validate(classroom);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Location cannot be blank", violations.iterator().next().getMessage());
    }
}