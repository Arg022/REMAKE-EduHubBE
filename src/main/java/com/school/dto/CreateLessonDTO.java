package com.school.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class CreateLessonDTO {
    @NotNull(message = "Course ID is required")
    private Long courseId;
    
    @NotNull(message = "Teacher ID is required")
    private Long teacherId;
    
    @NotNull(message = "Classroom ID is required")
    private Long classroomId;
    
    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
}