package com.school.service;

import com.school.model.StudyPath;
import com.school.repository.StudyPathRepository;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudyPathService {
    private static final Logger logger = LoggerFactory.getLogger(StudyPathService.class);

    private final StudyPathRepository studyPathRepository;

    @Autowired
    public StudyPathService(StudyPathRepository studyPathRepository) {
        this.studyPathRepository = studyPathRepository;
    }

    public List<StudyPath> getAllStudyPaths() {
        logger.info("Retrieving all study paths");
        List<StudyPath> studyPaths = studyPathRepository.findAll();
        logger.debug("Found {} study paths", studyPaths.size());
        return studyPaths;
    }

    public StudyPath getStudyPathById(Long id) {
        logger.info("Retrieving study path with id: {}", id);
        return studyPathRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Study path not found with id: {}", id);
                return new ResourceNotFoundException("StudyPath", "id", id);
            });
    }

    public StudyPath saveStudyPath(StudyPath studyPath) {
        logger.info("Saving new study path: {}", studyPath.getName());
        StudyPath savedStudyPath = studyPathRepository.save(studyPath);
        logger.debug("Study path saved successfully with id: {}", savedStudyPath.getId());
        return savedStudyPath;
    }

    public void deleteStudyPathById(Long id) {
        logger.info("Attempting to delete study path with id: {}", id);
        if (studyPathRepository.existsById(id)) {
            studyPathRepository.deleteById(id);
            logger.debug("Study path deleted successfully with id: {}", id);
        } else {
            logger.error("Failed to delete - study path not found with id: {}", id);
            throw new ResourceNotFoundException("StudyPath", "id", id);
        }
    }

    public StudyPath updateStudyPath(Long id, StudyPath updatedStudyPath) {
        logger.info("Attempting to update study path with id: {}", id);
        Optional<StudyPath> existingStudyPathOptional = studyPathRepository.findById(id);

        if (existingStudyPathOptional.isEmpty()) {
            logger.error("Failed to update - study path not found with id: {}", id);
            throw new ResourceNotFoundException("StudyPath", "id", id);
        }

        StudyPath existingStudyPath = existingStudyPathOptional.get();
        existingStudyPath.setName(updatedStudyPath.getName());
        existingStudyPath.setDescription(updatedStudyPath.getDescription());
        existingStudyPath.setCourses(updatedStudyPath.getCourses());

        StudyPath savedStudyPath = studyPathRepository.save(existingStudyPath);
        logger.debug("Study path updated successfully with id: {}", savedStudyPath.getId());
        return savedStudyPath;
    }
}