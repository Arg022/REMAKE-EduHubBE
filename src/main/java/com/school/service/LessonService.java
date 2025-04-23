package com.school.service;

import com.school.model.Lesson;
import com.school.repository.LessonRepository;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private static final Logger logger = LoggerFactory.getLogger(LessonService.class);

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> getAllLessons() {
        logger.info("Retrieving all lessons");
        List<Lesson> lessons = lessonRepository.findAll();
        logger.debug("Found {} lessons", lessons.size());
        return lessons;
    }

    public Lesson getLessonById(Long id) {
        logger.info("Retrieving lesson with id: {}", id);
        return lessonRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Lesson not found with id: {}", id);
                return new ResourceNotFoundException("Lesson", "id", id);
            });
    }

    public Lesson saveLesson(Lesson lesson) {
        logger.info("Saving new lesson for course {} with teacher {}", 
            lesson.getCourse().getId(), lesson.getTeacher().getId());
        Lesson savedLesson = lessonRepository.save(lesson);
        logger.debug("Lesson saved successfully with id: {}", savedLesson.getId());
        return savedLesson;
    }

    public void deleteLessonById(Long id) {
        logger.info("Attempting to delete lesson with id: {}", id);
        if (lessonRepository.existsById(id)) {
            lessonRepository.deleteById(id);
            logger.debug("Lesson deleted successfully with id: {}", id);
        } else {
            logger.error("Failed to delete - lesson not found with id: {}", id);
            throw new ResourceNotFoundException("Lesson", "id", id);
        }
    }

    public Lesson updateLesson(Long id, Lesson updatedLesson) {
        logger.info("Attempting to update lesson with id: {}", id);
        Optional<Lesson> existingLessonOptional = lessonRepository.findById(id);

        if (existingLessonOptional.isEmpty()) {
            logger.error("Failed to update - lesson not found with id: {}", id);
            throw new ResourceNotFoundException("Lesson", "id", id);
        }

        Lesson existingLesson = existingLessonOptional.get();
        existingLesson.setStartTime(updatedLesson.getStartTime());
        existingLesson.setEndTime(updatedLesson.getEndTime());
        existingLesson.setCourse(updatedLesson.getCourse());
        existingLesson.setTeacher(updatedLesson.getTeacher());
        existingLesson.setClassroom(updatedLesson.getClassroom());

        Lesson savedLesson = lessonRepository.save(existingLesson);
        logger.debug("Lesson updated successfully with id: {}", savedLesson.getId());
        return savedLesson;
    }
}