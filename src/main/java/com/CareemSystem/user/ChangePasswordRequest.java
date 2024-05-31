package com.CareemSystem.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChangePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String ConfirmPassword;
}
