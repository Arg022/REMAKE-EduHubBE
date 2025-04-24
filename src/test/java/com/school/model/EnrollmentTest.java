package com.school.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {
    private static Validator validator;
    private Enrollment enrollment;
    private Student student;
    private Course course;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);

        course = new Course();
        course.setId(1L);

        enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("Active");
    }

    @Test
    void validEnrollmentShouldHaveNoViolations() {
        var violations = validator.validate(enrollment);
        assertTrue(violations.isEmpty(), "Valid enrollment should not have any violations");
    }

    @Test
    void whenStudentIsNull_shouldHaveViolation() {
        enrollment.setStudent(null);
        var violations = validator.validate(enrollment);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Student is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenCourseIsNull_shouldHaveViolation() {
        enrollment.setCourse(null);
        var violations = validator.validate(enrollment);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Course is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenEnrollmentDateIsNull_shouldHaveViolation() {
        enrollment.setEnrollmentDate(null);
        var violations = validator.validate(enrollment);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Enrollment date is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenStatusIsBlank_shouldHaveViolation() {
        enrollment.setStatus("");
        var violations = validator.validate(enrollment);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Status cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenStatusIsNull_shouldHaveViolation() {
        enrollment.setStatus(null);
        var violations = validator.validate(enrollment);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Status cannot be blank", violations.iterator().next().getMessage());
    }
}