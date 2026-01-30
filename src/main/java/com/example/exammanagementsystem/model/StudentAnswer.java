package com.example.exammanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"exam_attempt_id", "exam_question_id"}
        )
)
public abstract class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_attempt_id")
    private ExamAttempt examAttempt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_question_id")
    private ExamQuestion examQuestion;

    private Integer score;

}