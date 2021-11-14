package com.jit.authority.dto;

import lombok.Data;

@Data
public class ResetPassword {
    private String oldPassword;
    private String newPassword;
}
