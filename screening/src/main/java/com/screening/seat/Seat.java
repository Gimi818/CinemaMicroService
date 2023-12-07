package com.screening.seat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.screening.common.entity.AbstractEntity;
import com.screening.screening.Screening;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Seat extends AbstractEntity {
    private int rowsNumber;
    private int seatInRow;
    @ManyToOne
    @JoinColumn(name = "screening_id")
    @JsonIgnore
    private Screening screening;
    @Enumerated(value = EnumType.STRING)
    private SeatStatus status;


}
