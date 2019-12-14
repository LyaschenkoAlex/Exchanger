package com.unicyb.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "monthly_rating")
public class MonthlyRating {

    public MonthlyRating(double courseUSD, double courseEUR, double courseRUB, String date) {
        this.courseUSD = courseUSD;
        this.courseEUR = courseEUR;
        this.courseRUB = courseRUB;
        this.date = date;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "course_usd")
    double courseUSD;
    @Column(name = "course_eur")
    double courseEUR;
    @Column(name = "course_rub")
    double courseRUB;
    String date;    
}
