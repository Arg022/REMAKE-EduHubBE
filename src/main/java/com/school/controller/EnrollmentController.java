package com.school.controller;

import com.school.model.Enrollment;
import com.school.dto.EnrollmentDTO;
import com.school.dto.CreateEnrollmentDTO;
import com.school.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Enrollments", description = "Endpoints for managing enrollments")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
            enrollment.getId(),
            enrollment.getStudent().getId(),
            enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName(),
            enrollment.getCourse().getId(),
            enrollment.getCourse().getName(),
            enrollment.getEnrollmentDate(),
            enrollment.getStatus()
        );
    }

    @Operation(summary = "Get all enrollments", description = "Retrieve a list of all enrollments")
    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        List<EnrollmentDTO> enrollmentDTOs = enrollmentService.getAllEnrollments()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(enrollmentDTOs);
    }

    @Operation(summary = "Get enrollment by ID", description = "Retrieve an enrollment by its ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(convertToDTO(enrollment));
    }

    @Operation(summary = "Create a new enrollment", description = "Add a new enrollment to the system")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody CreateEnrollmentDTO createEnrollmentDTO) {
        Enrollment savedEnrollment = enrollmentService.saveEnrollment(createEnrollmentDTO);
        return ResponseEntity.ok(convertToDTO(savedEnrollment));
    }

    @Operation(summary = "Update an enrollment", description = "Update the details of an existing enrollment")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(@PathVariable Long id, @Valid @RequestBody CreateEnrollmentDTO createEnrollmentDTO) {
        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(id, createEnrollmentDTO);
        return ResponseEntity.ok(convertToDTO(updatedEnrollment));
    }

    @Operation(summary = "Delete an enrollment", description = "Remove an enrollment from the system")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollmentById(id);
        return ResponseEntity.noContent().build();
    }
}