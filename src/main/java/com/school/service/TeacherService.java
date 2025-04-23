package com.school.service;

import com.school.model.Teacher;
import com.school.repository.TeacherRepository;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        logger.info("Retrieving all teachers");
        List<Teacher> teachers = teacherRepository.findAll();
        logger.debug("Found {} teachers", teachers.size());
        return teachers;
    }

    public Teacher getTeacherById(Long id) {
        logger.info("Retrieving teacher with id: {}", id);
        return teacherRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Teacher not found with id: {}", id);
                return new ResourceNotFoundException("Teacher", "id", id);
            });
    }

    public Teacher saveTeacher(Teacher teacher) {
        logger.info("Saving new teacher: {}", teacher.getEmail());
        Teacher savedTeacher = teacherRepository.save(teacher);
        logger.debug("Teacher saved successfully with id: {}", savedTeacher.getId());
        return savedTeacher;
    }

    public void deleteTeacherById(Long id) {
        logger.info("Attempting to delete teacher with id: {}", id);
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            logger.debug("Teacher deleted successfully with id: {}", id);
        } else {
            logger.error("Failed to delete - teacher not found with id: {}", id);
            throw new ResourceNotFoundException("Teacher", "id", id);
        }
    }

    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        logger.info("Attempting to update teacher with id: {}", id);
        Optional<Teacher> existingTeacherOptional = teacherRepository.findById(id);

        if (existingTeacherOptional.isEmpty()) {
            logger.error("Failed to update - teacher not found with id: {}", id);
            throw new ResourceNotFoundException("Teacher", "id", id);
        }

        Teacher existingTeacher = existingTeacherOptional.get();
        existingTeacher.setFirstName(updatedTeacher.getFirstName());
        existingTeacher.setLastName(updatedTeacher.getLastName());
        existingTeacher.setEmail(updatedTeacher.getEmail());
        existingTeacher.setPhone(updatedTeacher.getPhone());
        existingTeacher.setTeachingSubject(updatedTeacher.getTeachingSubject());

        Teacher savedTeacher = teacherRepository.save(existingTeacher);
        logger.debug("Teacher updated successfully with id: {}", savedTeacher.getId());
        return savedTeacher;
    }
}