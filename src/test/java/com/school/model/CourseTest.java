package com.school.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private static Validator validator;
    private Course course;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setName("Java Programming");
        course.setDescription("Learn Java Programming");
        course.setDurationHours(40);
        course.setCost(BigDecimal.valueOf(299.99));
    }

    @Test
    void validCourseShouldHaveNoViolations() {
        var violations = validator.validate(course);
        assertTrue(violations.isEmpty(), "Valid course should not have any violations");
    }

    @Test
    void whenNameIsBlank_shouldHaveViolation() {
        course.setName("");
        var violations = validator.validate(course);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Course name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenDescriptionIsBlank_shouldHaveViolation() {
        course.setDescription("");
        var violations = validator.validate(course);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Description cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenDurationIsNull_shouldHaveViolation() {
        course.setDurationHours(null);
        var violations = validator.validate(course);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Duration is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenDurationIsLessThanOne_shouldHaveViolation() {
        course.setDurationHours(0);
        var violations = validator.validate(course);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Duration must be at least 1 hour", violations.iterator().next().getMessage());
    }

    @Test
    void whenCostIsNull_shouldHaveViolation() {
        course.setCost(null);
        var violations = validator.validate(course);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Cost is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenCostIsZero_shouldHaveViolation() {
        course.setCost(BigDecimal.ZERO);
        var violations = validator.validate(course);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Cost must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void whenCostIsNegative_shouldHaveViolation() {
        course.setCost(BigDecimal.valueOf(-1));
        var violations = validator.validate(course);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Cost must be greater than 0", violations.iterator().next().getMessage());
    }
}