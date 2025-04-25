package com.school.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class CreateStudentDTO {
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    
    @NotBlank(message = "Phone cannot be blank")
    private String phone;
    
    @NotBlank(message = "Address cannot be blank")
    private String address;
    
    @NotBlank(message = "Tax code cannot be blank")
    private String code;
    
    @NotNull(message = "Study path ID is required")
    private Long studyPathId;
}