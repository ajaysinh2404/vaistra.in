package com.vaistra.master.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthenticationRequest {
    @NotEmpty(message = "Email shouldn't be empty.")
    @NotNull(message = "Email shouldn't be null.")
    @NotBlank(message = "Email shouldn't be blank.")
    private String email;

    @NotEmpty(message = "Password shouldn't be empty.")
    @NotNull(message = "Password shouldn't be null.")
    @NotBlank(message = "Password shouldn't be blank.")
    private String password;

}
