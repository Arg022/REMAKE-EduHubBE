package com.school.service;

import com.school.model.Classroom;
import com.school.repository.ClassroomRepository;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {
    private static final Logger logger = LoggerFactory.getLogger(ClassroomService.class);

    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAllClassrooms() {
        logger.info("Retrieving all classrooms");
        List<Classroom> classrooms = classroomRepository.findAll();
        logger.debug("Found {} classrooms", classrooms.size());
        return classrooms;
    }

    public Classroom getClassroomById(Long id) {
        logger.info("Retrieving classroom with id: {}", id);
        return classroomRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Classroom not found with id: {}", id);
                return new ResourceNotFoundException("Classroom", "id", id);
            });
    }

    public Classroom saveClassroom(Classroom classroom) {
        logger.info("Saving new classroom: {}", classroom.getName());
        Classroom savedClassroom = classroomRepository.save(classroom);
        logger.debug("Classroom saved successfully with id: {}", savedClassroom.getId());
        return savedClassroom;
    }

    public void deleteClassroomById(Long id) {
        logger.info("Attempting to delete classroom with id: {}", id);
        if (classroomRepository.existsById(id)) {
            classroomRepository.deleteById(id);
            logger.debug("Classroom deleted successfully with id: {}", id);
        } else {
            logger.error("Failed to delete - classroom not found with id: {}", id);
            throw new ResourceNotFoundException("Classroom", "id", id);
        }
    }

    public Classroom updateClassroom(Long id, Classroom updatedClassroom) {
        logger.info("Attempting to update classroom with id: {}", id);
        Optional<Classroom> existingClassroomOptional = classroomRepository.findById(id);

        if (existingClassroomOptional.isEmpty()) {
            logger.error("Failed to update - classroom not found with id: {}", id);
            throw new ResourceNotFoundException("Classroom", "id", id);
        }

        Classroom existingClassroom = existingClassroomOptional.get();
        existingClassroom.setName(updatedClassroom.getName());
        existingClassroom.setCapacity(updatedClassroom.getCapacity());
        existingClassroom.setLocation(updatedClassroom.getLocation());

        Classroom savedClassroom = classroomRepository.save(existingClassroom);
        logger.debug("Classroom updated successfully with id: {}", savedClassroom.getId());
        return savedClassroom;
    }
}