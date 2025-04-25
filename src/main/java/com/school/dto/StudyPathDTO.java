package com.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyPathDTO {
    private Long id;
    private String name;
    private String description;
    private Integer duration;
}