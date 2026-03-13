package com.example.studentservice.exception;

/**
 * Thrown when a student record cannot be found by ID or other criteria.
 */
public class StudentNotFoundException extends RuntimeException {

    private final Long studentId;

    public StudentNotFoundException(Long studentId) {
        super("Student not found with id: " + studentId);
        this.studentId = studentId;
    }

    public StudentNotFoundException(String message) {
        super(message);
        this.studentId = null;
    }

    public Long getStudentId() {
        return studentId;
    }
}

