package com.school.service;

import com.school.model.Lesson;
import com.school.model.Course;
import com.school.model.Teacher;
import com.school.model.Classroom;
import com.school.repository.LessonRepository;
import com.school.dto.CreateLessonDTO;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private static final Logger logger = LoggerFactory.getLogger(LessonService.class);

    private final LessonRepository lessonRepository;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final ClassroomService classroomService;

    public LessonService(LessonRepository lessonRepository,
                        CourseService courseService,
                        TeacherService teacherService,
                        ClassroomService classroomService) {
        this.lessonRepository = lessonRepository;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.classroomService = classroomService;
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

    public Lesson saveLesson(CreateLessonDTO createLessonDTO) {
        logger.info("Creating new lesson for course {} with teacher {}", 
            createLessonDTO.getCourseId(), createLessonDTO.getTeacherId());

        // Fetch related entities
        Course course = courseService.getCourseById(createLessonDTO.getCourseId());
        Teacher teacher = teacherService.getTeacherById(createLessonDTO.getTeacherId());
        Classroom classroom = classroomService.getClassroomById(createLessonDTO.getClassroomId());

        // Create and save lesson
        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setTeacher(teacher);
        lesson.setClassroom(classroom);
        lesson.setStartTime(createLessonDTO.getStartTime());
        lesson.setEndTime(createLessonDTO.getEndTime());

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

    public Lesson updateLesson(Long id, CreateLessonDTO createLessonDTO) {
        logger.info("Attempting to update lesson with id: {}", id);
        Optional<Lesson> existingLessonOptional = lessonRepository.findById(id);

        if (existingLessonOptional.isEmpty()) {
            logger.error("Failed to update - lesson not found with id: {}", id);
            throw new ResourceNotFoundException("Lesson", "id", id);
        }

        // Fetch related entities
        Course course = courseService.getCourseById(createLessonDTO.getCourseId());
        Teacher teacher = teacherService.getTeacherById(createLessonDTO.getTeacherId());
        Classroom classroom = classroomService.getClassroomById(createLessonDTO.getClassroomId());

        Lesson existingLesson = existingLessonOptional.get();
        existingLesson.setCourse(course);
        existingLesson.setTeacher(teacher);
        existingLesson.setClassroom(classroom);
        existingLesson.setStartTime(createLessonDTO.getStartTime());
        existingLesson.setEndTime(createLessonDTO.getEndTime());

        Lesson savedLesson = lessonRepository.save(existingLesson);
        logger.debug("Lesson updated successfully with id: {}", savedLesson.getId());
        return savedLesson;
    }
}