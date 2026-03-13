package com.example.studentservice.service;

import com.example.studentservice.dto.StudentDTO;
import com.example.studentservice.exception.StudentNotFoundException;
import com.example.studentservice.model.Student;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business-logic layer for Student operations.
 * Controllers are thin; all rules live here.
 */
@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ─── CREATE ─────────────────────────────────────────────────────────────

    /**
     * Add a new student.
     * Throws IllegalArgumentException if email is already registered.
     */
    public Student addStudent(StudentDTO dto) {
        studentRepository.findByEmail(dto.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException(
                    "Email already registered: " + dto.getEmail());
        });

        Student student = mapToEntity(dto);
        return studentRepository.save(student);
    }

    // ─── READ ────────────────────────────────────────────────────────────────

    /** Return every student record. */
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Return a student by primary key.
     * Throws StudentNotFoundException if not found.
     */
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    /**
     * Search students by name, email, or course (partial, case-insensitive).
     * Returns all students when the query string is blank.
     */
    @Transactional(readOnly = true)
    public List<Student> searchStudents(String query) {
        if (query == null || query.isBlank()) {
            return studentRepository.findAll();
        }
        return studentRepository.searchAll(query.trim());
    }

    // ─── UPDATE ──────────────────────────────────────────────────────────────

    /**
     * Update an existing student.
     * Only changes fields supplied in the DTO.
     * Throws StudentNotFoundException if the ID doesn't exist.
     * Throws IllegalArgumentException if the new email belongs to another student.
     */
    public Student updateStudent(Long id, StudentDTO dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        // Email uniqueness check — ignore if same student keeps same email
        studentRepository.findByEmail(dto.getEmail()).ifPresent(other -> {
            if (!other.getId().equals(id)) {
                throw new IllegalArgumentException(
                        "Email already used by another student: " + dto.getEmail());
            }
        });

        existing.setName(dto.getName());
        existing.setAge(dto.getAge());
        existing.setEmail(dto.getEmail());
        existing.setCourse(dto.getCourse());

        return studentRepository.save(existing);
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────

    /**
     * Delete a student by ID.
     * Throws StudentNotFoundException if the ID doesn't exist.
     */
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private Student mapToEntity(StudentDTO dto) {
        return new Student(
                dto.getName(),
                dto.getAge(),
                dto.getEmail(),
                dto.getCourse()
        );
    }
}

