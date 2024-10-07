package com.RideShare.wallet.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {

    @NotBlank(message = "Please enter wallet password")
    private String walletPassword;
    @NotNull(message = "reservation id couldn't be null")
    private Integer reservationID;
}
