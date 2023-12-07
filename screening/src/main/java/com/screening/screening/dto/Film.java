package com.screening.screening.dto;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Film {

    private Long id;
    private String title;

    private int durationFilmInMinutes;
}