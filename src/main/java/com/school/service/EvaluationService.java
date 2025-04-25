package com.school.service;

import com.school.model.Evaluation;
import com.school.model.Enrollment;
import com.school.repository.EvaluationRepository;
import com.school.repository.EnrollmentRepository;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    private static final Logger logger = LoggerFactory.getLogger(EvaluationService.class);

    private final EvaluationRepository evaluationRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository, EnrollmentRepository enrollmentRepository) {
        this.evaluationRepository = evaluationRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Evaluation> getAllEvaluations() {
        logger.info("Retrieving all evaluations");
        List<Evaluation> evaluations = evaluationRepository.findAll();
        logger.debug("Found {} evaluations", evaluations.size());
        return evaluations;
    }

    public Evaluation getEvaluationById(Long id) {
        logger.info("Retrieving evaluation with id: {}", id);
        return evaluationRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Evaluation not found with id: {}", id);
                return new ResourceNotFoundException("Evaluation", "id", id);
            });
    }

    public Evaluation saveEvaluation(Evaluation evaluation) {
        logger.info("Saving new evaluation for enrollment {}", evaluation.getEnrollment().getId());
        Evaluation savedEvaluation = evaluationRepository.save(evaluation);
        logger.debug("Evaluation saved successfully with id: {}", savedEvaluation.getId());
        return savedEvaluation;
    }

    public void deleteEvaluationById(Long id) {
        logger.info("Attempting to delete evaluation with id: {}", id);
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
            logger.debug("Evaluation deleted successfully with id: {}", id);
        } else {
            logger.error("Failed to delete - evaluation not found with id: {}", id);
            throw new ResourceNotFoundException("Evaluation", "id", id);
        }
    }

    public Evaluation updateEvaluation(Long id, Evaluation updatedEvaluation) {
        logger.info("Attempting to update evaluation with id: {}", id);
        Optional<Evaluation> existingEvaluationOptional = evaluationRepository.findById(id);

        if (existingEvaluationOptional.isEmpty()) {
            logger.error("Failed to update - evaluation not found with id: {}", id);
            throw new ResourceNotFoundException("Evaluation", "id", id);
        }

        Evaluation existingEvaluation = existingEvaluationOptional.get();
        
        Enrollment enrollment = enrollmentRepository.findByIdWithDetails(updatedEvaluation.getEnrollment().getId());
        if (enrollment == null) {
            throw new ResourceNotFoundException("Enrollment", "id", updatedEvaluation.getEnrollment().getId());
        }

        existingEvaluation.setGrade(updatedEvaluation.getGrade());
        existingEvaluation.setEvaluationDate(updatedEvaluation.getEvaluationDate());
        existingEvaluation.setNotes(updatedEvaluation.getNotes());
        existingEvaluation.setEnrollment(enrollment);

        Evaluation savedEvaluation = evaluationRepository.save(existingEvaluation);
        logger.debug("Evaluation updated successfully with id: {}", savedEvaluation.getId());
        return savedEvaluation;
    }
}