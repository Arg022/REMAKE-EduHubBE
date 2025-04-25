package com.school.controller;

import com.school.model.Evaluation;
import com.school.dto.EvaluationDTO;
import com.school.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Evaluations", description = "Endpoints for managing evaluations")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    private EvaluationDTO convertToDTO(Evaluation evaluation) {
        return new EvaluationDTO(
            evaluation.getId(),
            evaluation.getEnrollment().getId(),
            evaluation.getEnrollment().getStudent().getFirstName() + " " + evaluation.getEnrollment().getStudent().getLastName(),
            evaluation.getEnrollment().getCourse().getName(),
            evaluation.getGrade(),
            evaluation.getEvaluationDate(),
            evaluation.getNotes()
        );
    }

    @Operation(summary = "Get all evaluations", description = "Retrieve a list of all evaluations")
    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<EvaluationDTO>> getAllEvaluations() {
        List<EvaluationDTO> evaluationDTOs = evaluationService.getAllEvaluations()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(evaluationDTOs);
    }

    @Operation(summary = "Get evaluation by ID", description = "Retrieve an evaluation by its ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<EvaluationDTO> getEvaluationById(@PathVariable Long id) {
        Evaluation evaluation = evaluationService.getEvaluationById(id);
        return ResponseEntity.ok(convertToDTO(evaluation));
    }

    @Operation(summary = "Create a new evaluation", description = "Add a new evaluation to the system")
    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<EvaluationDTO> createEvaluation(@RequestBody Evaluation evaluation) {
        Evaluation savedEvaluation = evaluationService.saveEvaluation(evaluation);
        return ResponseEntity.ok(convertToDTO(savedEvaluation));
    }

    @Operation(summary = "Update an evaluation", description = "Update the details of an existing evaluation")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<EvaluationDTO> updateEvaluation(@PathVariable Long id, @RequestBody Evaluation evaluation) {
        Evaluation updatedEvaluation = evaluationService.updateEvaluation(id, evaluation);
        return ResponseEntity.ok(convertToDTO(updatedEvaluation));
    }

    @Operation(summary = "Delete an evaluation", description = "Remove an evaluation from the system")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluationById(id);
        return ResponseEntity.noContent().build();
    }
}