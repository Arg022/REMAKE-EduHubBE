package com.school.service;

import com.school.model.Enrollment;
import com.school.repository.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(Long id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        if (enrollment.isPresent()) {
            return enrollment.get();
        } else {
            throw new EntityNotFoundException("Enrollment not found with id: " + id);
        }
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollmentById(Long id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Enrollment not found with id: " + id);
        }
    }

    public Enrollment updateEnrollment(Long id, Enrollment updatedEnrollment) {
        Optional<Enrollment> existingEnrollmentOptional = enrollmentRepository.findById(id);

        if (existingEnrollmentOptional.isEmpty()) {
            throw new EntityNotFoundException("Enrollment not found with id: " + id);
        }

        Enrollment existingEnrollment = existingEnrollmentOptional.get();
        existingEnrollment.setStudent(updatedEnrollment.getStudent());
        existingEnrollment.setCourse(updatedEnrollment.getCourse());
        existingEnrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
        existingEnrollment.setStatus(updatedEnrollment.getStatus());

        return enrollmentRepository.save(existingEnrollment);
    }

}