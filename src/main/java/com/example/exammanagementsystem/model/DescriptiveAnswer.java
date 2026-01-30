package com.example.exammanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescriptiveAnswer extends StudentAnswer {



    @Column(length = 4000)
    private String answerText;
}
