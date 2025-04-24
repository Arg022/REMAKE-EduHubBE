package com.school.model;

import com.school.enums.Role;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    private static Validator validator;
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
        user.setPassword("password123");
        user.setRole(Role.STUDENT);
    }

    @Test
    void validUserShouldHaveNoViolations() {
        var violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Valid user should not have any violations");
    }

    @Test
    void uniqueConstraintsShouldBePresent() {
        assertTrue(isFieldAnnotatedAsUnique(Users.class, "username"),
                  "Username field should have @Column(unique=true) annotation");
    }

    private boolean isFieldAnnotatedAsUnique(Class<?> clazz, String fieldName) {
        try {
            var field = clazz.getDeclaredField(fieldName);
            var columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);
            return columnAnnotation != null && columnAnnotation.unique();
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}