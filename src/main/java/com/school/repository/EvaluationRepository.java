package com.school.repository;

import com.school.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Evaluation findByEnrollmentId(Long enrollmentId);
}
