package com.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Resource Not Found",
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeacherHasLessonsException.class)
    public ResponseEntity<ErrorResponse> handleTeacherHasLessonsException(TeacherHasLessonsException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Teacher Has Active Lessons",
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SubjectHasCoursesException.class)
    public ResponseEntity<ErrorResponse> handleSubjectHasCoursesException(SubjectHasCoursesException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Subject Has Associated Courses",
            ex.getMessage(),
            request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        logger.error("Internal server error: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred. Please try again later.",
            request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}