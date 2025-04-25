package com.school.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private static Validator validator;
    private Student student;
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
        user.setUsername("testuser");

        student = new Student();
        student.setId(1L);
        student.setUser(user);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setDateOfBirth(LocalDate.of(2000, 1, 1));
        student.setEmail("john.doe@example.com");
        student.setPhone("1234567890");
        student.setAddress("123 Main St");
        student.setCode("ABCDEF12G34H567I");
        student.setRegistrationDate(LocalDate.now());
    }

    @Test
    void validStudentShouldHaveNoViolations() {
        var violations = validator.validate(student);
        assertTrue(violations.isEmpty(), "Valid student should not have any violations");
    }

    @Test
    void whenFirstNameIsBlank_shouldHaveViolation() {
        student.setFirstName("");
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("First name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenLastNameIsBlank_shouldHaveViolation() {
        student.setLastName("");
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Last name cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenEmailIsInvalid_shouldHaveViolation() {
        student.setEmail("invalid-email");
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Email should be valid", violations.iterator().next().getMessage());
    }

    @Test
    void whenEmailIsBlank_shouldHaveViolation() {
        student.setEmail("");
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Email cannot be blank")));
    }

    @Test
    void whenPhoneIsBlank_shouldHaveViolation() {
        student.setPhone("");
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Phone cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenAddressIsBlank_shouldHaveViolation() {
        student.setAddress("");
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Address cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenTaxCodeIsBlank_shouldHaveViolation() {
        student.setCode("");
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Tax code cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenDateOfBirthIsNull_shouldHaveViolation() {
        student.setDateOfBirth(null);
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Date of birth is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenRegistrationDateIsNull_shouldHaveViolation() {
        student.setRegistrationDate(null);
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Registration date is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenUserIsNull_shouldHaveViolation() {
        student.setUser(null);
        var violations = validator.validate(student);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("User account is required", violations.iterator().next().getMessage());
    }
}