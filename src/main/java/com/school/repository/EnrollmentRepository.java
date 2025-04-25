package com.school.repository;

import com.school.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e FROM Enrollment e LEFT JOIN FETCH e.student LEFT JOIN FETCH e.course WHERE e.id = :id")
    Enrollment findByIdWithDetails(@Param("id") Long id);
}
