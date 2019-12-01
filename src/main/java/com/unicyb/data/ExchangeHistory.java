package com.unicyb.data;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "exchange_history")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeHistory {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_id")
    private int userId;
    private String date;
    @Column(name ="choose_sell_currency")
    private String chooseSellCurrency;
    @Column(name = "choose_buy_currency")
    private String chooseBuyCurrency;
    @Column(name = "exchange_cell")
    private double exchangeSell;
    @Column(name = "exchange_buy")
    private double exchangeBuy;

    public ExchangeHistory(String date) {
        this.date = date;
    }
}
