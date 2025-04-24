package com.school.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    private static Validator validator;
    private Teacher teacher;
    private Users user;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);
        user.setUsername("teacher1");

        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setUser(user);
        teacher.setFirstName("John");
        teacher.setLastName("Smith");
        teacher.setEmail("john.smith@example.com");
        teacher.setPhone("1234567890");
        teacher.setTeachingSubject("Mathematics");
    }

    @Test
    void validTeacherShouldHaveNoViolations() {
        var violations = validator.validate(teacher);
        assertTrue(violations.isEmpty(), "Valid teacher should not have any violations");
    }

    @Test
    void whenUserIsNull_shouldHaveViolation() {
        teacher.setUser(null);
        var violations = validator.validate(teacher);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("User account is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenFirstNameIsBlank_shouldHaveViolation() {
        teacher.setFirstName("");
        var violations = validator.validate(teacher);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("First name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenLastNameIsBlank_shouldHaveViolation() {
        teacher.setLastName("");
        var violations = validator.validate(teacher);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Last name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenEmailIsInvalid_shouldHaveViolation() {
        teacher.setEmail("invalid-email");
        var violations = validator.validate(teacher);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Email should be valid", violations.iterator().next().getMessage());
    }

    @Test
    void whenEmailIsBlank_shouldHaveViolation() {
        teacher.setEmail("");
        var violations = validator.validate(teacher);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Email cannot be blank")));
    }

    @Test
    void whenPhoneIsBlank_shouldHaveViolation() {
        teacher.setPhone("");
        var violations = validator.validate(teacher);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Phone cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenTeachingSubjectIsBlank_shouldHaveViolation() {
        teacher.setTeachingSubject("");
        var violations = validator.validate(teacher);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Teaching subject cannot be blank", violations.iterator().next().getMessage());
    }
}