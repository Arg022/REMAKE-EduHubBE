package com.school.controller;

import com.school.model.StudyPath;
import com.school.dto.StudyPathDTO;
import com.school.dto.CourseDTO;
import com.school.service.StudyPathService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

@Tag(name = "Study Paths", description = "Endpoints for managing study paths")
@RestController
@RequestMapping("/study-paths")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudyPathController {

    private final StudyPathService studyPathService;

    @Autowired
    public StudyPathController(StudyPathService studyPathService) {
        this.studyPathService = studyPathService;
    }

    private StudyPathDTO convertToDTO(StudyPath studyPath) {
        Set<CourseDTO> courseDTOs = studyPath.getCourses().stream()
            .map(course -> new CourseDTO(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getDurationHours(),
                course.getCost()
            ))
            .collect(Collectors.toSet());

        return new StudyPathDTO(
            studyPath.getId(),
            studyPath.getName(),
            studyPath.getDescription(),
            courseDTOs
        );
    }

    @Operation(summary = "Get all study paths", description = "Retrieve a list of all study paths")
    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<StudyPathDTO>> getAllStudyPaths() {
        List<StudyPathDTO> studyPathDTOs = studyPathService.getAllStudyPaths()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(studyPathDTOs);
    }

    @Operation(summary = "Get study path by ID", description = "Retrieve a study path by its ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<StudyPathDTO> getStudyPathById(@PathVariable Long id) {
        StudyPath studyPath = studyPathService.getStudyPathById(id);
        return ResponseEntity.ok(convertToDTO(studyPath));
    }

    @Operation(summary = "Create a new study path", description = "Add a new study path to the system")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudyPathDTO> createStudyPath(@RequestBody StudyPath studyPath) {
        StudyPath savedStudyPath = studyPathService.saveStudyPath(studyPath);
        return ResponseEntity.ok(convertToDTO(savedStudyPath));
    }

    @Operation(summary = "Update a study path", description = "Update the details of an existing study path")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudyPathDTO> updateStudyPath(@PathVariable Long id, @RequestBody StudyPath studyPath) {
        StudyPath updatedStudyPath = studyPathService.updateStudyPath(id, studyPath);
        return ResponseEntity.ok(convertToDTO(updatedStudyPath));
    }

    @Operation(summary = "Delete a study path", description = "Remove a study path from the system")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudyPath(@PathVariable Long id) {
        studyPathService.deleteStudyPathById(id);
        return ResponseEntity.noContent().build();
    }
}