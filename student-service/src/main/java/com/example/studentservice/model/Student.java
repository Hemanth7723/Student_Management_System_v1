package com.example.studentservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Student entity — maps to the STUDENTS table.
 * Schema per SRS Section 6:
 *   id, name, age, email, course
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @Min(value = 1, message = "Age must be a positive number")
    @Max(value = 120, message = "Age must be realistic")
    @Column(nullable = false)
    private Integer age;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid address")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Course is required")
    @Size(min = 2, max = 100, message = "Course must be between 2 and 100 characters")
    @Column(nullable = false)
    private String course;

    // ─── Constructors ────────────────────────────────────────────────────────

    public Student() {}

    public Student(String name, Integer age, String email, String course) {
        this.name   = name;
        this.age    = age;
        this.email  = email;
        this.course = course;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────

    public Long getId()               { return id; }
    public void setId(Long id)        { this.id = id; }

    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }

    public Integer getAge()             { return age; }
    public void setAge(Integer age)     { this.age = age; }

    public String getEmail()            { return email; }
    public void setEmail(String email)  { this.email = email; }

    public String getCourse()             { return course; }
    public void setCourse(String course)  { this.course = course; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', age=" + age
                + ", email='" + email + "', course='" + course + "'}";
    }
}

