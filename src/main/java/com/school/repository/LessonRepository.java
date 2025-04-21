package com.school.repository;

import com.school.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourseIdAndStartTimeBetween(Long courseId, LocalDateTime startTime, LocalDateTime endTime);
}
