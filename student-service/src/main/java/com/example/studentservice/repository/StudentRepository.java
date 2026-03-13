package com.example.studentservice.repository;

import com.example.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for Student entities.
 * Exposes CRUD plus custom search queries.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /** Search students whose name contains the given text (case-insensitive) */
    List<Student> findByNameContainingIgnoreCase(String name);

    /** Find student by exact email (used for duplicate-check) */
    Optional<Student> findByEmail(String email);

    /** Search by course */
    List<Student> findByCourseContainingIgnoreCase(String course);

    /**
     * Full-text search across name, email, and course.
     * Useful for the search box on the frontend.
     */
    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.name)   LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(s.email)  LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(s.course) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Student> searchAll(@Param("q") String query);
}

