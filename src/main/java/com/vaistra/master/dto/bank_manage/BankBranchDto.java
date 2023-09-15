package com.vaistra.master.dto.bank_manage;

import com.vaistra.master.entity.bank_manage.Bank;
import com.vaistra.master.entity.cscv_master.District;
import com.vaistra.master.entity.cscv_master.State;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BankBranchDto {
    private Integer branchId;

    @NotEmpty(message = "Branch name shouldn't be empty.")
    @NotNull(message = "Branch name  shouldn't be null.")
    @NotBlank(message = "Branch name shouldn't be blank.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Branch name should only contain alphabets,numeric and spaces.")
    @Size(min = 2, max = 250, message = "Branch name should have a length between 2 and 250 characters.")
    private String branchName;

    @NotEmpty(message = "Branch Code shouldn't be empty.")
    @NotNull(message = "Branch Code  shouldn't be null.")
    @NotBlank(message = "Branch Code shouldn't be blank.")
    @Pattern(regexp = "^[A-Za-z0-9]", message = "Invalid Branch Code.")
    @Size(min = 3, max = 6, message = "Invalid Branch Code length.")
    private String branchCode;

    @NotNull(message = "Branch address shouldn't be null.")
    private String branchAddress;

    @NotEmpty(message = "Branch IFSC shouldn't be empty.")
    @NotNull(message = "Branch IFSC  shouldn't be null.")
    @NotBlank(message = "Branch IFSC shouldn't be blank.")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$\n", message = "Invalid IFSC Code.")
    private String branchIfsc;

    @NotEmpty(message = "Phone Number shouldn't be empty.")
    @NotNull(message = "Phone Number shouldn't be null.")
    @NotBlank(message = "Phone Number shouldn't be blank.")
    @Pattern(regexp = "^[0-9+\\s]+$", message = "Phone Number should only contain alphabets,numeric and spaces.")
    @Size(min = 10, max = 15, message = "Phone Number should have a length between 10 and 15 digits.")
    private String branchPhoneNumber;

    @NotEmpty(message = "MICR Code shouldn't be empty.")
    @NotNull(message = "MICR Code  shouldn't be null.")
    @NotBlank(message = "MICR Code shouldn't be blank.")
    @Pattern(regexp = "^[0-9]{1,9}$", message = "Invalid MICR Code.")
    private String branchMicr;

    @NotNull(message = "Time shouldn't be null.")
    private LocalTime fromTiming;

    @NotNull(message = "Time shouldn't be null.")
    private LocalTime toTiming;

    @Min(value=0,message = "Bank Id should be positive digits only.")
    private Integer bankId;

    @Pattern(regexp = "^[a-zA-Z09\\s]+$", message = "Bank name should only contain alphabets,numeric and spaces.")
    private String bankName;

    @Min(value=0,message = "State Id should be positive digits only.")
    private Integer stateId;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "State name should only contain alphabets and spaces.")
    private String stateName;

    @Min(value=0,message = "District Id should be positive digits only.")
    private Integer districtId;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "District name should only contain alphabets and spaces.")
    private String districtName;

    private Boolean isActive;

}
