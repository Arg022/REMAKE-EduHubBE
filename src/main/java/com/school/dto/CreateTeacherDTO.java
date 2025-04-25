package com.school.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateTeacherDTO {
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Phone cannot be blank")
    private String phone;
    
    @NotBlank(message = "Teaching subject cannot be blank")
    private String teachingSubject;
}