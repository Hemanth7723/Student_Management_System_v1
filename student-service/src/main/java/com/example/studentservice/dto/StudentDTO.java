package com.example.studentservice.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for Student API requests.
 * Keeps the entity layer separate from the API contract.
 */
public class StudentDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 1) @Max(value = 120)
    private Integer age;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotBlank(message = "Course is required")
    @Size(min = 2, max = 100)
    private String course;

    // ─── Constructors ───────────────────────────────────────────────────────

    public StudentDTO() {}

    public StudentDTO(String name, Integer age, String email, String course) {
        this.name   = name;
        this.age    = age;
        this.email  = email;
        this.course = course;
    }

    // ─── Getters & Setters ──────────────────────────────────────────────────

    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }

    public Integer getAge()             { return age; }
    public void setAge(Integer age)     { this.age = age; }

    public String getEmail()            { return email; }
    public void setEmail(String email)  { this.email = email; }

    public String getCourse()             { return course; }
    public void setCourse(String course)  { this.course = course; }
}

