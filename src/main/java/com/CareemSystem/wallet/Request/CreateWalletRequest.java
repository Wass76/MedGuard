package com.CareemSystem.wallet.Request;

import com.CareemSystem.utils.annotation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateWalletRequest {

    @ValidPassword
    private String securityCode;

    private String confirmSecurityCode;

    @NotBlank(message = "bank account can't be blank")
    private String bankAccount;

}
