package com.school.service;

import com.school.model.Classroom;
import com.school.repository.ClassroomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public Classroom getClassroomById(Long id) {
        Optional<Classroom> classroom = classroomRepository.findById(id);
        if (classroom.isPresent()) {
            return classroom.get();
        } else {
            throw new EntityNotFoundException("Classroom not found with id: " + id);
        }
    }

    public Classroom saveClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public void deleteClassroomById(Long id) {
        if (classroomRepository.existsById(id)) {
            classroomRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Classroom not found with id: " + id);
        }
    }

    public Classroom updateClassroom(Long id, Classroom updatedClassroom) {
        Optional<Classroom> existingClassroomOptional = classroomRepository.findById(id);

        if (existingClassroomOptional.isEmpty()) {
            throw new EntityNotFoundException("Classroom not found with id: " + id);
        }

        Classroom existingClassroom = existingClassroomOptional.get();
        existingClassroom.setName(updatedClassroom.getName());
        existingClassroom.setCapacity(updatedClassroom.getCapacity());
        existingClassroom.setLocation(updatedClassroom.getLocation());

        return classroomRepository.save(existingClassroom);
    }

}