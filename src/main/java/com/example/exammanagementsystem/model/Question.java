package com.example.exammanagementsystem.model;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String questionText;

    @ManyToOne(optional = false)
    private User teacher;

    @ManyToOne(optional = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

}
