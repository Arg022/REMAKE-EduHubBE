package com.school.service;

import com.school.model.Course;
import com.school.repository.CourseRepository;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        logger.info("Retrieving all courses");
        List<Course> courses = courseRepository.findAll();
        logger.debug("Found {} courses", courses.size());
        return courses;
    }

    public Course getCourseById(Long id) {
        logger.info("Retrieving course with id: {}", id);
        return courseRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Course not found with id: {}", id);
                return new ResourceNotFoundException("Course", "id", id);
            });
    }

    public Course saveCourse(Course course) {
        logger.info("Saving new course: {}", course.getName());
        Course savedCourse = courseRepository.save(course);
        logger.debug("Course saved successfully with id: {}", savedCourse.getId());
        return savedCourse;
    }

    public void deleteCourseById(Long id) {
        logger.info("Attempting to delete course with id: {}", id);
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            logger.debug("Course deleted successfully with id: {}", id);
        } else {
            logger.error("Failed to delete - course not found with id: {}", id);
            throw new ResourceNotFoundException("Course", "id", id);
        }
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        logger.info("Attempting to update course with id: {}", id);
        Optional<Course> existingCourseOptional = courseRepository.findById(id);

        if (existingCourseOptional.isEmpty()) {
            logger.error("Failed to update - course not found with id: {}", id);
            throw new ResourceNotFoundException("Course", "id", id);
        }

        Course existingCourse = existingCourseOptional.get();
        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setDurationHours(updatedCourse.getDurationHours());
        existingCourse.setCost(updatedCourse.getCost());

        Course savedCourse = courseRepository.save(existingCourse);
        logger.debug("Course updated successfully with id: {}", savedCourse.getId());
        return savedCourse;
    }
}