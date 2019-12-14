package com.unicyb.data;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bank")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String name;

    @Override
    public String toString() {
        return "id - " + id + "; name - " + name;
    }
}
