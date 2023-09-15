package com.vaistra.master.dto.bank_manage;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BankDto {
    private Integer bankId;

    @NotEmpty(message = "Bank Short name shouldn't be empty.")
    @NotNull(message = "Bank Short name  shouldn't be null.")
    @NotBlank(message = "Bank Short name shouldn't be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Bank Short name should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 50, message = "Bank Short name should have a length between 2 and 50 characters.")
    private String bankShortName;

    @NotEmpty(message = "Bank name shouldn't be empty.")
    @NotNull(message = "Bank name  shouldn't be null.")
    @NotBlank(message = "Bank name shouldn't be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Bank name should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "Bank name should have a length between 2 and 250 characters.")
    private String bankLongName;

    private Boolean isActive;

}
