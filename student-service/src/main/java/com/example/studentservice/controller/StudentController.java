package com.example.studentservice.controller;

import com.example.studentservice.dto.ApiResponse;
import com.example.studentservice.dto.StudentDTO;
import com.example.studentservice.model.Student;
import com.example.studentservice.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful controller for Student CRUD operations.
 *
 * Base path : /students  (mapped by Spring Cloud Gateway from /api/students)
 *
 * ┌─────────────────────────────────────────────────────────┐
 * │  Method  │ Path             │ Description               │
 * ├──────────┼──────────────────┼───────────────────────────┤
 * │ GET      │ /students        │ Get all students           │
 * │ GET      │ /students/{id}   │ Get student by ID          │
 * │ GET      │ /students/search │ Search (name/email/course) │
 * │ POST     │ /students        │ Add a new student          │
 * │ PUT      │ /students/{id}   │ Update student             │
 * │ DELETE   │ /students/{id}   │ Delete student             │
 * └─────────────────────────────────────────────────────────┘
 *
 * All responses are wrapped in ApiResponse<T>:
 * { "success": true, "message": "...", "data": { ... } }
 */
@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")          // Allow direct calls from the static HTML page
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ─── GET /students ───────────────────────────────────────────────────────

    /**
     * Retrieve all students.
     *
     * Postman: GET http://localhost:8080/api/students
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        String msg = students.isEmpty() ? "No students found" : students.size() + " student(s) found";
        return ResponseEntity.ok(ApiResponse.ok(msg, students));
    }

    // ─── GET /students/{id} ──────────────────────────────────────────────────

    /**
     * Retrieve a single student by ID.
     *
     * Postman: GET http://localhost:8080/api/students/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.ok("Student found", student));
    }

    // ─── GET /students/search?q=... ──────────────────────────────────────────

    /**
     * Search students by name, email, or course (partial match).
     *
     * Postman: GET http://localhost:8080/api/students/search?q=John
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Student>>> searchStudents(
            @RequestParam(value = "q", defaultValue = "") String query) {

        List<Student> results = studentService.searchStudents(query);
        String msg = results.size() + " result(s) for: " + (query.isBlank() ? "(all)" : query);
        return ResponseEntity.ok(ApiResponse.ok(msg, results));
    }

    // ─── POST /students ──────────────────────────────────────────────────────

    /**
     * Create a new student record.
     *
     * Postman: POST http://localhost:8080/api/students
     * Body (raw JSON):
     * {
     *   "name":   "John Doe",
     *   "age":    21,
     *   "email":  "john.doe@example.com",
     *   "course": "Computer Science"
     * }
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> addStudent(
            @Valid @RequestBody StudentDTO studentDTO) {

        Student created = studentService.addStudent(studentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Student added successfully", created));
    }

    // ─── PUT /students/{id} ──────────────────────────────────────────────────

    /**
     * Update an existing student record.
     *
     * Postman: PUT http://localhost:8080/api/students/1
     * Body (raw JSON):
     * {
     *   "name":   "John Updated",
     *   "age":    22,
     *   "email":  "john.updated@example.com",
     *   "course": "Software Engineering"
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {

        Student updated = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(ApiResponse.ok("Student updated successfully", updated));
    }

    // ─── DELETE /students/{id} ───────────────────────────────────────────────

    /**
     * Delete a student record.
     *
     * Postman: DELETE http://localhost:8080/api/students/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.ok("Student deleted successfully", null));
    }
}
