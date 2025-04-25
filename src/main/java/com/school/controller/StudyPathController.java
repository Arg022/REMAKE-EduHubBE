package com.school.controller;

import com.school.model.StudyPath;
import com.school.dto.StudyPathDTO;
import com.school.service.StudyPathService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Study Paths", description = "Endpoints for managing study paths")
@RestController
@RequestMapping("/study-paths")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudyPathController {

    private final StudyPathService studyPathService;

    public StudyPathController(StudyPathService studyPathService) {
        this.studyPathService = studyPathService;
    }

    private StudyPathDTO convertToDTO(StudyPath studyPath) {
        return new StudyPathDTO(
            studyPath.getId(),
            studyPath.getName(),
            studyPath.getDescription(),
            studyPath.getDuration()
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
    public ResponseEntity<StudyPathDTO> createStudyPath(@Valid @RequestBody StudyPath studyPath) {
        StudyPath savedStudyPath = studyPathService.saveStudyPath(studyPath);
        return ResponseEntity.ok(convertToDTO(savedStudyPath));
    }

    @Operation(summary = "Update a study path", description = "Update the details of an existing study path")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudyPathDTO> updateStudyPath(@PathVariable Long id, @Valid @RequestBody StudyPath studyPath) {
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