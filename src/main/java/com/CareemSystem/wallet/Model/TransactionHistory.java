package com.CareemSystem.wallet.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Wallet wallet;

    private Integer transactionAmount;

    private Date date;

   // hotel id

   //  trip id

    private String description;

    private TransactionStatus status;
}
