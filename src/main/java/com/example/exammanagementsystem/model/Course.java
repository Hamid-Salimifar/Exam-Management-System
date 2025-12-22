package com.example.exammanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uniqueCode;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToMany
    @JoinTable(name = "course_students",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<User> student;


//    @OneToMany(mappedBy = "course")
//    private Set<Exam> exams;
}
