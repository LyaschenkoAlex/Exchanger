package com.unicyb.data;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@RequiredArgsConstructor
//@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "bank_id")
    private int bankId;
    @Column(name = "buy_usd")
    private double buyUSD;
    @Column(name = "sell_usd")
    private double sellUSD;
    @Column(name = "buy_eur")
    private double buyEUR;
    @Column(name = "sell_eur")
    private double sellEUR;
    @Column(name = "buy_rub")
    private double buyRUB;
    @Column(name = "sell_rub")
    private double sellRUB;
    private String date;

    public Course(int bankId, double buyUSD, double sellUSD, double buyEUR, double sellEUR, double buyRUB, double sellRUB, String date) {
        this.bankId = bankId;
        this.buyUSD = buyUSD;
        this.sellUSD = sellUSD;
        this.buyEUR = buyEUR;
        this.sellEUR = sellEUR;
        this.buyRUB = buyRUB;
        this.sellRUB = sellRUB;
        this.date = date;
    }


    @Override
    public String toString() {
        return "bankId - " + bankId;
    }
}
