package com.school.service;

import com.school.model.Teacher;
import com.school.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            return teacher.get();
        } else {
            throw new EntityNotFoundException("Teacher not found with id: " + id);
        }
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void deleteTeacherById(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Teacher not found with id: " + id);
        }
    }

    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        Optional<Teacher> existingTeacherOptional = teacherRepository.findById(id);

        if (existingTeacherOptional.isEmpty()) {
            throw new EntityNotFoundException("Teacher not found with id: " + id);
        }

        Teacher existingTeacher = existingTeacherOptional.get();
        existingTeacher.setFirstName(updatedTeacher.getFirstName());
        existingTeacher.setLastName(updatedTeacher.getLastName());
        existingTeacher.setEmail(updatedTeacher.getEmail());
        existingTeacher.setPhone(updatedTeacher.getPhone());
        existingTeacher.setTeachingSubject(updatedTeacher.getTeachingSubject());

        return teacherRepository.save(existingTeacher);
    }

}