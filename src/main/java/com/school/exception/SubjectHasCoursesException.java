package com.school.exception;

public class SubjectHasCoursesException extends RuntimeException {
    public SubjectHasCoursesException(Long subjectId, int courseCount) {
        super(String.format("Cannot delete subject with ID %d because it is associated with %d courses. Please remove the subject from all courses first.", subjectId, courseCount));
    }
}