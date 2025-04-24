package com.school.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class LessonTest {
    private static Validator validator;
    private Lesson lesson;
    private Course course;
    private Teacher teacher;
    private Classroom classroom;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);

        teacher = new Teacher();
        teacher.setId(1L);

        classroom = new Classroom();
        classroom.setId(1L);

        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setCourse(course);
        lesson.setTeacher(teacher);
        lesson.setClassroom(classroom);
        lesson.setStartTime(LocalDateTime.now());
        lesson.setEndTime(LocalDateTime.now().plusHours(2));
    }

    @Test
    void validLessonShouldHaveNoViolations() {
        var violations = validator.validate(lesson);
        assertTrue(violations.isEmpty(), "Valid lesson should not have any violations");
    }

    @Test
    void whenCourseIsNull_shouldHaveViolation() {
        lesson.setCourse(null);
        var violations = validator.validate(lesson);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Course is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenTeacherIsNull_shouldHaveViolation() {
        lesson.setTeacher(null);
        var violations = validator.validate(lesson);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Teacher is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenClassroomIsNull_shouldHaveViolation() {
        lesson.setClassroom(null);
        var violations = validator.validate(lesson);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Classroom is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenStartTimeIsNull_shouldHaveViolation() {
        lesson.setStartTime(null);
        var violations = validator.validate(lesson);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Start time is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenEndTimeIsNull_shouldHaveViolation() {
        lesson.setEndTime(null);
        var violations = validator.validate(lesson);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("End time is required", violations.iterator().next().getMessage());
    }
}