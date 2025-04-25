package com.school.service;

import com.school.dto.CreateTeacherDTO;
import com.school.model.Teacher;
import com.school.model.Users;
import com.school.repository.TeacherRepository;
import com.school.exception.ResourceNotFoundException;
import com.school.exception.TeacherHasLessonsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.HashSet;

@Service
public class TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;
    private final UserService userService;

    public TeacherService(TeacherRepository teacherRepository, UserService userService) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
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

    public Teacher saveTeacher(CreateTeacherDTO createTeacherDTO) {
        logger.info("Creating new teacher with email: {}", createTeacherDTO.getEmail());
        
        Users user = userService.getUserById(createTeacherDTO.getUserId());
        
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setFirstName(createTeacherDTO.getFirstName());
        teacher.setLastName(createTeacherDTO.getLastName());
        teacher.setEmail(createTeacherDTO.getEmail());
        teacher.setPhone(createTeacherDTO.getPhone());
        teacher.setTeachingSubject(createTeacherDTO.getTeachingSubject());
        teacher.setLessons(new HashSet<>());
        
        Teacher savedTeacher = teacherRepository.save(teacher);
        logger.debug("Teacher saved successfully with id: {}", savedTeacher.getId());
        return savedTeacher;
    }

    @Transactional
    public void deleteTeacherById(Long id) {
        logger.info("Attempting to delete teacher with id: {}", id);
        
        Teacher teacher = teacherRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Failed to delete - teacher not found with id: {}", id);
                return new ResourceNotFoundException("Teacher", "id", id);
            });

        if (!teacher.getLessons().isEmpty()) {
            int lessonCount = teacher.getLessons().size();
            logger.error("Cannot delete teacher with id {} - has {} active lessons", id, lessonCount);
            throw new TeacherHasLessonsException(id, lessonCount);
        }

        teacherRepository.deleteById(id);
        logger.debug("Teacher deleted successfully with id: {}", id);
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