package com.school.service;

import com.school.model.Lesson;
import com.school.repository.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson getLessonById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (lesson.isPresent()) {
            return lesson.get();
        } else {
            throw new EntityNotFoundException("Lesson not found with id: " + id);
        }
    }

    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public void deleteLessonById(Long id) {
        if (lessonRepository.existsById(id)) {
            lessonRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Lesson not found with id: " + id);
        }
    }

    public Lesson updateLesson(Long id, Lesson updatedLesson) {
        Optional<Lesson> existingLessonOptional = lessonRepository.findById(id);

        if (existingLessonOptional.isEmpty()) {
            throw new EntityNotFoundException("Lesson not found with id: " + id);
        }

        Lesson existingLesson = existingLessonOptional.get();
        existingLesson.setStartTime(updatedLesson.getStartTime());
        existingLesson.setEndTime(updatedLesson.getEndTime());
        existingLesson.setCourse(updatedLesson.getCourse());
        existingLesson.setTeacher(updatedLesson.getTeacher());
        existingLesson.setClassroom(updatedLesson.getClassroom());

        return lessonRepository.save(existingLesson);
    }

}