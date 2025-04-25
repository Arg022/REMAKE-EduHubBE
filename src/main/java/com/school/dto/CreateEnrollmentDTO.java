package com.school.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class CreateEnrollmentDTO {
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    @NotNull(message = "Course ID is required")
    private Long courseId;
    
    @NotNull(message = "Enrollment date is required")
    private LocalDate enrollmentDate;
    
    @NotBlank(message = "Status cannot be blank")
    private String status;
}