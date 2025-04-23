package com.school.service;

import com.school.model.Enrollment;
import com.school.repository.EnrollmentRepository;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getAllEnrollments() {
        logger.info("Retrieving all enrollments");
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        logger.debug("Found {} enrollments", enrollments.size());
        return enrollments;
    }

    public Enrollment getEnrollmentById(Long id) {
        logger.info("Retrieving enrollment with id: {}", id);
        return enrollmentRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Enrollment not found with id: {}", id);
                return new ResourceNotFoundException("Enrollment", "id", id);
            });
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        logger.info("Saving new enrollment for student {} in course {}", 
            enrollment.getStudent().getId(), enrollment.getCourse().getId());
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        logger.debug("Enrollment saved successfully with id: {}", savedEnrollment.getId());
        return savedEnrollment;
    }

    public void deleteEnrollmentById(Long id) {
        logger.info("Attempting to delete enrollment with id: {}", id);
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            logger.debug("Enrollment deleted successfully with id: {}", id);
        } else {
            logger.error("Failed to delete - enrollment not found with id: {}", id);
            throw new ResourceNotFoundException("Enrollment", "id", id);
        }
    }

    public Enrollment updateEnrollment(Long id, Enrollment updatedEnrollment) {
        logger.info("Attempting to update enrollment with id: {}", id);
        Optional<Enrollment> existingEnrollmentOptional = enrollmentRepository.findById(id);

        if (existingEnrollmentOptional.isEmpty()) {
            logger.error("Failed to update - enrollment not found with id: {}", id);
            throw new ResourceNotFoundException("Enrollment", "id", id);
        }

        Enrollment existingEnrollment = existingEnrollmentOptional.get();
        existingEnrollment.setStudent(updatedEnrollment.getStudent());
        existingEnrollment.setCourse(updatedEnrollment.getCourse());
        existingEnrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
        existingEnrollment.setStatus(updatedEnrollment.getStatus());

        Enrollment savedEnrollment = enrollmentRepository.save(existingEnrollment);
        logger.debug("Enrollment updated successfully with id: {}", savedEnrollment.getId());
        return savedEnrollment;
    }
}