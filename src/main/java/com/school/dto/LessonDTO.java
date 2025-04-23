package com.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private Long courseId;
    private String courseName;
    private Long teacherId;
    private String teacherName;
    private Long classroomId;
    private String classroomName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}