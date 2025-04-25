package com.school.controller;

import com.school.model.Lesson;
import com.school.dto.LessonDTO;
import com.school.dto.CreateLessonDTO;
import com.school.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Lessons", description = "Endpoints for managing lessons")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    private LessonDTO convertToDTO(Lesson lesson) {
        return new LessonDTO(
            lesson.getId(),
            lesson.getCourse().getId(),
            lesson.getCourse().getName(),
            lesson.getTeacher().getId(),
            lesson.getTeacher().getFirstName() + " " + lesson.getTeacher().getLastName(),
            lesson.getClassroom().getId(),
            lesson.getClassroom().getName(),
            lesson.getStartTime(),
            lesson.getEndTime()
        );
    }

    @Operation(summary = "Get all lessons", description = "Retrieve a list of all lessons")
    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        List<LessonDTO> lessonDTOs = lessonService.getAllLessons()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(lessonDTOs);
    }

    @Operation(summary = "Get lesson by ID", description = "Retrieve a lesson by its ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(convertToDTO(lesson));
    }

    @Operation(summary = "Create a new lesson", description = "Add a new lesson to the system")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<LessonDTO> createLesson(@Valid @RequestBody CreateLessonDTO createLessonDTO) {
        Lesson savedLesson = lessonService.saveLesson(createLessonDTO);
        return ResponseEntity.ok(convertToDTO(savedLesson));
    }

    @Operation(summary = "Update a lesson", description = "Update the details of an existing lesson")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @Valid @RequestBody CreateLessonDTO createLessonDTO) {
        Lesson updatedLesson = lessonService.updateLesson(id, createLessonDTO);
        return ResponseEntity.ok(convertToDTO(updatedLesson));
    }

    @Operation(summary = "Delete a lesson", description = "Remove a lesson from the system")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLessonById(id);
        return ResponseEntity.noContent().build();
    }
}