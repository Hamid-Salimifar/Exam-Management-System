package com.example.exammanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"exam_id","student_id"}))
public class ExamAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private User student;


    private LocalDateTime startTime;
    private LocalDateTime endTime;



    private Integer totalScore;

    @Enumerated(EnumType.STRING)
    private ExamAttemptStatus status;




}
