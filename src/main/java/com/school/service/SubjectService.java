package com.school.service;

import com.school.model.Subject;
import com.school.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            return subject.get();
        } else {
            throw new EntityNotFoundException("Subject not found with id: " + id);
        }
    }

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteSubjectById(Long id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Subject not found with id: " + id);
        }
    }

    public Subject updateSubject(Long id, Subject updatedSubject) {
        Optional<Subject> existingSubjectOptional = subjectRepository.findById(id);

        if (existingSubjectOptional.isEmpty()) {
            throw new EntityNotFoundException("Subject not found with id: " + id);
        }

        Subject existingSubject = existingSubjectOptional.get();
        existingSubject.setName(updatedSubject.getName());
        existingSubject.setDescription(updatedSubject.getDescription());

        return subjectRepository.save(existingSubject);
    }

}