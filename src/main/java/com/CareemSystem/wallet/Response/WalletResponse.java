package com.CareemSystem.wallet.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletResponse {
    private Double balance;
}
