package com.example.exammanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.core.SpringVersion;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class McqAnswer extends StudentAnswer{

    @ManyToOne(optional = false)
    private Option selectedOption;
}
