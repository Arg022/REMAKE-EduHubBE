package com.school.service;

import com.school.model.StudyPath;
import com.school.repository.StudyPathRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudyPathService {

    private final StudyPathRepository studyPathRepository;

    @Autowired
    public StudyPathService(StudyPathRepository studyPathRepository) {
        this.studyPathRepository = studyPathRepository;
    }

    public List<StudyPath> getAllStudyPaths() {
        return studyPathRepository.findAll();
    }

    public StudyPath getStudyPathById(Long id) {
        Optional<StudyPath> studyPath = studyPathRepository.findById(id);
        if (studyPath.isPresent()) {
            return studyPath.get();
        } else {
            throw new EntityNotFoundException("StudyPath not found with id: " + id);
        }
    }

    public StudyPath saveStudyPath(StudyPath studyPath) {
        return studyPathRepository.save(studyPath);
    }

    public void deleteStudyPathById(Long id) {
        if (studyPathRepository.existsById(id)) {
            studyPathRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("StudyPath not found with id: " + id);
        }
    }

    public StudyPath updateStudyPath(Long id, StudyPath updatedStudyPath) {
        Optional<StudyPath> existingStudyPathOptional = studyPathRepository.findById(id);

        if (existingStudyPathOptional.isEmpty()) {
            throw new EntityNotFoundException("StudyPath not found with id: " + id);
        }

        StudyPath existingStudyPath = existingStudyPathOptional.get();
        existingStudyPath.setName(updatedStudyPath.getName());
        existingStudyPath.setDescription(updatedStudyPath.getDescription());

        return studyPathRepository.save(existingStudyPath);
    }

}