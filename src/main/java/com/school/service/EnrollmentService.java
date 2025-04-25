package com.school.service;

import com.school.model.Enrollment;
import com.school.model.Student;
import com.school.model.Course;
import com.school.repository.EnrollmentRepository;
import com.school.dto.CreateEnrollmentDTO;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                           StudentService studentService,
                           CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
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

    public Enrollment saveEnrollment(CreateEnrollmentDTO createEnrollmentDTO) {
        logger.info("Creating new enrollment for student {} in course {}", 
            createEnrollmentDTO.getStudentId(), createEnrollmentDTO.getCourseId());
        
        Student student = studentService.getStudentById(createEnrollmentDTO.getStudentId());
        Course course = courseService.getCourseById(createEnrollmentDTO.getCourseId());

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(createEnrollmentDTO.getEnrollmentDate());
        enrollment.setStatus(createEnrollmentDTO.getStatus());
        
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

    public Enrollment updateEnrollment(Long id, CreateEnrollmentDTO createEnrollmentDTO) {
        logger.info("Attempting to update enrollment with id: {}", id);
        Optional<Enrollment> existingEnrollmentOptional = enrollmentRepository.findById(id);

        if (existingEnrollmentOptional.isEmpty()) {
            logger.error("Failed to update - enrollment not found with id: {}", id);
            throw new ResourceNotFoundException("Enrollment", "id", id);
        }

        Student student = studentService.getStudentById(createEnrollmentDTO.getStudentId());
        Course course = courseService.getCourseById(createEnrollmentDTO.getCourseId());

        Enrollment existingEnrollment = existingEnrollmentOptional.get();
        existingEnrollment.setStudent(student);
        existingEnrollment.setCourse(course);
        existingEnrollment.setEnrollmentDate(createEnrollmentDTO.getEnrollmentDate());
        existingEnrollment.setStatus(createEnrollmentDTO.getStatus());

        Enrollment savedEnrollment = enrollmentRepository.save(existingEnrollment);
        logger.debug("Enrollment updated successfully with id: {}", savedEnrollment.getId());
        return savedEnrollment;
    }
}