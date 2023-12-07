package com.screening.screening;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "screening")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime time;
    private Long filmId;

//    @ManyToOne
//    private Film film;
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "screening_id")
//    private List<Seat> seats;
}
