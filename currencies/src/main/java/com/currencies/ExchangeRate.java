package com.currencies;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exchangeRate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private String code;
    private double mid;

    public ExchangeRate(String currency, String code, double mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }
}
