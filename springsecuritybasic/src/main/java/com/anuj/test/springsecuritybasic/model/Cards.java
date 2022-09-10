package com.anuj.test.springsecuritybasic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="cards")
@Getter
@Setter
public class Cards {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="card_id")
    private int cardId;

    @Column(name="customer_id")
    private long customer_id;

    @Column(name="card_number")
    private String cardNumber;

    @Column(name="card_type")
    private String cardType;

    @Column(name="total_amount")
    private int totalLimit;

    @Column(name="amount_used")
    private int amountUsed;

    @Column(name="available_amount")
    private int availableAmount;

    @Column(name="create_dt")
    private Date createDt;


}
