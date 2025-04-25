package com.school.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class CreateStudyPathDTO {
    @NotBlank(message = "Study path name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotEmpty(message = "At least one course must be assigned to the study path")
    private Set<Long> courseIds;
}