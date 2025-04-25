package com.school.exception;

public class TeacherHasLessonsException extends RuntimeException {
    public TeacherHasLessonsException(Long teacherId, int lessonCount) {
        super(String.format("Cannot delete teacher with ID %d because they have %d active lessons. Please reassign or delete the lessons first.", teacherId, lessonCount));
    }
}