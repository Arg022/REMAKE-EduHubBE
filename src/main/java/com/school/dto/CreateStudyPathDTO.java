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

    @NotNull(message = "Duration cannot be null")
    @Min(value = 1, message = "Duration must be at least 1 year")
    private Integer duration;

    @NotEmpty(message = "At least one course must be assigned to the study path")
    private Set<Long> courses;
}