package com.school.controller;

import com.school.model.StudyPath;
import com.school.service.StudyPathService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Get all study paths", description = "Retrieve a list of all study paths")
    @GetMapping
    public ResponseEntity<List<StudyPath>> getAllStudyPaths() {
        return ResponseEntity.ok(studyPathService.getAllStudyPaths());
    }

    @Operation(summary = "Get study path by ID", description = "Retrieve a study path by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<StudyPath> getStudyPathById(@PathVariable Long id) {
        return ResponseEntity.ok(studyPathService.getStudyPathById(id));
    }

    @Operation(summary = "Create a new study path", description = "Add a new study path to the system")
    @PostMapping
    public ResponseEntity<StudyPath> createStudyPath(@RequestBody StudyPath studyPath) {
        return ResponseEntity.ok(studyPathService.saveStudyPath(studyPath));
    }

    @Operation(summary = "Update a study path", description = "Update the details of an existing study path")
    @PutMapping("/{id}")
    public ResponseEntity<StudyPath> updateStudyPath(@PathVariable Long id, @RequestBody StudyPath studyPath) {
        return ResponseEntity.ok(studyPathService.updateStudyPath(id, studyPath));
    }

    @Operation(summary = "Delete a study path", description = "Remove a study path from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyPath(@PathVariable Long id) {
        studyPathService.deleteStudyPathById(id);
        return ResponseEntity.noContent().build();
    }
}