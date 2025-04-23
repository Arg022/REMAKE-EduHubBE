package com.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDTO {
    private Long id;
    private Long enrollmentId;
    private String studentName;
    private String courseName;
    private Integer grade;
    private LocalDate evaluationDate;
    private String notes;
}