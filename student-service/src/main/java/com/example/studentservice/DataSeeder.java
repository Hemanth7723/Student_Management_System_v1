package com.example.studentservice;

import com.example.studentservice.model.Student;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Seeds the H2 in-memory database with sample students on startup.
 * Remove or comment @Bean for production deployments.
 */
@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedDatabase(StudentRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Student("Alice Johnson",  21, "alice@example.com",  "Computer Science"));
                repo.save(new Student("Bob Smith",      22, "bob@example.com",    "Software Engineering"));
                repo.save(new Student("Carol Williams", 20, "carol@example.com",  "Information Technology"));
                repo.save(new Student("David Brown",    23, "david@example.com",  "Data Science"));
                repo.save(new Student("Eve Davis",      21, "eve@example.com",    "Cybersecurity"));
                System.out.println("✅ Sample students seeded into database.");
            }
        };
    }
}

