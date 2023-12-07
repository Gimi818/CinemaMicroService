package com.screening;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {


    private String title;

    private int durationFilmInMinutes;
}
