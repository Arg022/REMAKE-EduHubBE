package com.school.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationTest {
    private static Validator validator;
    private Evaluation evaluation;
    private Enrollment enrollment;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        // Create a valid enrollment
        Student student = new Student();
        student.setId(1L);
        
        Course course = new Course();
        course.setId(1L);
        
        enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        
        // Create a valid evaluation
        evaluation = new Evaluation();
        evaluation.setId(1L);
        evaluation.setEnrollment(enrollment);
        evaluation.setGrade(85);
        evaluation.setEvaluationDate(LocalDate.now());
        evaluation.setNotes("Good performance");
    }

    @Test
    void validEvaluationShouldHaveNoViolations() {
        var violations = validator.validate(evaluation);
        assertTrue(violations.isEmpty(), "Valid evaluation should not have any violations");
    }

    @Test
    void whenEnrollmentIsNull_shouldHaveViolation() {
        evaluation.setEnrollment(null);
        var violations = validator.validate(evaluation);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Enrollment is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenGradeIsNull_shouldHaveViolation() {
        evaluation.setGrade(null);
        var violations = validator.validate(evaluation);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Grade is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenGradeIsLessThanZero_shouldHaveViolation() {
        evaluation.setGrade(-1);
        var violations = validator.validate(evaluation);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Grade must be at least 0", violations.iterator().next().getMessage());
    }

    @Test
    void whenGradeIsGreaterThan100_shouldHaveViolation() {
        evaluation.setGrade(101);
        var violations = validator.validate(evaluation);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Grade must be at most 100", violations.iterator().next().getMessage());
    }

    @Test
    void whenEvaluationDateIsNull_shouldHaveViolation() {
        evaluation.setEvaluationDate(null);
        var violations = validator.validate(evaluation);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Evaluation date is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenNotesAreNull_shouldNotHaveViolation() {
        evaluation.setNotes(null);
        var violations = validator.validate(evaluation);
        assertTrue(violations.isEmpty(), "Notes are optional and should not cause violations");
    }
}