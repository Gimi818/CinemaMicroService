package com.screening.screening;

import com.screening.common.entity.AbstractEntity;
import com.screening.seat.Seat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "screening")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Screening extends AbstractEntity {

    private LocalDate date;

    private LocalTime time;
    private Long filmId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id")
    private List<Seat> seats;
}
