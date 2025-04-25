package com.school.service;

import com.school.model.Subject;
import com.school.repository.SubjectRepository;
import com.school.exception.ResourceNotFoundException;
import com.school.exception.SubjectHasCoursesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private static final Logger logger = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() {
        logger.info("Retrieving all subjects");
        List<Subject> subjects = subjectRepository.findAll();
        logger.debug("Found {} subjects", subjects.size());
        return subjects;
    }

    public Subject getSubjectById(Long id) {
        logger.info("Retrieving subject with id: {}", id);
        return subjectRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Subject not found with id: {}", id);
                return new ResourceNotFoundException("Subject", "id", id);
            });
    }

    public Subject saveSubject(Subject subject) {
        logger.info("Saving new subject: {}", subject.getName());
        Subject savedSubject = subjectRepository.save(subject);
        logger.debug("Subject saved successfully with id: {}", savedSubject.getId());
        return savedSubject;
    }

    @Transactional
    public void deleteSubjectById(Long id) {
        logger.info("Attempting to delete subject with id: {}", id);
        
        Subject subject = subjectRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Failed to delete - subject not found with id: {}", id);
                return new ResourceNotFoundException("Subject", "id", id);
            });

        if (!subject.getCourses().isEmpty()) {
            int courseCount = subject.getCourses().size();
            logger.error("Cannot delete subject with id {} - has {} associated courses", id, courseCount);
            throw new SubjectHasCoursesException(id, courseCount);
        }

        subjectRepository.deleteById(id);
        logger.debug("Subject deleted successfully with id: {}", id);
    }

    public Subject updateSubject(Long id, Subject updatedSubject) {
        logger.info("Attempting to update subject with id: {}", id);
        Optional<Subject> existingSubjectOptional = subjectRepository.findById(id);

        if (existingSubjectOptional.isEmpty()) {
            logger.error("Failed to update - subject not found with id: {}", id);
            throw new ResourceNotFoundException("Subject", "id", id);
        }

        Subject existingSubject = existingSubjectOptional.get();
        existingSubject.setName(updatedSubject.getName());
        existingSubject.setDescription(updatedSubject.getDescription());

        Subject savedSubject = subjectRepository.save(existingSubject);
        logger.debug("Subject updated successfully with id: {}", savedSubject.getId());
        return savedSubject;
    }
}