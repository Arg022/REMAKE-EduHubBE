package com.school.service;

import com.school.model.Evaluation;
import com.school.repository.EvaluationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public Evaluation getEvaluationById(Long id) {
        Optional<Evaluation> evaluation = evaluationRepository.findById(id);
        if (evaluation.isPresent()) {
            return evaluation.get();
        } else {
            throw new EntityNotFoundException("Evaluation not found with id: " + id);
        }
    }

    public Evaluation saveEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public void deleteEvaluationById(Long id) {
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Evaluation not found with id: " + id);
        }
    }

    public Evaluation updateEvaluation(Long id, Evaluation updatedEvaluation) {
        Optional<Evaluation> existingEvaluationOptional = evaluationRepository.findById(id);

        if (existingEvaluationOptional.isEmpty()) {
            throw new EntityNotFoundException("Evaluation not found with id: " + id);
        }

        Evaluation existingEvaluation = existingEvaluationOptional.get();
        existingEvaluation.setGrade(updatedEvaluation.getGrade());
        existingEvaluation.setEvaluationDate(updatedEvaluation.getEvaluationDate());
        existingEvaluation.setNotes(updatedEvaluation.getNotes());
        existingEvaluation.setEnrollment(updatedEvaluation.getEnrollment());

        return evaluationRepository.save(existingEvaluation);
    }

}