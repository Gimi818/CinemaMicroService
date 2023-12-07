package com.screening.screening;


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
