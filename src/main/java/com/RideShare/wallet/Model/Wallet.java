package com.RideShare.wallet.Model;

import com.RideShare.user.Model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name = "clientId")
    @JsonIgnore
    private Client client;

    private Double balance;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TransactionHistory> transactionHistory;
    @JsonIgnore
    private String securityCode;

    private String bankAccount;

}
